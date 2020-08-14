package fr.tde.surveyservice.repositories;

import fr.tde.surveyservice.models.Survey;
import org.springframework.data.repository.CrudRepository;

public interface SurveyRepository extends CrudRepository<Survey, Long> {
}
