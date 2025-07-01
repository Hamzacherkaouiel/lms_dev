package project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.UserCreation;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.UserDTO;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Admin;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Student;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Teacher;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Repositories.Admin_Repo;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Repositories.Student_Repo;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Repositories.Teacher_Repo;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.Exceptions.Assign_Exception;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.Exceptions.ClientUIID_Exception;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.Exceptions.Role_Exception;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.ConfigKeycloak.KeyCloakService;
import project.learning_managment_system.learning_managment_system_dev.user_managment.mappers.Implementation.Admin_Mapper;
import project.learning_managment_system.learning_managment_system_dev.user_managment.mappers.Implementation.Student_Mapper;
import project.learning_managment_system.learning_managment_system_dev.user_managment.mappers.Implementation.Teacher_Mapper;

@Service
public class Authentication_Service {
    @Autowired
    public Student_Mapper studentMapper;
    @Autowired
    public Admin_Mapper adminMapper;
    @Autowired
    public Teacher_Mapper teacherMapper;
    @Autowired
    public Student_Repo studentRepo;
    @Autowired
    public Teacher_Repo teacherRepo;
    @Autowired
    public Admin_Repo adminRepo;
    @Autowired
    public KeyCloakService keyCloakService;
    private BCryptPasswordEncoder bCrypt=new BCryptPasswordEncoder(12);

    public UserDTO handler(UserCreation userCreation){
        if(userCreation.getRole()=="student"){
            return this.createStudent(userCreation);
        }
        return this.createPrivateUser(userCreation);
    }
    public UserDTO createStudent(UserCreation userCreation)  {
         try {
             this.keyCloakService.createUser(userCreation);
             userCreation.setPassword(this.bCrypt.encode(userCreation.getPassword()));
             Student student=this.studentRepo.save(this.studentMapper.Creation(userCreation));
             return this.studentMapper.toDto(student);
         }
         catch (Assign_Exception | Role_Exception | ClientUIID_Exception e){
             throw  new IllegalArgumentException(e.getMessage());
         }
    }
    public UserDTO createPrivateUser(UserCreation userCreation){

        try {
            this.keyCloakService.createUser(userCreation);
            userCreation.setPassword(this.bCrypt.encode(userCreation.getPassword()));
            switch (userCreation.getRole()){
                case "admin":
                    Admin admin=this.adminRepo.save(this.adminMapper.Creation(userCreation));
                    return this.adminMapper.toDto(admin);
                case "teacher":
                    Teacher teacher=this.teacherRepo.save(this.teacherMapper.Creation(userCreation));
                    return this.teacherMapper.toDto(teacher);
                default:
                    return null;
            }
        }
        catch (Assign_Exception | Role_Exception | ClientUIID_Exception e){
            throw  new IllegalArgumentException(e.getMessage());
        }
    }
}
