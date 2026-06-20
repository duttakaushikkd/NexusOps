import uuid
# pyrefly: ignore [missing-import]
from langchain_core.messages import HumanMessage, AIMessage
from orchestrator import graph

def main():
    print("==================================================")
    print("           NEXUSOPS AGENT ORCHESTRATOR           ")
    print("==================================================")
    print("Interactive CLI session. Type 'exit' or 'quit' to end.")
    print("Configuring stateful session checkpointer...")
    
    # Generate a unique thread ID for the session to persist state
    thread_id = str(uuid.uuid4())
    config = {"configurable": {"thread_id": thread_id}}
    print(f"Session started with Thread ID: {thread_id}\n")
    
    while True:
        try:
            user_input = input("User > ").strip()
            if not user_input:
                continue
            if user_input.lower() in ["exit", "quit"]:
                print("Exiting NexusOps. Goodbye!")
                break
                
            inputs = {"messages": [HumanMessage(content=user_input)]}
            
            # Stream the execution of the graph nodes
            for output in graph.stream(inputs, config=config):
                for node_name, state_update in output.items():
                    messages = state_update.get("messages", [])
                    if messages:
                        last_msg = messages[-1]
                        # Print the final response from NexusOps (AIMessage with content and no tool calls)
                        if isinstance(last_msg, AIMessage) and last_msg.content and not last_msg.tool_calls:
                            print(f"\nNexusOps > {last_msg.content}\n")
                            
        except KeyboardInterrupt:
            print("\nExiting NexusOps. Goodbye!")
            break
        except Exception as e:
            print(f"\nAn error occurred during execution: {e}")

if __name__ == "__main__":
    main()
