package fr.tde.surveyservice.services;

import fr.tde.surveyservice.controllers.dtos.GroupsDto;
import fr.tde.surveyservice.controllers.dtos.MailRequest;
import fr.tde.surveyservice.controllers.dtos.PersonneDto;
import fr.tde.surveyservice.controllers.dtos.UserDto;
import fr.tde.surveyservice.exceptions.SurveyException;
import fr.tde.surveyservice.models.Survey;
import fr.tde.surveyservice.repositories.SurveyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class SurveyService {

    @Autowired
    private SurveyRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();

    public Survey upsertSurvey ( Survey survey ) throws SurveyException {

        Survey surveySaved = repository.save( survey );

        ResponseEntity<GroupsDto> responseGroups = restTemplate.postForEntity("URLTOGATEWAYGroups/"+surveySaved.getIdGroup(), null, GroupsDto.class);

        if(!responseGroups.getStatusCode().is2xxSuccessful()){
            log.error("Error : the http status isn't 200 but is " + responseGroups.getStatusCode());
        }
        else if (responseGroups.getBody() != null) {
            GroupsDto groups = responseGroups.getBody();
            Set<PersonneDto> personnes = groups.getListPersonne();

            MailRequest mailRequest = new MailRequest();

            List<String> to = new ArrayList<>();

            personnes.forEach(personne -> {
                ResponseEntity<UserDto> responseUser = restTemplate.postForEntity("URLTOGATEWAYUSer/"+personne.getIdUser(), null, UserDto.class);
                if(!responseUser.getStatusCode().is2xxSuccessful()){
                    log.error("Error : the http status isn't 200 but is " + responseUser.getStatusCode());
                }
                else if (responseUser.getBody() != null) {
                    UserDto user = responseUser.getBody();
                    to.add(user.getEmail());
                }
            });

            mailRequest.setTo(to.stream().toArray(String[]::new));
            mailRequest.setSubject("Un nouveau sondage a été envoyé");
            mailRequest.setText("Bonjour, " +
                    "\r\rUn nouveau sondage a été envoyé dans votre groupe." +
                    "\r\rPour voir les propositions, veuillez suivre le lien : -lien- . " +
                    "\r\rMerci et à bientôt sur OuCeKonMange !");

            ResponseEntity<Boolean> responseMail = restTemplate.postForEntity("URLTOGATEWAYMail", mailRequest, Boolean.class);
            if (!responseMail.getStatusCode().is2xxSuccessful()) {
                log.error("Error : the http status isn't 200, but is : " + responseMail.getStatusCode());
            } else if (responseMail.getBody() != null && !responseMail.getBody()) {
                log.error("Error : the mail can't be send");
            }
        }
        else {
            throw new SurveyException("You're alone bro !");
        }

        return surveySaved;
    }

}
