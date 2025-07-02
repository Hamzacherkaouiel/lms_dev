package project.learning_managment_system.learning_managment_system_dev.NotificationContext.KeycloakNotification;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.UserDTO;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.ConfigKeycloak.KeycloakConfig;

import java.util.List;

@Service
public class KeycloakSync {
    @Value("${keycloak.realm}")
    private String realm;
    @Autowired
    public Keycloak keycloakConfig;

    private UserRepresentation getUserByMail(String mail){

        List<UserRepresentation> users = this.keycloakConfig.realm(realm).users().search(mail,0,1);

        if (users == null || users.isEmpty()) {
            throw new RuntimeException("Aucun utilisateur trouvé avec l'e-mail : " + mail);
        }
        return users.getFirst();
    }

    public void syncUser(UserDTO userDTO){
        try {
            UserRepresentation user = this.getUserByMail(userDTO.getMail());
            updateAttributes(userDTO, user);
            this.keycloakConfig.realm(realm)
                    .users().get(user.getId())
                    .update(user);
        } catch (RuntimeException e) {
            System.err.println("Erreur de synchronisation Keycloak: " + e.getMessage());
        }
    }


    private void updateAttributes(UserDTO userDTO,UserRepresentation user){
        user.setEmail(userDTO.getMail()!=null? userDTO.getMail() : user.getEmail());
        user.setFirstName(userDTO.getFirstname()!=null? userDTO.getFirstname() : user.getFirstName());
        user.setLastName(userDTO.getLastname()!=null? userDTO.getLastname() : user.getLastName());

    }




}
