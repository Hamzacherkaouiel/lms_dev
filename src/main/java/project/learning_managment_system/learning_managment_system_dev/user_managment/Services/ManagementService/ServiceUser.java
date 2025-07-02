package project.learning_managment_system.learning_managment_system_dev.user_managment.Services.ManagementService;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface ServiceUser<T> {
    public List<T> getUsers();
    public T getSingleUser(int id);
    public T updateUser(T user,int id);
    public void deleteUser(int id);
    public T getMyProfile(Jwt token);
}
