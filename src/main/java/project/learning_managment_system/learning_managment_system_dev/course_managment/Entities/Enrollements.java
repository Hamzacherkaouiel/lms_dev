package project.learning_managment_system.learning_managment_system_dev.course_managment.Entities;

import jakarta.persistence.*;
import lombok.*;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Student;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "enrollement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollemnt_id")
    public int enrollemnt_id;
    public LocalDate enrollmentDate;
    @ManyToOne
    @JoinColumn(name = "course_id")
    public Course course;
    @ManyToOne
    @JoinColumn(name = "id")
    public Student student;

}
