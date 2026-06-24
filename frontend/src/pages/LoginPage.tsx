import { Box, Button, Card, CardContent, Container, Link, Stack, TextField, Typography } from '@mui/material';
import { useState } from 'react';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import { useAuthStore } from '../store/authStore';
import { AuthResponse } from '../types';

export function LoginPage() {
  const [email, setEmail] = useState('admin@nexusops.local');
  const [password, setPassword] = useState('admin123');
  const setAuth = useAuthStore((state) => state.setAuth);
  const navigate = useNavigate();

  async function submit() {
    const { data } = await api.post<AuthResponse>('/auth/login', { email, password });
    setAuth(data);
    navigate('/');
  }

  return (
    <Container maxWidth="sm" sx={{ py: 12 }}>
      <Card>
        <CardContent>
          <Stack spacing={2.5}>
            <Box>
              <Typography variant="h4">NexusOps</Typography>
              <Typography color="text.secondary">Sign in to the AI project manager workspace.</Typography>
            </Box>
            <TextField label="Email" value={email} onChange={(event) => setEmail(event.target.value)} fullWidth />
            <TextField label="Password" type="password" value={password} onChange={(event) => setPassword(event.target.value)} fullWidth />
            <Button size="large" onClick={submit}>Login</Button>
            <Typography variant="body2">New workspace user? <Link component={RouterLink} to="/register">Create an account</Link></Typography>
          </Stack>
        </CardContent>
      </Card>
    </Container>
  );
}
