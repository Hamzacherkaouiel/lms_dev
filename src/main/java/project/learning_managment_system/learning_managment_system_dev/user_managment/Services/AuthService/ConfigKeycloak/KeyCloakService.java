package project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.ConfigKeycloak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.UserCreation;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.Exceptions.Assign_Exception;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.Exceptions.ClientUIID_Exception;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.Exceptions.Role_Exception;

@Service
public class KeyCloakService {
    @Autowired
    public KeyCloakOperation keyCloakOperation;

    public String createUser(UserCreation user) throws Assign_Exception, Role_Exception, ClientUIID_Exception {
        String client_uiid=this.keyCloakOperation.createUserInKeycloak(user.getMail(),user.getPassword(),user.getFirstname(),user.getLastname());
        this.keyCloakOperation.assignRoleToUser(client_uiid,user.getRole());
        return "USER CREATED";
    }
}
