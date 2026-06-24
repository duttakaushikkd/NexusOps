import { Button, Card, CardContent, Stack, TextField, Typography } from '@mui/material';
import { useState } from 'react';
import { api } from '../services/api';

export function RisksPage() {
  const [scope, setScope] = useState('Authentication release has blocked dependency and delayed sprint.');
  const [result, setResult] = useState<any>();
  async function analyze() {
    setResult((await api.post('/risk-analysis', { scope })).data);
  }
  return (
    <Stack spacing={3}>
      <Typography variant="h4">Risk Analysis</Typography>
      <Card><CardContent><Stack spacing={2}>
        <TextField label="Scope" multiline minRows={4} value={scope} onChange={(e) => setScope(e.target.value)} />
        <Button onClick={analyze}>Analyze Risk</Button>
      </Stack></CardContent></Card>
      {result && <Card><CardContent><Stack spacing={1}>
        <Typography variant="h5">Risk Score: {result.riskScore}</Typography>
        <Typography>{result.report}</Typography>
        <Typography color="text.secondary">Mitigations: {result.mitigations?.join(', ')}</Typography>
      </Stack></CardContent></Card>}
    </Stack>
  );
}
