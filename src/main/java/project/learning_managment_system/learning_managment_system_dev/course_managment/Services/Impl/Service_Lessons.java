package project.learning_managment_system.learning_managment_system_dev.course_managment.Services.Impl;

import org.springframework.stereotype.Service;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Dto.Lessons_Dto;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Entities.Lessons;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Entities.Modules;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Exceptions.CustomesException.Lessons_Exception;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Mappers.Impl.Mapper_Lessons;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Repositories.Lessons_Repo;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Services.Service_Interface;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Service_Lessons implements Service_Interface<Lessons_Dto> {
    public Mapper_Lessons mapperLessons;
    public Lessons_Repo lessonsRepo;
    public Service_Lessons(Lessons_Repo repo)
    {
        this.mapperLessons=new Mapper_Lessons();
        this.lessonsRepo=repo;
    }


    @Override
    public List<Lessons_Dto> getAllData() {
        return this.lessonsRepo.findAll()
                .stream().map(mapperLessons::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Lessons_Dto getSingleData(int id) {
        return this.lessonsRepo.findById(id)
                .map(mapperLessons::toDto)
                .orElseThrow(()->new Lessons_Exception("Lesson not found"));
    }

    @Override
    public Lessons_Dto createData(Lessons_Dto data, int id) {
        Lessons lessons=this.mapperLessons.toEntity(data);
        lessons.setModule(Modules.builder()
                .id(id).build());
        return this.mapperLessons.toDto(this.lessonsRepo.save(lessons));
    }

    @Override
    public Lessons_Dto updateData(Lessons_Dto data, int id) {
        Lessons lessons=this.lessonsRepo.findById(id)
                .orElseThrow(()->new Lessons_Exception("Lessons not found"));
        this.mapperLessons.updateFields(data,lessons);
        return this.mapperLessons.toDto(this.lessonsRepo.save(lessons));
    }

    @Override
    public void deleteData(int id) {
          this.lessonsRepo.deleteById(id);
    }
    public List<Lessons_Dto> getLessonsByModules(int id ){
        return this.lessonsRepo.findByModule_Id(id)
                .stream().map(mapperLessons::toDto)
                .collect(Collectors.toList());
    }
}

