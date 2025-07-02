package project.learning_managment_system.learning_managment_system_dev.user_managment.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.Student_Dto;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.ManagementService.ServiceImpl.ServiceStudent;

@RestController
@RequestMapping("/Student")
public class StudentController extends ManagmentController<Student_Dto>{
    public StudentController(ServiceStudent serviceStudent){
        this.serviceUser=serviceStudent;
    }
    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin') or hasRole('student')")
    public ResponseEntity<Student_Dto> updateUser(@RequestBody Student_Dto user, int id){
        return super.updateUser(user,id);
    }
    @Override
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('student')")
    public ResponseEntity<Student_Dto> getProfile(@AuthenticationPrincipal Jwt jwt){
        return super.getProfile(jwt);
    }


}
