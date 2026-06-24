import { Button, Card, CardContent, Grid, MenuItem, Stack, TextField, Typography } from '@mui/material';
import { useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import { api } from '../services/api';

export function SettingsPage() {
  const { data, refetch } = useQuery({ queryKey: ['models'], queryFn: async () => (await api.get('/admin/ai/models')).data });
  const [model, setModel] = useState('llama3');
  async function saveModel() { await api.put('/admin/ai/models', { model }); refetch(); }
  return (
    <Stack spacing={3}>
      <Typography variant="h4">Admin Settings</Typography>
      <Grid container spacing={2}>
        <Grid item xs={12} md={6}><Card><CardContent><Stack spacing={2}>
          <Typography variant="h6">LLM Models</Typography>
          <Typography color="text.secondary">Current model: {data?.current}</Typography>
          <TextField select label="Model" value={model} onChange={(e) => setModel(e.target.value)}>
            {(data?.supported ?? ['llama3', 'qwen3', 'mistral']).map((item: string) => <MenuItem key={item} value={item}>{item}</MenuItem>)}
          </TextField>
          <Button onClick={saveModel}>Change Ollama Model</Button>
        </Stack></CardContent></Card></Grid>
        <Grid item xs={12} md={6}><Card><CardContent><Stack spacing={2}>
          <Typography variant="h6">Agent Controls</Typography>
          <Typography color="text.secondary">Enable or disable agents, inspect logs, and manage prompts through the admin APIs.</Typography>
          <Button variant="outlined">View Agent Logs</Button>
        </Stack></CardContent></Card></Grid>
      </Grid>
    </Stack>
  );
}
