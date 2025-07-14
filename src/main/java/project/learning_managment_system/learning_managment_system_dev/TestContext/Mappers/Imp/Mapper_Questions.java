package project.learning_managment_system.learning_managment_system_dev.TestContext.Mappers.Imp;

import project.learning_managment_system.learning_managment_system_dev.TestContext.Dto.Questions_Dto;
import project.learning_managment_system.learning_managment_system_dev.TestContext.Entities.Questions;
import project.learning_managment_system.learning_managment_system_dev.TestContext.Mappers.Mapper_Interface;

import java.util.stream.Collectors;

public class Mapper_Questions implements Mapper_Interface<Questions_Dto, Questions> {
    public Mapper_Answers mapperAnswers;
    public Mapper_Questions(){
        this.mapperAnswers=new Mapper_Answers();
    }
    @Override
    public Questions_Dto toDto(Questions entity) {
        return Questions_Dto.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .scoreQuestion(entity.getScoreQuestion())
                .options(entity.getAnswerOptions().stream()
                        .map(mapperAnswers::toDto)
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public Questions toEntity(Questions_Dto dto) {
        return Questions.builder()
                .description(dto.getDescription())
                .scoreQuestion(dto.getScoreQuestion())
                .build();
    }

    @Override
    public void updateEntity(Questions_Dto dto, Questions entity) {
        entity.setScoreQuestion(dto.getScoreQuestion());
       entity.setDescription(dto.getDescription()!=null? dto.getDescription() : entity.getDescription());
    }
}
