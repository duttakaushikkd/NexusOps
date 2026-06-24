package com.nexusops.orchestration;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class AgentRegistry {
    private final Map<AgentType, Agent> agents = new EnumMap<>(AgentType.class);

    public AgentRegistry(List<Agent> registeredAgents) {
        registeredAgents.forEach(agent -> agents.put(agent.type(), agent));
    }

    public Agent get(AgentType type) {
        var agent = agents.get(type);
        if (agent == null) {
            throw new IllegalArgumentException("Agent is not registered: " + type);
        }
        return agent;
    }
}
