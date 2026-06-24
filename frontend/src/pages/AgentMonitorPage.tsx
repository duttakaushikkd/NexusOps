import { Card, CardContent, Chip, Stack, Step, StepLabel, Stepper, Table, TableBody, TableCell, TableHead, TableRow, Typography } from '@mui/material';
import { useQuery } from '@tanstack/react-query';
import { api } from '../services/api';
import { AgentExecution } from '../types';

export function AgentMonitorPage() {
  const { data } = useQuery({ queryKey: ['agent-history'], queryFn: async () => (await api.get<AgentExecution[]>('/agents/history')).data, refetchInterval: 5000 });
  return (
    <Stack spacing={3}>
      <Typography variant="h4">Agent Monitor</Typography>
      <Card><CardContent>
        <Stepper activeStep={3} alternativeLabel>
          {['User Request', 'Planner Agent', 'Orchestrator', 'Specialist Agents', 'Aggregate Response'].map((step) => <Step key={step}><StepLabel>{step}</StepLabel></Step>)}
        </Stepper>
      </CardContent></Card>
      <Card><CardContent>
        <Typography variant="h6" gutterBottom>Execution History</Typography>
        <Table size="small"><TableHead><TableRow><TableCell>Agent</TableCell><TableCell>Status</TableCell><TableCell>Execution Time</TableCell><TableCell>Tool Calls</TableCell><TableCell>LLM Calls</TableCell></TableRow></TableHead>
          <TableBody>{(data ?? []).map((row) => <TableRow key={row.id}><TableCell>{row.agentType}</TableCell><TableCell><Chip label={row.status} size="small" color={row.status === 'SUCCESS' ? 'success' : 'error'} /></TableCell><TableCell>{row.executionTimeMs} ms</TableCell><TableCell>{row.toolCalls}</TableCell><TableCell>{row.llmCalls}</TableCell></TableRow>)}</TableBody>
        </Table>
      </CardContent></Card>
    </Stack>
  );
}
