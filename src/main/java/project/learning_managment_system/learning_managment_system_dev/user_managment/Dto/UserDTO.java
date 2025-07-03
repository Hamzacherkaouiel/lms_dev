package project.learning_managment_system.learning_managment_system_dev.user_managment.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @Email(message = "email format invalid")
    @NotBlank(message = "email is mandatory")
    public String mail;
}
