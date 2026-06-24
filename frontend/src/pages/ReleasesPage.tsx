import { Button, Card, CardContent, LinearProgress, Stack, Typography } from '@mui/material';
import AutoAwesomeIcon from '@mui/icons-material/AutoAwesome';

export function ReleasesPage() {
  return (
    <Stack spacing={3}>
      <Stack direction="row" justifyContent="space-between"><Typography variant="h4">Releases</Typography><Button startIcon={<AutoAwesomeIcon />}>Generate Release Plan</Button></Stack>
      {['v1.0 Authentication', 'v1.1 Knowledge Base', 'v2.0 Agent Marketplace'].map((name, index) => (
        <Card key={name}><CardContent>
          <Stack spacing={1}>
            <Typography variant="h6">{name}</Typography>
            <Typography color="text.secondary">Readiness score</Typography>
            <LinearProgress variant="determinate" value={[82, 61, 37][index]} />
            <Typography variant="body2">Deployment checklist, release notes, rollback plan, QA signoff.</Typography>
          </Stack>
        </CardContent></Card>
      ))}
    </Stack>
  );
}
