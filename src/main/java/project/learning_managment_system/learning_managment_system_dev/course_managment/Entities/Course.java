package project.learning_managment_system.learning_managment_system_dev.course_managment.Entities;

import jakarta.persistence.*;
import lombok.*;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Teacher;

import java.util.List;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    public int course_id;
    public String title;
    public String description;
    public int capacity;
    @OneToMany(mappedBy = "course")
    public List<Modules> modulesList;
    @ManyToOne
    @JoinColumn(name = "id",nullable = false)
    public Teacher teacher;


}
