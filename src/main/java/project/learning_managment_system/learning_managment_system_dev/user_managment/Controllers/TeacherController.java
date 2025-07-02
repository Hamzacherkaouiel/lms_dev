package project.learning_managment_system.learning_managment_system_dev.user_managment.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.Student_Dto;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.Teacher_Dto;
import project.learning_managment_system.learning_managment_system_dev.user_managment.KafkaConfig.Producer;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.ManagementService.ServiceImpl.ServiceStudent;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.ManagementService.ServiceImpl.ServiceTeacher;

@RestController
@RequestMapping("/Teacher")
public class TeacherController extends ManagmentController<Teacher_Dto> {
    @Autowired
    Producer producer;
    public TeacherController(ServiceTeacher serviceTeacher){
        this.serviceUser=serviceTeacher;
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin') or hasRole('teacher')")
    public ResponseEntity<Teacher_Dto> updateUser(@RequestBody Teacher_Dto user, int id){
        this.producer.syncData(user);
        return super.updateUser(user,id);
    }
    @Override
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('teacher')")
        public ResponseEntity<Teacher_Dto> getProfile(@AuthenticationPrincipal Jwt jwt){
        return super.getProfile(jwt);
    }
}
