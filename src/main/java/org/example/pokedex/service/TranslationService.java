package org.example.pokedex.service;

import org.example.pokedex.model.yoda.TranslationRequest;
import org.example.pokedex.model.yoda.TranslationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TranslationService {

    @Autowired
    RestTemplate restTemplate;

    public String getYodaTranslation(String text){
        TranslationRequest req = TranslationRequest.builder()
                .text(text)
                .build();
        TranslationResponse response = restTemplate.postForObject(
                "https://api.funtranslations.com/translate/yoda",
                req,
                TranslationResponse.class);
        return response.getContents().getTranslated();
    }

    public String getShakespeareTranslation(String text){
        TranslationRequest req = TranslationRequest.builder()
                .text(text)
                .build();
        TranslationResponse response = restTemplate.postForObject(
                "https://api.funtranslations.com/translate/shakespeare",
                req,
                TranslationResponse.class);
        return response.getContents().getTranslated();
    }
}
