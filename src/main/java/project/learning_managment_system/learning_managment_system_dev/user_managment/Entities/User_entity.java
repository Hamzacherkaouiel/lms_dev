package project.learning_managment_system.learning_managment_system_dev.user_managment.Entities;

import jakarta.persistence.*;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
public abstract class User_entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "name", nullable = false)
    public String name;
    @Column(name = "email",nullable = false,unique = true)
    public String mail;

}
