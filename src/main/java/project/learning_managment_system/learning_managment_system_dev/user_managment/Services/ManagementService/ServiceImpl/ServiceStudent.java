package project.learning_managment_system.learning_managment_system_dev.user_managment.Services.ManagementService.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import project.learning_managment_system.learning_managment_system_dev.Config.JwtExtractor;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.Student_Dto;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Student;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Repositories.Student_Repo;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.ManagementService.ServiceUser;
import project.learning_managment_system.learning_managment_system_dev.user_managment.mappers.Implementation.Student_Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceStudent implements ServiceUser<Student_Dto> {
    @Autowired
    public Student_Repo studentRepo;
    @Autowired
    public Student_Mapper studentMapper;

    @Override
    public List<Student_Dto> getUsers() {
        return this.studentRepo.findAll()
                .stream().map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Student_Dto getSingleUser(int id) {
        return this.studentRepo.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(()->new IllegalArgumentException("STUDENT NOT FOUND"));
    }

    @Override
    public Student_Dto updateUser(Student_Dto user, int id) {
        Student student =this.studentRepo.findById(id).orElseThrow(()->new IllegalArgumentException("STUDENT NOT FOUND"));
        this.studentMapper.updateEntityFromDto(user,student);
        return this.studentMapper.toDto(this.studentRepo.save(student));
    }

    @Override
    public void deleteUser(int id) {
       this.studentRepo.deleteById(id);
    }

    @Override
    public Student_Dto getMyProfile(Jwt token) {
        JwtExtractor jwtExtractor = new JwtExtractor();
        return this.studentRepo.findByMail(jwtExtractor.extractClaim(token, "preferred_username"))
                .map(studentMapper::toDto).orElseThrow(() -> new IllegalArgumentException("STUDENT NOT FOUND"));
    }
}
