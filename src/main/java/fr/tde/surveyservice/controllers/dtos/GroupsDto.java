package fr.tde.surveyservice.controllers.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupsDto {

    private Long id;

    private String name;

    private Set<PersonneDto> listPersonne;

}
