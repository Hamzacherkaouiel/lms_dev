import api from '@/lib/api';
import { Student, Teacher, UserCreation } from '@/types';

export const userService = {
  // Student operations
  student: {
    // Get profile
    getProfile: async (): Promise<Student> => {
      const response = await api.get('/Student/profile');
      return response.data;
    },

    // Update profile
    updateProfile: async (id: number, studentData: Partial<Student>): Promise<Student> => {
      const response = await api.put(`/Student/${id}`, studentData);
      return response.data;
    },

    // Update password
    updatePassword: async (passwordData: UserCreation): Promise<string> => {
      const response = await api.put('/Student/password', passwordData);
      return response.data;
    },

    // Delete profile
    deleteProfile: async (): Promise<void> => {
      await api.delete('/Student/profile');
    },

    // Get not enrolled students for a course
    getNotEnrolledStudents: async (courseId: number): Promise<Student[]> => {
      const response = await api.get(`/Student/not-enrolled/${courseId}`);
      return response.data;
    },

    // Get enrolled students for a course
    getEnrolledStudents: async (courseId: number): Promise<Student[]> => {
      const response = await api.get(`/Student/enrolled/${courseId}`);
      return response.data;
    }
  },

  // Teacher operations
  teacher: {
    // Get profile
    getProfile: async (): Promise<Teacher> => {
      const response = await api.get('/Teacher/profile');
      return response.data;
    },

    // Update profile
    updateProfile: async (id: number, teacherData: Partial<Teacher>): Promise<Teacher> => {
      const response = await api.put(`/Teacher/${id}`, teacherData);
      return response.data;
    },

    // Update password
    updatePassword: async (passwordData: UserCreation): Promise<string> => {
      const response = await api.put('/Teacher/password', passwordData);
      return response.data;
    },

    // Delete profile
    deleteProfile: async (): Promise<void> => {
      await api.delete('/Teacher/profile');
    },

    // Get all teachers (if needed)
    getAllTeachers: async (): Promise<Teacher[]> => {
      const response = await api.get('/Teacher/all');
      return response.data;
    }
  },

  // Admin operations
  admin: {
    // Get all users
    getAllUsers: async (): Promise<any[]> => {
      const response = await api.get('/Admin/users');
      return response.data;
    },

    // Get all students
    getAllStudents: async (): Promise<Student[]> => {
      const response = await api.get('/Admin/students');
      return response.data;
    },

    // Get all teachers
    getAllTeachers: async (): Promise<Teacher[]> => {
      const response = await api.get('/Admin/teachers');
      return response.data;
    },

    // Delete user
    deleteUser: async (userId: number): Promise<void> => {
      await api.delete(`/Admin/user/${userId}`);
    },

    // Update user role
    updateUserRole: async (userId: number, role: string): Promise<void> => {
      await api.put(`/Admin/user/${userId}/role`, { role });
    }
  },

  // Common operations
  common: {
    // Get user by ID
    getUserById: async (id: number): Promise<any> => {
      const response = await api.get(`/user/${id}`);
      return response.data;
    },

    // Search users
    searchUsers: async (query: string): Promise<any[]> => {
      const response = await api.get(`/users/search?q=${query}`);
      return response.data;
    }
  }
};