# NexusOps: Agent Orchestrator

NexusOps is a LangGraph-based single-agent orchestrator powered by Ollama.

## Setup Instructions

### 1. Prerequisites
- **Ollama**: Download and install Ollama.
- **Model**: Pull the `llama3.1` model, which is used for tool calling:
  ```bash
  ollama pull llama3.1
  ```

### 2. Environment Setup
Create a virtual environment and install the required dependencies:
```bash
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt
pip install streamlit
```

### 3. Launching the Interfaces

#### Option A: Run the CLI Interface
To interact with the orchestrator in your terminal, run:
```bash
python main.py
```

#### Option B: Run the Web User Interface (UI)
To launch the modern Streamlit web UI:
```bash
streamlit run ui.py
```
This will open the interface in your default web browser (typically at `http://localhost:8501`).

## UI Features
- **Visual Identity**: Premium theme with real-time Ollama connection status indicators.
- **Orchestration State transparency**: Collapsible/sidebar panel reflecting raw real-time trace events of `graph.stream()` showing how the agent makes decisions and executes tools.
- **Session management**: Automatic conversational persistence per browser session with clean state reset options.