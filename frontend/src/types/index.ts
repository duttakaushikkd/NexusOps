export type Role = 'ADMIN' | 'PROJECT_MANAGER' | 'SCRUM_MASTER' | 'DEVELOPER' | 'VIEWER';

export interface AuthResponse {
  accessToken: string;
  tokenType: string;
  name: string;
  email: string;
  roles: Role[];
}

export interface Project {
  id: string;
  name: string;
  description?: string;
  owner?: string;
  status: string;
  healthScore: number;
  createdAt: string;
  updatedAt: string;
}

export interface Page<T> {
  content: T[];
  totalElements: number;
}

export interface AgentExecution {
  id: string;
  correlationId: string;
  agentType: string;
  status: string;
  executionTimeMs: number;
  llmCalls: number;
  toolCalls: number;
  createdAt: string;
  response?: string;
}
