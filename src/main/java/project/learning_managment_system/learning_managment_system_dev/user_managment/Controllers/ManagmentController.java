package project.learning_managment_system.learning_managment_system_dev.user_managment.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.ManagementService.ServiceUser;

import java.util.List;

public abstract class ManagmentController<T> {
    public ServiceUser<T> serviceUser;
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('admin','teacher','student')")
    public ResponseEntity<List<T>> getUsers(){
        return ResponseEntity.ok(this.serviceUser.getUsers());
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin','teacher','student')")
    public ResponseEntity<T> getSingleUser(@PathVariable int id){
        return ResponseEntity.ok(this.serviceUser.getSingleUser(id));
    }
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('admin','teacher','student')")
    public ResponseEntity<T> getProfile(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.ok(this.serviceUser.getMyProfile(jwt));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin','teacher','student')")
    public ResponseEntity<T> updateUser(@RequestBody T user,@PathVariable int id){
        return ResponseEntity.ok(this.serviceUser.updateUser(user,id));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity deleteUser(@PathVariable int id){
        return ResponseEntity.status(204).build();
    }
}
