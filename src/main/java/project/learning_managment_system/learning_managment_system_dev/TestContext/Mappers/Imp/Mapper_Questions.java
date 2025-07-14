package project.learning_managment_system.learning_managment_system_dev.TestContext.Mappers.Imp;

import project.learning_managment_system.learning_managment_system_dev.TestContext.Dto.Questions_Dto;
import project.learning_managment_system.learning_managment_system_dev.TestContext.Entities.Questions;
import project.learning_managment_system.learning_managment_system_dev.TestContext.Mappers.Mapper_Interface;

public class Mapper_Questions implements Mapper_Interface<Questions_Dto, Questions> {
    @Override
    public Questions_Dto toDto(Questions entity) {
        return Questions_Dto.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public Questions toEntity(Questions_Dto dto) {
        return Questions.builder()
                .description(dto.getDescription())
                .build();
    }

    @Override
    public void updateEntity(Questions_Dto dto, Questions entity) {
       dto.setDescription(dto.getDescription()!=null? dto.getDescription() : entity.getDescription());
    }
}
