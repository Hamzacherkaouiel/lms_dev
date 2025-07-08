package project.learning_managment_system.learning_managment_system_dev.course_managment.Services.Impl;

import org.springframework.stereotype.Service;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Dto.Course_Dto;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Entities.Course;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Exceptions.CustomesException.Course_Exception;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Mappers.Impl.Mapper_Course;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Mappers.Mapper_Interface;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Repositories.Course_Repo;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Services.Service_Interface;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Teacher;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Service_Course implements Service_Interface<Course_Dto> {
    public Mapper_Interface<Course_Dto,Course> mapperInterface;
    public Course_Repo courseRepo;

    public Service_Course(Course_Repo repo){
        mapperInterface=new Mapper_Course();
        courseRepo=repo;
    }
    @Override
    public List<Course_Dto> getAllData() {
        return this.courseRepo.findAll()
                .stream().map(mapperInterface::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Course_Dto getSingleData(int id) {
        return this.courseRepo.findById(id)
                .map(mapperInterface::toDto)
                .orElseThrow(()->new Course_Exception("Course Not found"));
    }

    @Override
    public Course_Dto createData(Course_Dto data,int id) {
        Course course=this.mapperInterface.toEntity(data);
        course.setTeacher(Teacher.builder()
                        .id(id)
                .build());
        return this.mapperInterface.toDto(this.courseRepo.save(course));
    }

    @Override
    public Course_Dto updateData(Course_Dto data, int id) {
        Course course=this.courseRepo.findById(id)
                .orElseThrow(()->new Course_Exception("Course not found"));
        this.mapperInterface.updateFields(data,course);
        return this.mapperInterface.toDto(this.courseRepo.save(course));
    }

    @Override
    public void deleteData(int id) {
         this.courseRepo.deleteById(id);
    }
    public List<Course_Dto> getCourseByOwner(int id){
        return this.courseRepo.findByTeacher_Id(id)
                .stream().map(mapperInterface::toDto)
                .collect(Collectors.toList());
    }

}
