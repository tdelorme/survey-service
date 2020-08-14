package fr.tde.surveyservice.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonneDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String company;

    private Long idUser;

    private GroupsDto groups;
}
