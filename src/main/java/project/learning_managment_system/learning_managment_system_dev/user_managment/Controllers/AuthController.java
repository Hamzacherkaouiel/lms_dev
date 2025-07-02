package project.learning_managment_system.learning_managment_system_dev.user_managment.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.UserCreation;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.UserDTO;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.Authentication_Service;

@RestController
@RequestMapping("/sign")
public class AuthController {
    @Autowired
    public Authentication_Service service;
    @PostMapping("/")
    public ResponseEntity<UserDTO> createStudent(@RequestBody UserCreation userCreation){
        UserDTO user=this.service.creaUser(userCreation);
        return ResponseEntity.status(201).body(user);
    }
    @PostMapping("/create-user")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreation userCreation){
        UserDTO user=this.service.orchestrator(userCreation);
        return ResponseEntity.status(201).body(user);
    }
}
