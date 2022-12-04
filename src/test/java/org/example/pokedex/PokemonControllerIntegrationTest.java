package org.example.pokedex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PokedexApplication.class)
@AutoConfigureMockMvc
public class PokemonControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenMewTwo_getPokemon_success() throws Exception {

        mvc.perform(get("/pokemon/mewtwo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("mewtwo"))
                .andExpect(jsonPath("$.isLegendary").value(false))
                .andExpect(jsonPath("$.description").value("It was created by\na scientist after\nyears of horrific\fgene splicing and\nDNA engineering\nexperiments."))
                .andExpect(jsonPath("$.habitat").value("rare"));
    }

    @Test
    public void givenGolbat_getTranslatedPokemon_with_habitat_cave_success() throws Exception {

        mvc.perform(get("/pokemon/translated/golbat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("golbat"))
                .andExpect(jsonPath("$.isLegendary").value(false))
                .andExpect(jsonPath("$.description").value("Once it strikes,It will not stop draining energy from the victim even if it gets too heavy to fly."))
                .andExpect(jsonPath("$.habitat").value("cave"));
    }

}
