import api from '@/lib/api';
import { Lesson, Module } from '@/types';

interface SimpleLessonDto {
  id: number;
  title: string;
  content: string;
  moduleId: number;
}

interface FullLessonDto extends SimpleLessonDto {
  videoUrl?: string;
  videoContent?: string;
}

export const lessonService = {
  // Get all lessons
  getAllLessons: async (): Promise<SimpleLessonDto[]> => {
    const response = await api.get('/lessons/');
    return response.data;
  },

  // Get simple lesson
  getSimpleLesson: async (id: number): Promise<SimpleLessonDto> => {
    const response = await api.get(`/lessons/${id}/simple`);
    return response.data;
  },

  // Get full lesson with video
  getFullLesson: async (id: number): Promise<FullLessonDto> => {
    const response = await api.get(`/lessons/${id}/full`);
    return response.data;
  },

  // Get lessons by module ID
  getLessonsByModule: async (moduleId: number): Promise<SimpleLessonDto[]> => {
    const response = await api.get(`/lessons/module/${moduleId}`);
    return response.data;
  },

  // Create new lesson
  createLesson: async (lessonData: Partial<Lesson>, moduleId: number): Promise<SimpleLessonDto> => {
    const response = await api.post(`/lessons/module/${moduleId}`, lessonData);
    return response.data;
  },

  // Update lesson
  updateLesson: async (id: number, lessonData: Partial<Lesson>): Promise<SimpleLessonDto> => {
    const response = await api.put(`/lessons/${id}`, lessonData);
    return response.data;
  },

  // Delete lesson
  deleteLesson: async (id: number): Promise<void> => {
    await api.delete(`/lessons/${id}`);
  },

  // Upload video for lesson
  uploadLessonVideo: async (lessonId: number, videoFile: FormData): Promise<void> => {
    const response = await api.post(`/lessons/${lessonId}/video`, videoFile, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  }
};

export const moduleService = {
  // Get all modules
  getAllModules: async (): Promise<Module[]> => {
    const response = await api.get('/modules/');
    return response.data;
  },

  // Get single module
  getModule: async (id: number): Promise<Module> => {
    const response = await api.get(`/modules/${id}`);
    return response.data;
  },

  // Get modules by course ID
  getModulesByCourse: async (courseId: number): Promise<Module[]> => {
    const response = await api.get(`/modules/course/${courseId}`);
    return response.data;
  },

  // Create new module
  createModule: async (moduleData: Partial<Module>, courseId: number): Promise<Module> => {
    const response = await api.post(`/modules/course/${courseId}`, moduleData);
    return response.data;
  },

  // Update module
  updateModule: async (id: number, moduleData: Partial<Module>): Promise<Module> => {
    const response = await api.put(`/modules/${id}`, moduleData);
    return response.data;
  },

  // Delete module
  deleteModule: async (id: number): Promise<void> => {
    await api.delete(`/modules/${id}`);
  },

  // Get module with lessons
  getModuleWithLessons: async (id: number): Promise<Module> => {
    const response = await api.get(`/modules/${id}/lessons`);
    return response.data;
  }
};