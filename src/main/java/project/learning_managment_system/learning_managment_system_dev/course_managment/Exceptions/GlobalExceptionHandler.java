package project.learning_managment_system.learning_managment_system_dev.course_managment.Exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Exceptions.CustomesException.Course_Exception;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Exceptions.CustomesException.Lessons_Exception;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Exceptions.CustomesException.Module_Exception;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Exceptions.CustomesException.InvalidUser;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Exceptions.CustomesException.RoleNotFound;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Exceptions.CustomesException.UserNotFound;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({Course_Exception.class})
    public ResponseEntity<String> handleCourseException(Course_Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
    @ExceptionHandler({Lessons_Exception.class})
    public ResponseEntity<String> handleLessonsException(Lessons_Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler({Module_Exception.class})
    public ResponseEntity<String> handleModulesException(RoleNotFound e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccess(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur d'accès à la base de données");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne : " + ex.getMessage());
    }
}
