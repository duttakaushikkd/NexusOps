import { Navigate, Route, Routes } from 'react-router-dom';
import { AppLayout } from '../layouts/AppLayout';
import { LoginPage } from '../pages/LoginPage';
import { RegisterPage } from '../pages/RegisterPage';
import { DashboardPage } from '../pages/DashboardPage';
import { ProjectsPage } from '../pages/ProjectsPage';
import { ScrumPage } from '../pages/ScrumPage';
import { ReleasesPage } from '../pages/ReleasesPage';
import { RisksPage } from '../pages/RisksPage';
import { KnowledgePage } from '../pages/KnowledgePage';
import { AssistantPage } from '../pages/AssistantPage';
import { AnalyticsPage } from '../pages/AnalyticsPage';
import { AgentMonitorPage } from '../pages/AgentMonitorPage';
import { SettingsPage } from '../pages/SettingsPage';
import { useAuthStore } from '../store/authStore';

function PrivateRoute() {
  const token = useAuthStore((state) => state.token);
  return token ? <AppLayout /> : <Navigate to="/login" replace />;
}

export function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route element={<PrivateRoute />}>
        <Route path="/" element={<DashboardPage />} />
        <Route path="/projects" element={<ProjectsPage />} />
        <Route path="/scrum" element={<ScrumPage />} />
        <Route path="/releases" element={<ReleasesPage />} />
        <Route path="/risks" element={<RisksPage />} />
        <Route path="/knowledge" element={<KnowledgePage />} />
        <Route path="/assistant" element={<AssistantPage />} />
        <Route path="/analytics" element={<AnalyticsPage />} />
        <Route path="/agents" element={<AgentMonitorPage />} />
        <Route path="/settings" element={<SettingsPage />} />
      </Route>
    </Routes>
  );
}
