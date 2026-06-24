# Architecture

NexusOps uses a clean modular monolith that can be split into microservices later.

## Backend Layers

- `security`: authentication, authorization, JWT, users, and roles.
- `ai`: model selection, prompt management, and all LLM calls.
- `orchestration`: shared agent contracts, registry, orchestrator, context cache, and execution history.
- `planner`: intent detection and workflow planning.
- `agents/*`: independent bounded modules for project, scrum, release, knowledge, and risk capabilities.
- `events`: Spring event contracts and notification projection.
- `monitoring`: analytics, health, logging, and metrics integration.

## Agent Rules

- Agents implement `Agent`.
- Agents do not call each other.
- `AgentOrchestratorService` is the only coordination mechanism.
- `AgentRegistry` resolves pluggable agents by `AgentType`.
- Agent state is recorded in PostgreSQL and transient context is cached in Redis.

## Future Extraction

Each agent package has controller, service, model, repository, mapper, DTO, prompts, tools, and events boundaries. To extract one agent, expose its `execute` contract over HTTP or messaging and replace the registry entry with a client adapter.
