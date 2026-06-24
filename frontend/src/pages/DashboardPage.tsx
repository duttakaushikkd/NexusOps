import { Grid, Card, CardContent, Stack, Typography } from '@mui/material';
import FolderIcon from '@mui/icons-material/Folder';
import ViewKanbanIcon from '@mui/icons-material/ViewKanban';
import RocketLaunchIcon from '@mui/icons-material/RocketLaunch';
import ReportProblemIcon from '@mui/icons-material/ReportProblem';
import { useQuery } from '@tanstack/react-query';
import { Area, AreaChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts';
import { api } from '../services/api';
import { MetricCard } from '../components/MetricCard';

export function DashboardPage() {
  const { data } = useQuery({ queryKey: ['analytics-summary'], queryFn: async () => (await api.get('/analytics/summary')).data });
  const trends = data?.trends ?? [];
  return (
    <Stack spacing={3}>
      <Typography variant="h4">Dashboard</Typography>
      <Grid container spacing={2}>
        <Grid item xs={12} md={3}><MetricCard title="Total Projects" value={data?.projects?.total ?? 0} icon={<FolderIcon />} /></Grid>
        <Grid item xs={12} md={3}><MetricCard title="Active Sprints" value={data?.sprints?.active ?? 0} icon={<ViewKanbanIcon />} tone="secondary.main" /></Grid>
        <Grid item xs={12} md={3}><MetricCard title="Upcoming Releases" value={data?.releases?.upcoming ?? 0} icon={<RocketLaunchIcon />} tone="warning.main" /></Grid>
        <Grid item xs={12} md={3}><MetricCard title="High Risks" value={data?.risks?.high ?? 0} icon={<ReportProblemIcon />} tone="error.main" /></Grid>
      </Grid>
      <Card><CardContent>
        <Typography variant="h6" gutterBottom>AI Insights</Typography>
        <Typography color="text.secondary">Current delivery health is trending upward. Review dependency risks before release readiness review.</Typography>
      </CardContent></Card>
      <Card><CardContent>
        <Typography variant="h6" gutterBottom>Project Health Trend</Typography>
        <ResponsiveContainer width="100%" height={300}>
          <AreaChart data={trends}><CartesianGrid strokeDasharray="3 3" /><XAxis dataKey="name" /><YAxis /><Tooltip /><Area dataKey="health" stroke="#2563eb" fill="#93c5fd" /></AreaChart>
        </ResponsiveContainer>
      </CardContent></Card>
    </Stack>
  );
}
