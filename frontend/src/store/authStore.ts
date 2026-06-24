import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import { AuthResponse } from '../types';

interface AuthState {
  token?: string;
  user?: Pick<AuthResponse, 'name' | 'email' | 'roles'>;
  setAuth: (response: AuthResponse) => void;
  logout: () => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      setAuth: (response) => set({ token: response.accessToken, user: { name: response.name, email: response.email, roles: response.roles } }),
      logout: () => set({ token: undefined, user: undefined })
    }),
    { name: 'nexusops-auth' }
  )
);
