package project.learning_managment_system.learning_managment_system_dev.user_managment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.learning_managment_system.learning_managment_system_dev.user_managment.Entities.Teacher;

public interface Teacher_Repo extends JpaRepository<Teacher,Integer> {
}
