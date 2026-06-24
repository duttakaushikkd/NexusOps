import { Box, Card, CardContent, Grid, Stack, Typography } from '@mui/material';
import { Bar, BarChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts';

const columns = ['Backlog', 'Todo', 'In Progress', 'Review', 'Done'];
const cards = ['Auth login API', 'JWT refresh flow', 'Project create form', 'Risk scoring test', 'Release notes UI'];

export function ScrumPage() {
  return (
    <Stack spacing={3}>
      <Typography variant="h4">Scrum Board</Typography>
      <Grid container spacing={2}>
        {columns.map((column, index) => (
          <Grid item xs={12} md={2.4} key={column}>
            <Card className="scrum-column"><CardContent>
              <Typography variant="h6" gutterBottom>{column}</Typography>
              <Stack spacing={1}>
                {cards.filter((_, i) => i % columns.length === index).map((card) => (
                  <Box key={card} sx={{ p: 1.5, border: 1, borderColor: 'divider', borderRadius: 2, bgcolor: 'background.default' }}>
                    <Typography fontWeight={700}>{card}</Typography>
                    <Typography variant="caption" color="text.secondary">Story points: 5</Typography>
                  </Box>
                ))}
              </Stack>
            </CardContent></Card>
          </Grid>
        ))}
      </Grid>
      <Card><CardContent>
        <Typography variant="h6">Velocity</Typography>
        <ResponsiveContainer height={260}>
          <BarChart data={[{ sprint: 'S1', velocity: 28 }, { sprint: 'S2', velocity: 34 }, { sprint: 'S3', velocity: 42 }]}>
            <CartesianGrid strokeDasharray="3 3" /><XAxis dataKey="sprint" /><YAxis /><Tooltip /><Bar dataKey="velocity" fill="#0f766e" />
          </BarChart>
        </ResponsiveContainer>
      </CardContent></Card>
    </Stack>
  );
}
