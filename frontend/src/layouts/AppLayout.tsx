import { Outlet, NavLink, useNavigate } from 'react-router-dom';
import {
  AppBar, Avatar, Badge, Box, Divider, Drawer, IconButton, InputBase, List, ListItemButton,
  ListItemIcon, ListItemText, Stack, Toolbar, Tooltip, Typography
} from '@mui/material';
import DashboardIcon from '@mui/icons-material/Dashboard';
import FolderIcon from '@mui/icons-material/Folder';
import ViewKanbanIcon from '@mui/icons-material/ViewKanban';
import RocketLaunchIcon from '@mui/icons-material/RocketLaunch';
import ReportProblemIcon from '@mui/icons-material/ReportProblem';
import MenuBookIcon from '@mui/icons-material/MenuBook';
import SmartToyIcon from '@mui/icons-material/SmartToy';
import BarChartIcon from '@mui/icons-material/BarChart';
import HubIcon from '@mui/icons-material/Hub';
import SettingsIcon from '@mui/icons-material/Settings';
import NotificationsIcon from '@mui/icons-material/Notifications';
import SearchIcon from '@mui/icons-material/Search';
import DarkModeIcon from '@mui/icons-material/DarkMode';
import LogoutIcon from '@mui/icons-material/Logout';
import { useAuthStore } from '../store/authStore';
import { useUiStore } from '../store/uiStore';

const drawerWidth = 248;
const nav = [
  ['/', 'Dashboard', <DashboardIcon />],
  ['/projects', 'Projects', <FolderIcon />],
  ['/scrum', 'Scrum', <ViewKanbanIcon />],
  ['/releases', 'Releases', <RocketLaunchIcon />],
  ['/risks', 'Risks', <ReportProblemIcon />],
  ['/knowledge', 'Knowledge', <MenuBookIcon />],
  ['/assistant', 'AI Assistant', <SmartToyIcon />],
  ['/analytics', 'Analytics', <BarChartIcon />],
  ['/agents', 'Agent Monitor', <HubIcon />],
  ['/settings', 'Settings', <SettingsIcon />]
] as const;

export function AppLayout() {
  const user = useAuthStore((state) => state.user);
  const logout = useAuthStore((state) => state.logout);
  const toggleMode = useUiStore((state) => state.toggleMode);
  const navigate = useNavigate();

  return (
    <Box sx={{ display: 'flex', minHeight: '100vh' }}>
      <AppBar position="fixed" color="inherit" elevation={0} sx={{ zIndex: (theme) => theme.zIndex.drawer + 1, borderBottom: 1, borderColor: 'divider' }}>
        <Toolbar sx={{ gap: 2 }}>
          <Typography variant="h6" sx={{ minWidth: 190 }}>NexusOps</Typography>
          <Box sx={{ display: 'flex', alignItems: 'center', bgcolor: 'action.hover', borderRadius: 2, px: 1.5, width: { xs: 180, md: 460 } }}>
            <SearchIcon fontSize="small" />
            <InputBase placeholder="Search projects, risks, docs" sx={{ ml: 1, flex: 1 }} />
          </Box>
          <Box sx={{ flex: 1 }} />
          <Tooltip title="Notifications"><IconButton><Badge color="error" badgeContent={3}><NotificationsIcon /></Badge></IconButton></Tooltip>
          <Tooltip title="Toggle theme"><IconButton onClick={toggleMode}><DarkModeIcon /></IconButton></Tooltip>
          <Stack direction="row" spacing={1} alignItems="center">
            <Avatar sx={{ width: 32, height: 32 }}>{user?.name?.[0] ?? 'U'}</Avatar>
            <Box sx={{ display: { xs: 'none', md: 'block' } }}>
              <Typography variant="body2" fontWeight={700}>{user?.name}</Typography>
              <Typography variant="caption" color="text.secondary">{user?.roles?.join(', ')}</Typography>
            </Box>
          </Stack>
          <Tooltip title="Logout"><IconButton onClick={() => { logout(); navigate('/login'); }}><LogoutIcon /></IconButton></Tooltip>
        </Toolbar>
      </AppBar>
      <Drawer variant="permanent" sx={{ width: drawerWidth, '& .MuiDrawer-paper': { width: drawerWidth, boxSizing: 'border-box' } }}>
        <Toolbar />
        <Divider />
        <List sx={{ px: 1 }}>
          {nav.map(([to, label, icon]) => (
            <ListItemButton key={to} component={NavLink} to={to} end={to === '/'} sx={{ borderRadius: 2, mb: 0.5 }}>
              <ListItemIcon>{icon}</ListItemIcon>
              <ListItemText primary={label} />
            </ListItemButton>
          ))}
        </List>
      </Drawer>
      <Box component="main" sx={{ flex: 1, p: 3, mt: 8, minWidth: 0 }}>
        <Outlet />
      </Box>
    </Box>
  );
}
