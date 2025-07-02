package project.learning_managment_system.learning_managment_system_dev.user_managment.Services.ManagementService.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import project.learning_managment_system.learning_managment_system_dev.Config.JwtExtractor;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.Student_Dto;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.Teacher_Dto;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Student;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Teacher;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Repositories.Student_Repo;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Repositories.Teacher_Repo;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.ManagementService.ServiceUser;
import project.learning_managment_system.learning_managment_system_dev.user_managment.mappers.Implementation.Student_Mapper;
import project.learning_managment_system.learning_managment_system_dev.user_managment.mappers.Implementation.Teacher_Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceTeacher implements ServiceUser<Teacher_Dto> {
    @Autowired
    public Teacher_Repo teacherRepo;
    @Autowired
    public Teacher_Mapper teacherMapper;

    @Override
    public List<Teacher_Dto> getUsers() {
         return this.teacherRepo.findAll()
                .stream().map(teacherMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Teacher_Dto getSingleUser(int id) {
        return this.teacherRepo.findById(id)
                .map(teacherMapper::toDto)
                .orElseThrow(()->new IllegalArgumentException("TEACHER NOT FOUND"));
    }

    @Override
    public Teacher_Dto updateUser(Teacher_Dto user, int id) {
        Teacher teacher =this.teacherRepo.findById(id).orElseThrow(()->new IllegalArgumentException("TEACHER NOT FOUND"));
        this.teacherMapper.updateEntityFromDto(user,teacher);
        return this.teacherMapper.toDto(this.teacherRepo.save(teacher));
    }

    @Override
    public void deleteUser(int id) {
        this.teacherRepo.deleteById(id);
    }
    @Override
    public Teacher_Dto getMyProfile(Jwt token) {
        JwtExtractor jwtExtractor = new JwtExtractor();
        return this.teacherRepo.findByMail(jwtExtractor.extractClaim(token, "preferred_username"))
                .map(teacherMapper::toDto).orElseThrow(() -> new IllegalArgumentException("TEACHER NOT FOUND"));
    }
}
