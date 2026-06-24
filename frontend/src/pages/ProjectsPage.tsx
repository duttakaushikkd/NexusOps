import { Button, Card, CardContent, Grid, Stack, TextField, Typography, Chip } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import AutoAwesomeIcon from '@mui/icons-material/AutoAwesome';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';
import { api } from '../services/api';
import { Page, Project } from '../types';

export function ProjectsPage() {
  const [name, setName] = useState('');
  const queryClient = useQueryClient();
  const { data } = useQuery({ queryKey: ['projects'], queryFn: async () => (await api.get<Page<Project>>('/projects')).data });
  const create = useMutation({
    mutationFn: async () => api.post('/projects', { name, description: 'Created from NexusOps UI', owner: 'PMO', status: 'ACTIVE' }),
    onSuccess: () => { setName(''); queryClient.invalidateQueries({ queryKey: ['projects'] }); }
  });

  return (
    <Stack spacing={3}>
      <Stack direction={{ xs: 'column', md: 'row' }} spacing={2} justifyContent="space-between">
        <Typography variant="h4">Projects</Typography>
        <Stack direction="row" spacing={1}>
          <TextField size="small" placeholder="Project name" value={name} onChange={(event) => setName(event.target.value)} />
          <Button startIcon={<AddIcon />} onClick={() => create.mutate()} disabled={!name}>Create</Button>
        </Stack>
      </Stack>
      <Grid container spacing={2}>
        {(data?.content ?? []).map((project) => (
          <Grid item xs={12} md={4} key={project.id}>
            <Card><CardContent>
              <Stack spacing={1.5}>
                <Stack direction="row" justifyContent="space-between"><Typography variant="h6">{project.name}</Typography><Chip label={project.status} size="small" /></Stack>
                <Typography color="text.secondary">{project.description}</Typography>
                <Typography variant="body2">Owner: {project.owner ?? 'Unassigned'}</Typography>
                <Typography variant="body2">Health: {project.healthScore}%</Typography>
                <Button startIcon={<AutoAwesomeIcon />} variant="outlined">Generate Project Plan</Button>
              </Stack>
            </CardContent></Card>
          </Grid>
        ))}
      </Grid>
    </Stack>
  );
}
