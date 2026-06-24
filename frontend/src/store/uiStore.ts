import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface UiState {
  mode: 'light' | 'dark';
  toggleMode: () => void;
}

export const useUiStore = create<UiState>()(
  persist(
    (set, get) => ({
      mode: 'light',
      toggleMode: () => set({ mode: get().mode === 'light' ? 'dark' : 'light' })
    }),
    { name: 'nexusops-ui' }
  )
);
