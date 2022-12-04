package org.example.pokedex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.pokedex.model.Pokemon;
import org.example.pokedex.service.PokemonService;
import org.example.pokedex.service.TranslationService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class PokemonServiceTest {

    @Autowired
    private PokemonService pokemonService;

    @MockBean
    private TranslationService translationService;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        Mockito.when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon-species/mewtwo", ObjectNode.class))
                .thenReturn(readObjectFromFile("mewtwo.json", ObjectNode.class));

        Mockito.when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon-species/golbat", ObjectNode.class))
                .thenReturn(readObjectFromFile("golbat.json", ObjectNode.class));

        Mockito.when(translationService.getYodaTranslation(anyString()))
                .thenReturn("yoda description");

        Mockito.when(translationService.getShakespeareTranslation(Mockito.any(String.class)))
                .thenReturn("shakespeare description");
    }

    @Test
    public void getPokemonTest(){
        Pokemon pokemon = pokemonService.getPokemon("mewtwo");
        assertThat(pokemon.getHabitat()).isEqualTo("rare");
        assertThat(pokemon.getIsLegendary()).isEqualTo(false);
        assertThat(pokemon.getDescription()).isEqualTo("Custom modified text from mock");
        assertThat(pokemon.getName()).isEqualTo("mewtwo");
    }

    @Test
    public void getTranslatedPokemon_with_type_cave(){
        Pokemon pokemon = pokemonService.getTranslatedPokemon("golbat");
        assertThat(pokemon.getHabitat()).isEqualTo("cave");
        assertThat(pokemon.getIsLegendary()).isEqualTo(false);
        assertThat(pokemon.getDescription()).isEqualTo("yoda description");
        assertThat(pokemon.getName()).isEqualTo("golbat");
    }

    @Test
    public void getTranslatedPokemon_normal(){
        Pokemon pokemon = pokemonService.getTranslatedPokemon("mewtwo");
        assertThat(pokemon.getHabitat()).isEqualTo("rare");
        assertThat(pokemon.getIsLegendary()).isEqualTo(false);
        assertThat(pokemon.getDescription()).isEqualTo("shakespeare description");
        assertThat(pokemon.getName()).isEqualTo("mewtwo");
    }

    @Test
    public void getTranslatedPokemon_translation_service_has_problems(){
        // When translation service has problems, it uses default description
        Mockito.when(translationService.getShakespeareTranslation(Mockito.any(String.class)))
                .thenThrow(new RuntimeException("problem with translation api"));
        Pokemon pokemon = pokemonService.getTranslatedPokemon("mewtwo");
        assertThat(pokemon.getHabitat()).isEqualTo("rare");
        assertThat(pokemon.getIsLegendary()).isEqualTo(false);
        assertThat(pokemon.getDescription()).isEqualTo("Custom modified text from mock");
        assertThat(pokemon.getName()).isEqualTo("mewtwo");
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
