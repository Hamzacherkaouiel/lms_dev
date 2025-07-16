import api from '@/lib/api';
import { Course, Module, Lesson } from '@/types';

export const courseService = {
  // Get all courses
  getAllCourses: async (): Promise<Course[]> => {
    const response = await api.get('/courses/');
    return response.data;
  },

  // Get single course
  getCourse: async (id: number): Promise<Course> => {
    const response = await api.get(`/courses/${id}`);
    return response.data;
  },

  // Get courses by teacher ID
  getCoursesByTeacherId: async (teacherId: number): Promise<Course[]> => {
    const response = await api.get(`/courses/teacher/${teacherId}`);
    return response.data;
  },

  // Get courses by teacher email
  getCoursesByTeacherEmail: async (email: string): Promise<Course[]> => {
    const response = await api.get(`/courses/teacher/mail/${email}`);
    return response.data;
  },

  // Create new course
  createCourse: async (courseData: Partial<Course>, teacherId: number): Promise<Course> => {
    const response = await api.post(`/courses/teacher/${teacherId}`, courseData);
    return response.data;
  },

  // Create course by teacher email
  createCourseByEmail: async (courseData: Partial<Course>, email: string): Promise<Course> => {
    const response = await api.post(`/courses/teacher/mail/${email}`, courseData);
    return response.data;
  },

  // Update course
  updateCourse: async (id: number, courseData: Partial<Course>): Promise<Course> => {
    const response = await api.put(`/courses/${id}`, courseData);
    return response.data;
  },

  // Delete course
  deleteCourse: async (id: number): Promise<void> => {
    await api.delete(`/courses/${id}`);
  },

  // Get enrolled courses by student ID
  getEnrolledCourses: async (studentId: number): Promise<Course[]> => {
    const response = await api.get(`/enrollemnt/${studentId}`);
    return response.data;
  },

  // Get enrolled courses by student email
  getEnrolledCoursesByEmail: async (email: string): Promise<Course[]> => {
    const response = await api.get(`/enrollemnt/mail/${email}`);
    return response.data;
  },

  // Create single enrollment
  createEnrollment: async (courseId: number, studentId: number): Promise<void> => {
    await api.post(`/enrollemnt/${courseId}/${studentId}/single`);
  },

  // Create multiple enrollments
  createMultipleEnrollments: async (courseId: number, studentIds: number[]): Promise<void> => {
    await api.post(`/enrollemnt/${courseId}/multiple`, studentIds);
  },

  // Delete enrollment
  deleteEnrollment: async (enrollmentId: number): Promise<void> => {
    await api.delete(`/enrollemnt/${enrollmentId}`);
  },

  // Delete enrollment by student ID
  deleteEnrollmentByStudentId: async (studentId: number): Promise<void> => {
    await api.delete(`/enrollemnt/student/${studentId}`);
  }
};