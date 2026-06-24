import { Button, Card, CardContent, Container, MenuItem, Stack, TextField, Typography } from '@mui/material';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import { useAuthStore } from '../store/authStore';
import { AuthResponse, Role } from '../types';

export function RegisterPage() {
  const [form, setForm] = useState({ name: '', email: '', password: '', confirmPassword: '', role: 'DEVELOPER' as Role });
  const setAuth = useAuthStore((state) => state.setAuth);
  const navigate = useNavigate();

  async function submit() {
    if (form.password !== form.confirmPassword) return;
    const { data } = await api.post<AuthResponse>('/auth/register', form);
    setAuth(data);
    navigate('/');
  }

  return (
    <Container maxWidth="sm" sx={{ py: 8 }}>
      <Card><CardContent>
        <Stack spacing={2}>
          <Typography variant="h4">Create Account</Typography>
          <TextField label="Name" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} />
          <TextField label="Email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} />
          <TextField label="Password" type="password" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} />
          <TextField label="Confirm Password" type="password" value={form.confirmPassword} onChange={(e) => setForm({ ...form, confirmPassword: e.target.value })} />
          <TextField select label="Role" value={form.role} onChange={(e) => setForm({ ...form, role: e.target.value as Role })}>
            {['PROJECT_MANAGER', 'SCRUM_MASTER', 'DEVELOPER', 'VIEWER'].map((role) => <MenuItem key={role} value={role}>{role}</MenuItem>)}
          </TextField>
          <Button onClick={submit}>Register</Button>
        </Stack>
      </CardContent></Card>
    </Container>
  );
}
