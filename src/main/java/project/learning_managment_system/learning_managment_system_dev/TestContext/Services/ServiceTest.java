package project.learning_managment_system.learning_managment_system_dev.TestContext.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.learning_managment_system.learning_managment_system_dev.TestContext.Repository.Test_Repo;

@Service
public class ServiceTest {
    @Autowired
    public Test_Repo testRepo;

}
