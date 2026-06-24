import { createTheme } from '@mui/material/styles';

export const buildTheme = (mode: 'light' | 'dark') =>
  createTheme({
    palette: {
      mode,
      primary: { main: '#2563eb' },
      secondary: { main: '#0f766e' },
      warning: { main: '#f59e0b' },
      error: { main: '#dc2626' },
      background: {
        default: mode === 'light' ? '#f5f7fb' : '#101418',
        paper: mode === 'light' ? '#ffffff' : '#171d23'
      }
    },
    shape: { borderRadius: 8 },
    typography: {
      fontFamily: 'Inter, ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif',
      h4: { fontWeight: 700 },
      h5: { fontWeight: 700 },
      h6: { fontWeight: 700 },
      button: { textTransform: 'none', fontWeight: 700 }
    },
    components: {
      MuiCard: { styleOverrides: { root: { boxShadow: 'none', border: '1px solid rgba(127,127,127,0.18)' } } },
      MuiButton: { defaultProps: { variant: 'contained' } }
    }
  });
