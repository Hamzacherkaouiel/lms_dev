package project.learning_managment_system.learning_managment_system_dev.course_managment.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lessons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int lessons_id;
    public String description;
    @ManyToOne
    @JoinColumn(name = "module_id",nullable = false)
    public Modules  module;
    public String s3Url;
    public String objectKey;


}
