# pyrefly: ignore [missing-import]
import streamlit as st
import uuid
import requests
import json
from langchain_core.messages import HumanMessage, AIMessage, ToolMessage
from orchestrator import graph, llm

# 1. Page Configuration and Styling
st.set_page_config(
    page_title="NexusOps: Agent Orchestrator",
    page_icon="🤖",
    layout="wide",
    initial_sidebar_state="expanded"
)

# Custom Premium Styling
st.markdown("""
<style>
    /* Dark theme overrides and container backgrounds */
    .stApp {
        background: linear-gradient(135deg, #111827 0%, #1F2937 100%);
        color: #F3F4F6;
    }
    
    /* Title customization */
    .title-container {
        padding: 1rem 0;
        margin-bottom: 2rem;
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .main-title {
        font-family: 'Outfit', 'Inter', sans-serif;
        font-weight: 800;
        font-size: 2.2rem;
        background: linear-gradient(90deg, #60A5FA 0%, #3B82F6 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        margin: 0;
    }
    
    /* Status Badge styling */
    .status-badge {
        padding: 0.4rem 0.8rem;
        border-radius: 9999px;
        font-size: 0.85rem;
        font-weight: 600;
        display: inline-flex;
        align-items: center;
        gap: 0.5rem;
    }
    .status-connected {
        background-color: rgba(16, 185, 129, 0.15);
        color: #10B981;
        border: 1px solid rgba(16, 185, 129, 0.3);
    }
    .status-disconnected {
        background-color: rgba(239, 68, 68, 0.15);
        color: #EF4444;
        border: 1px solid rgba(239, 68, 68, 0.3);
    }
    
    /* Log block styling inside sidebar */
    .log-card {
        background: rgba(255, 255, 255, 0.03);
        border: 1px solid rgba(255, 255, 255, 0.05);
        border-radius: 8px;
        padding: 0.75rem;
        margin-bottom: 0.5rem;
        font-family: 'Courier New', Courier, monospace;
        font-size: 0.85rem;
        color: #E5E7EB;
        word-break: break-all;
    }
    .log-header {
        font-weight: bold;
        margin-bottom: 0.25rem;
    }
    .log-user { color: #60A5FA; }
    .log-dec { color: #F59E0B; }
    .log-tool { color: #A78BFA; }
    .log-resp { color: #10B981; }
    .log-err { color: #EF4444; }
</style>
""", unsafe_allow_html=True)

# 2. Connection and Ollama Verification
def check_ollama(model_name):
    url = "http://localhost:11434/api/tags"
    try:
        response = requests.get(url, timeout=2)
        if response.status_code == 200:
            try:
                models = response.json().get("models", [])
                for m in models:
                    if model_name in m.get("name", ""):
                        return True, "Ollama Connected"
                return False, f"Ollama online, but model '{model_name}' missing"
            except Exception:
                return True, "Ollama Connected"
        return False, f"Ollama returned status {response.status_code}"
    except requests.exceptions.RequestException:
        return False, "Ollama Offline"

# Get model configured in orchestrator
configured_model = getattr(llm, "model", "llama3.1")
ollama_connected, status_message = check_ollama(configured_model)

# 3. Session State Initialization
if "thread_id" not in st.session_state:
    st.session_state.thread_id = str(uuid.uuid4())
if "messages" not in st.session_state:
    st.session_state.messages = []
if "orchestration_logs" not in st.session_state:
    st.session_state.orchestration_logs = []

# Header and Status Indicator
st.markdown(f"""
<div class="title-container">
    <h1 class="main-title">NexusOps: Agent Orchestrator</h1>
    <span class="status-badge {"status-connected" if ollama_connected else "status-disconnected"}">
        {"🟢" if ollama_connected else "🔴"} {status_message}
    </span>
</div>
""", unsafe_allow_html=True)

if not ollama_connected:
    st.error(f"""
    **Warning:** Ollama service is not reachable or configured incorrectly. 
    
    * **Current Status:** {status_message}
    * **Troubleshooting:**
        1. Make sure Ollama is installed and running on `http://localhost:11434`.
        2. Verify that the **`{configured_model}`** model is pulled by running:
           ```bash
           ollama pull {configured_model}
           ```
    """)

