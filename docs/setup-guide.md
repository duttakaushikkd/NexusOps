# Setup Guide

## Prerequisites

- Docker Desktop or a compatible Docker Engine.
- 8 GB RAM minimum, 16 GB recommended when running Ollama models.

## Start

```bash
docker compose up --build
```

## Pull Models

```bash
docker compose exec ollama ollama pull llama3
docker compose exec ollama ollama pull mistral
docker compose exec ollama ollama pull qwen3
```

## Local URLs

- Frontend: http://localhost:3000
- API: http://localhost:8080/api
- Swagger: http://localhost:8080/swagger-ui.html
- Health: http://localhost:8080/actuator/health

## Default Login

- Email: `admin@nexusops.local`
- Password: `admin123`
