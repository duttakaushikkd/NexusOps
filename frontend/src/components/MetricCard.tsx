import { Card, CardContent, Stack, Typography } from '@mui/material';
import { ReactNode } from 'react';

interface Props {
  title: string;
  value: string | number;
  icon: ReactNode;
  tone?: string;
}

export function MetricCard({ title, value, icon, tone = 'primary.main' }: Props) {
  return (
    <Card>
      <CardContent>
        <Stack direction="row" justifyContent="space-between" alignItems="center">
          <div>
            <Typography color="text.secondary" variant="body2">{title}</Typography>
            <Typography variant="h4">{value}</Typography>
          </div>
          <Stack alignItems="center" justifyContent="center" sx={{ width: 44, height: 44, borderRadius: 2, color: tone, bgcolor: 'action.hover' }}>
            {icon}
          </Stack>
        </Stack>
      </CardContent>
    </Card>
  );
}
