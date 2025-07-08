package project.learning_managment_system.learning_managment_system_dev.course_managment.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Dto.Course_Dto;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Dto.Lessons_Dto;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Entities.Lessons;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Services.Impl.Service_Course;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Services.Impl.Service_Lessons;

import java.util.List;
@RestController
@RequestMapping("/lessons")
public class Lessons_Controller {
    public Service_Lessons serviceLessons;
    public Lessons_Controller(Service_Lessons lessons){
        this.serviceLessons=lessons;
    }
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('teacher','admin','student')")
    public ResponseEntity<List<Lessons_Dto>> getLessons(){
        return ResponseEntity.ok(this.serviceLessons.getAllData());
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('teacher','admin','student')")
    public ResponseEntity<Lessons_Dto> getSingleLesson(@PathVariable int id){
        return ResponseEntity.ok(this.serviceLessons.getSingleData(id));
    }
    @GetMapping("/module/{id}")
    @PreAuthorize("hasRole('teacher')")
    public ResponseEntity<List<Lessons_Dto>> getLessonsByModules(@PathVariable int id ){
        return ResponseEntity.ok(this.serviceLessons.getLessonsByModules(id));
    }
    @PostMapping("/module/{id}")
    @PreAuthorize("hasRole('teacher')")
    public ResponseEntity<Lessons_Dto> createLessons(@RequestBody Lessons_Dto lessonsDto, @PathVariable int id){
        return ResponseEntity.status(201)
                .body(this.serviceLessons.createData(lessonsDto,id));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('teacher')")
    public ResponseEntity<Lessons_Dto> updateLessons(@RequestBody Lessons_Dto lessonsDto,@PathVariable int id){
        return ResponseEntity.ok(this.serviceLessons.updateData(lessonsDto,id));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('teacher')")
    public ResponseEntity<String> deleteLessons(@PathVariable int id){
        this.serviceLessons.deleteData(id);
        return ResponseEntity.status(204).body("COURSE DELETED");
    }
}
