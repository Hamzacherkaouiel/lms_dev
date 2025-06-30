package project.learning_managment_system.learning_managment_system_dev.user_managment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Admin;

public interface Admin_Repo extends JpaRepository<Admin,Integer> {

}
