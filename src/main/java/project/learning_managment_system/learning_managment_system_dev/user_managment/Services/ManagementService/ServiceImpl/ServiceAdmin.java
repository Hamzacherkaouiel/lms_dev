package project.learning_managment_system.learning_managment_system_dev.user_managment.Services.ManagementService.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import project.learning_managment_system.learning_managment_system_dev.Config.JwtExtractor;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.Admin_Dto;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.Student_Dto;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Admin;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Teacher;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Repositories.Admin_Repo;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Repositories.Teacher_Repo;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.ManagementService.ServiceUser;
import project.learning_managment_system.learning_managment_system_dev.user_managment.mappers.Implementation.Admin_Mapper;
import project.learning_managment_system.learning_managment_system_dev.user_managment.mappers.Implementation.Teacher_Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceAdmin implements ServiceUser<Admin_Dto> {
    @Autowired
    public Admin_Repo adminRepo;
    @Autowired
    public Admin_Mapper adminMapper;
    @Override
    public List<Admin_Dto> getUsers() {
        return this.adminRepo.findAll()
                .stream().map(adminMapper::toDto)
                .collect(Collectors.toList());    }

    @Override
    public Admin_Dto getSingleUser(int id) {
        return this.adminRepo.findById(id)
                .map(adminMapper::toDto)
                .orElseThrow(()->new IllegalArgumentException("ADMIN NOT FOUND"));    }

    @Override
    public Admin_Dto updateUser(Admin_Dto user, int id) {
        Admin admin =this.adminRepo.findById(id).orElseThrow(()->new IllegalArgumentException("ADMIN NOT FOUND"));
        this.adminMapper.updateEntityFromDto(user,admin);
        return this.adminMapper.toDto(this.adminRepo.save(admin));    }

    @Override
    public void deleteUser(int id) {
       this.adminRepo.deleteById(id);
    }
    @Override
    public Admin_Dto getMyProfile(Jwt token) {
        JwtExtractor jwtExtractor = new JwtExtractor();
        return this.adminRepo.findByMail(jwtExtractor.extractClaim(token, "preferred_username"))
                .map(adminMapper::toDto).orElseThrow(() -> new IllegalArgumentException("ADMIN NOT FOUND"));
    }
}
