export interface User {
  id: number;
  firstname: string;
  lastname: string;
  mail: string;
  password?: string;
}

export interface Student extends User {
  enrolledCourses?: Course[];
}

export interface Teacher extends User {
  courses?: Course[];
}

export interface Admin extends User {
  // Admin specific properties
}

export interface Course {
  id: number;
  title: string;
  description: string;
  capacity: number;
  teacher: Teacher;
  modulesList: Module[];
  enrollementsCourses: Enrollment[];
  test?: Test;
}

export interface Module {
  id: number;
  title: string;
  description: string;
  course: Course;
  lessons: Lesson[];
}

export interface Lesson {
  id: number;
  title: string;
  content: string;
  videoUrl?: string;
  module: Module;
}

export interface Enrollment {
  id: number;
  student: Student;
  course: Course;
  enrollmentDate: Date;
}

export interface Test {
  id: number;
  title: string;
  description: string;
  course: Course;
  questions: Question[];
}

export interface Question {
  id: number;
  text: string;
  options: string[];
  correctAnswer: string;
  test: Test;
}

export interface UserCreation {
  firstname: string;
  lastname: string;
  mail: string;
  password: string;
  role: 'student' | 'teacher' | 'admin';
}

export interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}

export type UserRole = 'student' | 'teacher' | 'admin';

export interface AuthUser {
  id: number;
  firstname: string;
  lastname: string;
  mail: string;
  role: UserRole;
  token: string;
}