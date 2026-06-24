# Agent Design

## Planner Agent

Responsibilities:

- Detect user intent.
- Produce workflow steps.
- Select specialist agents.
- Resolve high-level dependencies.

## Orchestrator

Responsibilities:

- Execute planner output.
- Route requests to agents.
- Maintain shared context.
- Retry and record failures.
- Aggregate responses.

## Specialist Agents

- Project Agent: plans, milestones, team structure, project health.
- Scrum Agent: sprint goals, stories, tasks, standups, velocity, backlog.
- Release Agent: release plans, readiness, notes, deployment checklist.
- Knowledge Agent: document ingestion, chunking, embeddings, search, RAG answers.
- Risk Agent: risk scoring, evidence, confidence, mitigation plans.

## Adding an Agent

1. Create a package under `backend/src/main/java/com/nexusops/agents`.
2. Implement `Agent`.
3. Add a new `AgentType`.
4. Add prompt file under `backend/src/main/resources/prompts`.
5. Add API endpoints only if the agent also exposes direct product workflows.
