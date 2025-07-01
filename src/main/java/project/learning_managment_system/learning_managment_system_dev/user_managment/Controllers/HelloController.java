package project.learning_managment_system.learning_managment_system_dev.user_managment.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/student")
    @PreAuthorize("hasRole('student')")
    public String HelloStudent(){
        return "Hello Student";
    }
    @GetMapping("/teacher")
    @PreAuthorize("hasRole('teacher')")
    public String HelloTeacher(){
        return "Hello Teacher";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('admin')")
    public String HelloAdmin(){
        return "Hello Admin";
    }

}