# 4. Sidebar: Orchestration State
with st.sidebar:
    st.header("Orchestration State")
    st.markdown("Raw real-time trace events of the graph execution:")
    
    # Reset logs button
    if st.button("Clear Logs & History"):
        st.session_state.messages = []
        st.session_state.orchestration_logs = []
        st.session_state.thread_id = str(uuid.uuid4())
        st.rerun()
        
    logs_container = st.container()

# Render historic logs in sidebar
def render_logs():
    with logs_container:
        for log in st.session_state.orchestration_logs:
            role = log.get("role", "system")
            content = log.get("content", "")
            
            if role == "user_input":
                header = "[USER INPUT]"
                cls = "log-user"
            elif role == "decision":
                header = "[ORCHESTRATOR DECISION]"
                cls = "log-dec"
            elif role == "tool":
                header = "[TOOL EXECUTION]"
                cls = "log-tool"
            elif role == "response":
                header = "[AGENT RESPONSE]"
                cls = "log-resp"
            else:
                header = "[ERROR]"
                cls = "log-err"
                
            st.markdown(f"""
            <div class="log-card">
                <div class="log-header {cls}">{header}</div>
                <div>{content}</div>
            </div>
            """, unsafe_allow_html=True)

render_logs()

# 5. Main Panel: Conversation History
for message in st.session_state.messages:
    with st.chat_message(message["role"]):
        st.write(message["content"])

# 6. User Input and Execution Loop
if user_query := st.chat_input("Ask NexusOps something...", disabled=not ollama_connected):
    # Append human message to standard chat history
    st.session_state.messages.append({"role": "user", "content": user_query})
    with st.chat_message("user"):
        st.write(user_query)
        
    # Append to orchestration state
    st.session_state.orchestration_logs.append({
        "role": "user_input",
        "content": user_query
    })
    
    # Setup placeholder for spinner / assistant answer
    with st.chat_message("assistant"):
        response_placeholder = st.empty()
        with st.spinner("NexusOps Orchestrator is thinking..."):
            # Config for LangGraph thread checkpointer
            config = {"configurable": {"thread_id": st.session_state.thread_id}}
            inputs = {"messages": [HumanMessage(content=user_query)]}
            
            final_reply = ""
            try:
                # Stream the execution of the graph nodes
                for output in graph.stream(inputs, config=config):
                    for node_name, state_update in output.items():
                        messages = state_update.get("messages", [])
                        if not messages:
                            continue
                        
                        last_msg = messages[-1]
                        
                        if node_name == "orchestrator":
                            if hasattr(last_msg, "tool_calls") and last_msg.tool_calls:
                                for tool_call in last_msg.tool_calls:
                                    t_name = tool_call.get("name")
                                    t_args = tool_call.get("args")
                                    st.session_state.orchestration_logs.append({
                                        "role": "decision",
                                        "content": f"Decided to invoke tool '{t_name}' with args: {json.dumps(t_args)}"
                                    })
                            elif isinstance(last_msg, AIMessage) and last_msg.content:
                                final_reply = last_msg.content
                                st.session_state.orchestration_logs.append({
                                    "role": "response",
                                    "content": final_reply
                                })
                                
                        elif node_name == "action":
                            if isinstance(last_msg, ToolMessage):
                                st.session_state.orchestration_logs.append({
                                    "role": "tool",
                                    "content": f"Executed '{last_msg.name}'. Result: {last_msg.content}"
                                })
                                
                    # Trigger visual update of the sidebar logs
                    render_logs()
                    
                if final_reply:
                    response_placeholder.write(final_reply)
                    st.session_state.messages.append({"role": "assistant", "content": final_reply})
                else:
                    response_placeholder.write("No final response was formulated.")
                    
            except Exception as e:
                err_msg = f"An error occurred during execution: {str(e)}"
                response_placeholder.error(err_msg)
                st.session_state.orchestration_logs.append({
                    "role": "error",
                    "content": err_msg
                })
                render_logs()
