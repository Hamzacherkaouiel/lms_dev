package project.learning_managment_system.learning_managment_system_dev.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.beans.Customizer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Config_Security {
    @Autowired
    AuthCoverter authCoverter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(customizer->customizer.disable())
                .authorizeHttpRequests(request->
                        request
                                .requestMatchers("/sign/")
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2->
                        oauth2.jwt(jwt->
                                jwt.jwtAuthenticationConverter(authCoverter)
                                )
                        );
        return httpSecurity.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/sign/");
    }

}
