package project.learning_managment_system.learning_managment_system_dev.user_managment.Dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class UserDTO {
    public int id;
    public String firstname;
    public String lastname;
    public String mail;
}
