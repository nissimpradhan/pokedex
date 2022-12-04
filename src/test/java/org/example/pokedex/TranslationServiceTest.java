package org.example.pokedex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.pokedex.model.yoda.TranslationRequest;
import org.example.pokedex.model.yoda.TranslationResponse;
import org.example.pokedex.service.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class TranslationServiceTest {

    @Autowired
    private TranslationService translationService;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        Mockito.when(restTemplate.postForObject(eq("https://api.funtranslations.com/translate/yoda"), any(TranslationRequest.class), any()))
                .thenReturn(readObjectFromFile("yoda.json", TranslationResponse.class));

        Mockito.when(restTemplate.postForObject(eq("https://api.funtranslations.com/translate/shakespeare"), any(TranslationRequest.class), any()))
                .thenReturn(readObjectFromFile("shakespeare.json", TranslationResponse.class));
    }

    @Test
    public void getYodaTranslation_success(){
        String translatedText = translationService.getYodaTranslation("Master Anakin has lost a planet");
        assertThat(translatedText).isEqualTo("Lost a planet,  master anakin has");
    }

    @Test
    public void getShakespeareTranslation_success(){
        String translatedText = translationService.getShakespeareTranslation("Master Anakin has lost a planet");
        assertThat(translatedText).isEqualTo("Master anakin hath did lose a planet");
    }

    private <T> T readObjectFromFile(final String fileName, final Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(this.getClass().getClassLoader().getResourceAsStream(fileName), clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
