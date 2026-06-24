# Database Schema

Primary tables:

- `users`, `roles`, `user_roles`, `refresh_tokens`
- `projects`, `project_members`
- `sprints`, `stories`, `tasks`
- `releases`
- `documents`, `embeddings`
- `assignments`
- `agent_execution_history`
- `notifications`
- `prompts`, `system_settings`

The `embeddings` table uses the pgvector extension:

```sql
CREATE EXTENSION IF NOT EXISTS vector;
embedding VECTOR(1536)
```

Flyway owns schema changes. JPA is configured with validation so drift is detected at startup.
