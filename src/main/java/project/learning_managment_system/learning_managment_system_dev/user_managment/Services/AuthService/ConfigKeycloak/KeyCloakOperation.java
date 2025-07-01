package project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.ConfigKeycloak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.Exceptions.Assign_Exception;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.Exceptions.ClientUIID_Exception;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.Exceptions.Role_Exception;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class KeyCloakOperation {
    @Autowired
    KeycloakConfig keycloakClient;
    @Autowired
    RestTemplate restTemplate;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.server-url}")
    private String keycloakBaseUrl;
    public String createUserInKeycloak( String email, String password,String firstname,String lastname) {
        String token = this.keycloakClient.getAdminAccessToken();
        String url = keycloakBaseUrl + "/admin/realms/" + realm + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, Object> userPayload = Map.of(
                "username", email,
                "enabled", true,
                "emailVerified", true,
                "email", email,
                "firstName",firstname,
                "lastName",lastname,
                "credentials", List.of(Map.of(
                        "type", "password",
                        "value", password,
                        "temporary", false
                ))
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(userPayload, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {

            return getUserIdByUsername(email, token).orElse(null);
        } else {
            return null;
        }
    }

    private Optional<String> getUserIdByUsername(String username, String token) {
        String url = keycloakBaseUrl + "/admin/realms/" + realm + "/users?username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        if (response.getStatusCode().is2xxSuccessful() && !response.getBody().isEmpty()) {
            Map<String, Object> user = (Map<String, Object>) response.getBody().get(0);
            return Optional.of((String) user.get("id"));
        }
        return Optional.empty();
    }

    public void assignRoleToUser(String userId, String roleName) throws Assign_Exception,Role_Exception,ClientUIID_Exception {
        String token = this.keycloakClient.getAdminAccessToken();
        String clientUUID = getClientUUID(token);

        String url = keycloakBaseUrl + "/admin/realms/" + realm + "/users/" + userId +
                "/role-mappings/clients/" + clientUUID;

        Map<String, Object> roleRep = getClientRoleRepresentation(roleName, token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<Map<String, Object>>> entity = new HttpEntity<>(List.of(roleRep), headers);

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new Assign_Exception("L'attribution du rôle a échoué : " + response.getStatusCode());
        }
    }



    private String getClientUUID(String token) throws ClientUIID_Exception {
        String url = keycloakBaseUrl + "/admin/realms/" + realm + "/clients";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        for (Object clientObj : response.getBody()) {
            Map<String, Object> client = (Map<String, Object>) clientObj;
            System.out.println("Client: " + client);
            if (client.get("clientId").equals(clientId)) {
                System.out.println("MATCHED clientId: " + client.get("clientId") + " UUID: " + client.get("id"));
                return (String) client.get("id");
            }
        }
        throw new ClientUIID_Exception("Client not found: " + clientId);
    }


    private Map<String, Object> getClientRoleRepresentation(String roleName, String token) throws Role_Exception,ClientUIID_Exception {


            String clientUUID = getClientUUID(token);
            String url = keycloakBaseUrl + "/admin/realms/" + realm + "/clients/" + clientUUID + "/roles/" + roleName;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new Role_Exception("Le rôle " + roleName + " est introuvable dans le client.");
            }



    }





}
