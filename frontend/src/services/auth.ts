import api from '@/lib/api';
import { UserCreation, AuthUser, ApiResponse } from '@/types';

export const authService = {
  // Register new user
  register: async (userData: UserCreation): Promise<AuthUser> => {
    const response = await api.post('/sign/', userData);
    return response.data;
  },

  // Create user (admin only)
  createUser: async (userData: UserCreation): Promise<AuthUser> => {
    const response = await api.post('/sign/create-user', userData);
    return response.data;
  },

  // Login with Keycloak (handled by NextAuth)
  login: async (credentials: { username: string; password: string }) => {
    // This would typically integrate with Keycloak
    // For now, we'll simulate the response
    const response = await api.post('/auth/login', credentials);
    return response.data;
  },

  // Logout
  logout: async () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    // Additional logout logic if needed
  },

  // Get current user profile
  getCurrentUser: () => {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
  },

  // Save user to localStorage
  saveUser: (user: AuthUser) => {
    localStorage.setItem('user', JSON.stringify(user));
    localStorage.setItem('token', user.token);
  },

  // Check if user is authenticated
  isAuthenticated: () => {
    return !!localStorage.getItem('token');
  },

  // Get user role
  getUserRole: (): string | null => {
    const user = authService.getCurrentUser();
    return user?.role || null;
  }
};