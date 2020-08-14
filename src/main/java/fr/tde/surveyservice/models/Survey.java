package fr.tde.surveyservice.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "survey")
@Data
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String questions;

    @Column
    private Long idGroup;

    @ElementCollection
    private Set<String> proposals;


}
