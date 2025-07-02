package project.learning_managment_system.learning_managment_system_dev.NotificationContext.KeycloakNotification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Dto.UserDTO;

@Service
public class Consummer {
    @Autowired
    KeycloakSync sync;
    @KafkaListener(topics = "keycloak", groupId = "keycloak-id")
    public void consumme(UserDTO userDTO){
        System.out.println("USEERRRRRRRRRRRR   "+userDTO.getMail());
        this.sync.syncUser(userDTO);
    }


}
