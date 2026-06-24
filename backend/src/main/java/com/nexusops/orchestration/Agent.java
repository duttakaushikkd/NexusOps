package com.nexusops.orchestration;

public interface Agent {
    AgentResponse execute(AgentRequest request);
    AgentType type();
}
