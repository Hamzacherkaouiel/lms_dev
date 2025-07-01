package project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.ConfigKeycloak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class KeycloakConfig {
    @Value("${keycloak.server-url}")
    private String keycloakBaseUrl;

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    public RestTemplate restTemplate=new RestTemplate();
    public String getAdminAccessToken() {
        String url = keycloakBaseUrl + "/realms/master/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=admin-cli" +
                "&username=" + adminUsername +
                "&password=" + adminPassword +
                "&grant_type=password";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        return (String) response.getBody().get("access_token");
    }



}
