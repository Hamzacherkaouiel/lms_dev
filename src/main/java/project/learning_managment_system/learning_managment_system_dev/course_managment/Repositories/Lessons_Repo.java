package project.learning_managment_system.learning_managment_system_dev.course_managment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Entities.Course;
import project.learning_managment_system.learning_managment_system_dev.course_managment.Entities.Lessons;

import java.util.List;

public interface Lessons_Repo extends JpaRepository<Lessons,Integer> {
<<<<<<< HEAD
    List<Lessons> findByModule_Id(int id);
=======
    List<Lessons> findByModule_Id(int id); // doit refléter EXACTEMENT le nom du champ Java
>>>>>>> 61b3e0d7e0ee280a869724938ece2ed168e77015

}
