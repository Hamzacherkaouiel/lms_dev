package project.learning_managment_system.learning_managment_system_dev.user_managment.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Student extends User_entity {
    @ColumnDefault("0")
    public Integer progress;
}
