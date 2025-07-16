'use client'

import React, { useState, useEffect } from 'react'
import { useParams, useRouter } from 'next/navigation'
import Layout from '@/components/layout/Layout'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/Card'
import { Button } from '@/components/ui/Button'
import { authService } from '@/services/auth'
import { courseService } from '@/services/course'
import { moduleService } from '@/services/lesson'
import { Course, Module } from '@/types'
import { 
  BookOpenIcon, 
  UsersIcon, 
  UserIcon,
  PlayIcon,
  CheckCircleIcon,
  PlusIcon,
  EditIcon,
  TrashIcon,
  ChevronDownIcon,
  ChevronRightIcon
} from 'lucide-react'
import Link from 'next/link'

export default function CourseDetailPage() {
  const params = useParams()
  const router = useRouter()
  const courseId = parseInt(params.id as string)
  
  const [course, setCourse] = useState<Course | null>(null)
  const [modules, setModules] = useState<Module[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [isEnrolled, setIsEnrolled] = useState(false)
  const [error, setError] = useState('')
  const [expandedModules, setExpandedModules] = useState<number[]>([])
  
  const user = authService.getCurrentUser()

  useEffect(() => {
    const fetchCourseDetail = async () => {
      try {
        const courseData = await courseService.getCourse(courseId)
        setCourse(courseData)
        
        // Fetch modules for this course
        const modulesData = await moduleService.getModulesByCourse(courseId)
        setModules(modulesData)
        
        // Check if student is enrolled
        if (user?.role === 'student') {
          const enrolledCourses = await courseService.getEnrolledCoursesByEmail(user.mail)
          setIsEnrolled(enrolledCourses.some(c => c.id === courseId))
        }
      } catch (error) {
        setError('Failed to load course details')
        console.error('Course detail error:', error)
      } finally {
        setIsLoading(false)
      }
    }

    fetchCourseDetail()
  }, [courseId, user])

  const handleEnrollment = async () => {
    if (!user || user.role !== 'student') return
    
    try {
      await courseService.createEnrollment(courseId, user.id)
      setIsEnrolled(true)
      alert('Successfully enrolled in the course!')
    } catch (error) {
      alert('Failed to enroll in the course')
    }
  }

  const toggleModule = (moduleId: number) => {
    setExpandedModules(prev => 
      prev.includes(moduleId) 
        ? prev.filter(id => id !== moduleId)
        : [...prev, moduleId]
    )
  }

  const canEditCourse = user?.role === 'admin' || (user?.role === 'teacher' && course?.teacher.mail === user.mail)

  if (isLoading) {
    return (
      <Layout>
        <div className="flex items-center justify-center min-h-96">
          <div className="text-center">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto"></div>
            <p className="mt-4 text-gray-600">Loading course details...</p>
          </div>
        </div>
      </Layout>
    )
  }

  if (error || !course) {
    return (
      <Layout>
        <div className="text-center py-12">
          <div className="rounded-md bg-red-50 p-4 max-w-md mx-auto">
            <div className="text-sm text-red-700">{error || 'Course not found'}</div>
          </div>
          <Button 
            onClick={() => router.push('/courses')}
            className="mt-4"
          >
            Back to Courses
          </Button>
        </div>
      </Layout>
    )
  }

  return (
    <Layout>
      <div className="space-y-6">
        {/* Course Header */}
        <div className="bg-white rounded-lg shadow-sm border p-6">
          <div className="flex flex-col lg:flex-row lg:items-start lg:justify-between">
            <div className="flex-1">
              <h1 className="text-3xl font-bold text-gray-900 mb-2">
                {course.title}
              </h1>
              <p className="text-gray-600 mb-4">
                {course.description}
              </p>
              
              <div className="flex items-center space-x-6 text-sm text-gray-500">
                <div className="flex items-center">
                  <UserIcon className="h-4 w-4 mr-2" />
                  <span>{course.teacher.firstname} {course.teacher.lastname}</span>
                </div>
                <div className="flex items-center">
                  <UsersIcon className="h-4 w-4 mr-2" />
                  <span>{course.enrollementsCourses.length} enrolled</span>
                </div>
                <div className="flex items-center">
                  <BookOpenIcon className="h-4 w-4 mr-2" />
                  <span>{course.modulesList.length} modules</span>
                </div>
              </div>
            </div>
            
            <div className="mt-6 lg:mt-0 lg:ml-6 flex flex-col space-y-2">
              {user?.role === 'student' && (
                <>
                  {isEnrolled ? (
                    <div className="flex items-center text-green-600">
                      <CheckCircleIcon className="h-5 w-5 mr-2" />
                      <span>Enrolled</span>
                    </div>
                  ) : (
                    <Button
                      onClick={handleEnrollment}
                      disabled={course.capacity === 0}
                      className="w-full"
                    >
                      {course.capacity === 0 ? 'Course Full' : 'Enroll Now'}
                    </Button>
                  )}
                </>
              )}
              
              {canEditCourse && (
                <div className="flex space-x-2">
                  <Button variant="outline" size="sm">
                    <EditIcon className="h-4 w-4 mr-2" />
                    Edit Course
                  </Button>
                  <Button variant="destructive" size="sm">
                    <TrashIcon className="h-4 w-4 mr-2" />
                    Delete
                  </Button>
                </div>
              )}
            </div>
          </div>
        </div>

        {/* Course Content */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Main Content */}
          <div className="lg:col-span-2">
            <Card>
              <CardHeader>
                <div className="flex justify-between items-center">
                  <CardTitle>Course Content</CardTitle>
                  {canEditCourse && (
                    <Button size="sm" variant="outline">
                      <PlusIcon className="h-4 w-4 mr-2" />
                      Add Module
                    </Button>
                  )}
                </div>
              </CardHeader>
              <CardContent>
                {modules.length === 0 ? (
                  <div className="text-center py-8">
                    <BookOpenIcon className="h-12 w-12 text-gray-400 mx-auto mb-4" />
                    <p className="text-gray-500">No modules available yet</p>
                  </div>
                ) : (
                  <div className="space-y-3">
                    {modules.map((module, index) => (
                      <div key={module.id} className="border rounded-lg">
                        <div
                          className="p-4 cursor-pointer hover:bg-gray-50 flex items-center justify-between"
                          onClick={() => toggleModule(module.id)}
                        >
                          <div className="flex items-center">
                            <div className="flex-shrink-0 w-8 h-8 bg-blue-100 rounded-full flex items-center justify-center mr-3">
                              <span className="text-blue-600 font-semibold text-sm">
                                {index + 1}
                              </span>
                            </div>
                            <div>
                              <h3 className="font-medium text-gray-900">
                                {module.title}
                              </h3>
                              <p className="text-sm text-gray-500">
                                {module.description}
                              </p>
                            </div>
                          </div>
                          {expandedModules.includes(module.id) ? (
                            <ChevronDownIcon className="h-5 w-5 text-gray-400" />
                          ) : (
                            <ChevronRightIcon className="h-5 w-5 text-gray-400" />
                          )}
                        </div>
                        
                        {expandedModules.includes(module.id) && (
                          <div className="border-t bg-gray-50 p-4">
                            {module.lessons.length === 0 ? (
                              <p className="text-gray-500 text-sm">No lessons available</p>
                            ) : (
                              <div className="space-y-2">
                                {module.lessons.map((lesson, lessonIndex) => (
                                  <div key={lesson.id} className="flex items-center justify-between py-2">
                                    <div className="flex items-center">
                                      <PlayIcon className="h-4 w-4 text-blue-600 mr-3" />
                                      <span className="text-sm font-medium text-gray-900">
                                        {lesson.title}
                                      </span>
                                    </div>
                                    {(isEnrolled || canEditCourse) && (
                                      <Button variant="ghost" size="sm">
                                        View
                                      </Button>
                                    )}
                                  </div>
                                ))}
                              </div>
                            )}
                          </div>
                        )}
                      </div>
                    ))}
                  </div>
                )}
              </CardContent>
            </Card>
          </div>

          {/* Sidebar */}
          <div className="space-y-6">
            {/* Course Info */}
            <Card>
              <CardHeader>
                <CardTitle>Course Information</CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <div>
                  <h4 className="font-medium text-gray-900">Instructor</h4>
                  <p className="text-sm text-gray-600">
                    {course.teacher.firstname} {course.teacher.lastname}
                  </p>
                </div>
                
                <div>
                  <h4 className="font-medium text-gray-900">Enrollment</h4>
                  <p className="text-sm text-gray-600">
                    {course.enrollementsCourses.length} students enrolled
                  </p>
                </div>
                
                <div>
                  <h4 className="font-medium text-gray-900">Capacity</h4>
                  <p className="text-sm text-gray-600">
                    {course.capacity} spots remaining
                  </p>
                </div>
                
                <div>
                  <h4 className="font-medium text-gray-900">Status</h4>
                  <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                    course.capacity > 0 ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                  }`}>
                    {course.capacity > 0 ? 'Open for enrollment' : 'Enrollment closed'}
                  </span>
                </div>
              </CardContent>
            </Card>

            {/* Enrolled Students (for teachers) */}
            {canEditCourse && (
              <Card>
                <CardHeader>
                  <CardTitle>Enrolled Students</CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="space-y-2">
                    {course.enrollementsCourses.map((enrollment) => (
                      <div key={enrollment.id} className="flex items-center justify-between">
                        <div className="flex items-center">
                          <div className="w-8 h-8 bg-gray-200 rounded-full flex items-center justify-center mr-3">
                            <UserIcon className="h-4 w-4 text-gray-600" />
                          </div>
                          <span className="text-sm font-medium">
                            {enrollment.student.firstname} {enrollment.student.lastname}
                          </span>
                        </div>
                      </div>
                    ))}
                  </div>
                </CardContent>
              </Card>
            )}
          </div>
        </div>
      </div>
    </Layout>
  )
}