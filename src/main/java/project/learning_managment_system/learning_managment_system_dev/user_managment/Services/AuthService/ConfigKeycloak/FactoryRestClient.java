package project.learning_managment_system.learning_managment_system_dev.user_managment.Services.AuthService.ConfigKeycloak;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class FactoryRestClient {
    @Bean
    public RestTemplate client(){
        return new RestTemplate();
    }
}
