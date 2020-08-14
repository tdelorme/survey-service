package fr.tde.surveyservice.controllers;

import fr.tde.surveyservice.models.Survey;
import fr.tde.surveyservice.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/survey")
public class SurveyController {

    @Autowired
    private SurveyService service;

    @PostMapping("/upsert")
    public ResponseEntity<Survey> upsertSurvey(@RequestBody  Survey survey) throws Exception {
        return ResponseEntity.ok(service.upsertSurvey(survey));
    }

}
