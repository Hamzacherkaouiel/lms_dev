package project.learning_managment_system.learning_managment_system_dev.NotificationContext.KeycloakNotification;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class kyecloakConfig2 {
    @Value("${keycloak.server-url}")
    private String keycloakBaseUrl;
    @Value("${keycloak.admin.username}")
    private String adminUsername;
    @Value("${keycloak.admin.password}")
    private String adminPassword;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.realm}")
    private String realm;
    @Bean
    public Keycloak keycloakClient(){
        return KeycloakBuilder.builder()
                .clientId("admin-cli")
                .serverUrl(keycloakBaseUrl)
                .realm("master")
                .username("admin")
                .password("admin")
                .build();
    }

}
