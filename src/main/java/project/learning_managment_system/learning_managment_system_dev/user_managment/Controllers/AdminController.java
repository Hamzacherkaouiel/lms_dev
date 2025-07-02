package project.learning_managment_system.learning_managment_system_dev.user_managment.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.Admin_Dto;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.Student_Dto;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.Teacher_Dto;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.ManagementService.ServiceImpl.ServiceAdmin;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.ManagementService.ServiceImpl.ServiceTeacher;

import java.util.List;

@RestController
@RequestMapping("/Admin")
public class AdminController extends ManagmentController<Admin_Dto> {
    public AdminController(ServiceAdmin serviceAdmin){
        this.serviceUser=serviceAdmin;
    }
    @Override
    @GetMapping("/")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<Admin_Dto>> getUsers(){
        return super.getUsers();
    }
    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity<Admin_Dto> getSingleUser(@PathVariable int id){
        return super.getSingleUser(id);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity<Admin_Dto> updateUser(@RequestBody Admin_Dto user, int id){
        return super.updateUser(user,id);
    }
    @Override
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity<Admin_Dto> getProfile(@AuthenticationPrincipal Jwt jwt){
        return super.getProfile(jwt);
    }
}
