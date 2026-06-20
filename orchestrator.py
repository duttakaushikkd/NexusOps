from typing import Annotated, Sequence
from typing_extensions import TypedDict
# pyrefly: ignore [missing-import]
from langchain_core.messages import BaseMessage, ToolMessage
# pyrefly: ignore [missing-import]
from langgraph.graph.message import add_messages
# pyrefly: ignore [missing-import]
from langchain_ollama import ChatOllama
# pyrefly: ignore [missing-import]
from langgraph.graph import StateGraph, END
# pyrefly: ignore [missing-import]
from langgraph.checkpoint.memory import MemorySaver

from tools import lookup_order_status, process_refund

# Define tools list and map
tools = [lookup_order_status, process_refund]
tool_map = {tool.name: tool for tool in tools}

# 1. State Definition
class AgentState(TypedDict):
    messages: Annotated[Sequence[BaseMessage], add_messages]

# 2. LLM Setup
# Connects to the local Ollama instance on http://localhost:11434
# Uses a model like llama3.1 that is capable of tool calling
llm = ChatOllama(
    model="llama3.1",
    base_url="http://localhost:11434",
    temperature=0
)
llm_with_tools = llm.bind_tools(tools)

# 3. Orchestrator Node
def orchestrator_node(state: AgentState):
    print("\n[ORCHESTRATOR] Invoking LLM with conversation state...")
    response = llm_with_tools.invoke(state["messages"])
    return {"messages": [response]}

# 4. Action/Tool Node
def action_node(state: AgentState):
    print("\n[ACTION/TOOL] Executing requested tool calls...")
    last_message = state["messages"][-1]
    tool_outputs = []
    
    for tool_call in last_message.tool_calls:
        tool_name = tool_call["name"]
        tool_args = tool_call["args"]
        print(f"[ACTION/TOOL] Calling function '{tool_name}' with parameters: {tool_args}")
        
        tool = tool_map.get(tool_name)
        if tool:
            try:
                output = tool.invoke(tool_args)
            except Exception as e:
                output = f"Error executing tool: {str(e)}"
        else:
            output = f"Error: Tool '{tool_name}' not found."
            
        print(f"[ACTION/TOOL] Result: {output}")
        tool_outputs.append(ToolMessage(
            content=str(output),
            name=tool_name,
            tool_call_id=tool_call["id"]
        ))
        
    return {"messages": tool_outputs}

# 5. Conditional Router
def router(state: AgentState):
    last_message = state["messages"][-1]
    if hasattr(last_message, "tool_calls") and last_message.tool_calls:
        print("[ORCHESTRATOR] Routing control to Action Node: execution in progress...")
        return "action"
    print("[ORCHESTRATOR] Final response formulated. Routing to END.")
    return END

# 6. Graph Compilation
workflow = StateGraph(AgentState)

workflow.add_node("orchestrator", orchestrator_node)
workflow.add_node("action", action_node)

workflow.set_entry_point("orchestrator")
workflow.add_conditional_edges(
    "orchestrator",
    router,
    {
        "action": "action",
        END: END
    }
)
workflow.add_edge("action", "orchestrator")

memory = MemorySaver()
graph = workflow.compile(checkpointer=memory)
