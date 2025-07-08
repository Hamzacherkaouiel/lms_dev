package project.learning_managment_system.learning_managment_system_dev.course_managment.Services.Impl;

import org.springframework.stereotype.Service;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Dto.Course_Dto;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Entities.Course;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Entities.Enrollements;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Exceptions.CustomesException.Enrollemnts_Exception;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Mappers.Impl.Mapper_Course;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Mappers.Mapper_Interface;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Repositories.Enrollemnt_Repo;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Service_Enrollemnt {
    public Mapper_Course mapperCourse;
    public Enrollemnt_Repo enrollemntRepo;
    public Service_Enrollemnt(Enrollemnt_Repo repo){
        this.enrollemntRepo=repo;
        this.mapperCourse=new Mapper_Course();
    }
    public List<Course_Dto> getEnrollmentCourses(int studentId) {
        return this.enrollemntRepo.findByStudent_Id(studentId)
                .stream().map(enrollements -> mapperCourse.toDto(enrollements.getCourse()))
                .collect(Collectors.toList());
    }
    public Enrollements createSingleEnrollement(int studentId,int courseId){
        if(this.enrollemntRepo.existsByStudent_IdAndCourse_Id(studentId,courseId)){
            throw  new Enrollemnts_Exception("Student Already enrollet by this course");
        }
        Enrollements enrollements= Enrollements.builder()
                .enrollmentDate(LocalDate.now())
                .course(Course.builder()
                        .id(courseId)
                        .build())
                .student(Student.builder()
                        .id(studentId)
                        .build())
                .build();
        return this.enrollemntRepo.save(enrollements);
    }
    public List<Enrollements> createMultipleEnrollemnts(int courseId, List<Integer> students){
        List<Enrollements> enrollements=new ArrayList<>();
        students.forEach(id->{
            if(!this.enrollemntRepo.existsByStudent_IdAndCourse_Id(id,courseId)) {


                Enrollements element = Enrollements.builder()
                        .enrollmentDate(LocalDate.now())
                        .course(Course.builder()
                                .id(courseId)
                                .build())
                        .student(Student.builder()
                                .id(id)
                                .build())
                        .build();
                enrollements.add(element);
            }

        });
        return this.enrollemntRepo.saveAll(enrollements);
    }
    public void deleteEnrollemnt(int id){
        this.enrollemntRepo.deleteById(id);
    }



}
