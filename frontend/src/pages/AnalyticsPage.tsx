import { Card, CardContent, Grid, Stack, Typography } from '@mui/material';
import { Line, LineChart, Bar, BarChart, Pie, PieChart, Cell, ResponsiveContainer, CartesianGrid, Tooltip, XAxis, YAxis } from 'recharts';

const trend = [{ name: 'Jan', completion: 45, health: 70 }, { name: 'Feb', completion: 58, health: 74 }, { name: 'Mar', completion: 71, health: 80 }];

export function AnalyticsPage() {
  return (
    <Stack spacing={3}>
      <Typography variant="h4">Analytics</Typography>
      <Grid container spacing={2}>
        <Grid item xs={12} md={6}><Card><CardContent><Typography variant="h6">Project Completion Trend</Typography><ResponsiveContainer height={280}><LineChart data={trend}><CartesianGrid strokeDasharray="3 3" /><XAxis dataKey="name" /><YAxis /><Tooltip /><Line dataKey="completion" stroke="#2563eb" /></LineChart></ResponsiveContainer></CardContent></Card></Grid>
        <Grid item xs={12} md={6}><Card><CardContent><Typography variant="h6">Velocity</Typography><ResponsiveContainer height={280}><BarChart data={trend}><CartesianGrid strokeDasharray="3 3" /><XAxis dataKey="name" /><YAxis /><Tooltip /><Bar dataKey="completion" fill="#0f766e" /></BarChart></ResponsiveContainer></CardContent></Card></Grid>
        <Grid item xs={12} md={6}><Card><CardContent><Typography variant="h6">Workload Distribution</Typography><ResponsiveContainer height={280}><PieChart><Pie data={[{ name: 'Backend', value: 35 }, { name: 'Frontend', value: 30 }, { name: 'AI', value: 20 }, { name: 'QA', value: 15 }]} dataKey="value">{['#2563eb', '#0f766e', '#f59e0b', '#dc2626'].map((c) => <Cell key={c} fill={c} />)}</Pie></PieChart></ResponsiveContainer></CardContent></Card></Grid>
        <Grid item xs={12} md={6}><Card><CardContent><Typography variant="h6">Release Success Rate</Typography><ResponsiveContainer height={280}><LineChart data={trend}><CartesianGrid strokeDasharray="3 3" /><XAxis dataKey="name" /><YAxis /><Tooltip /><Line dataKey="health" stroke="#f59e0b" /></LineChart></ResponsiveContainer></CardContent></Card></Grid>
      </Grid>
    </Stack>
  );
}
