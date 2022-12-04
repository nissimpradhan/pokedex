package org.example.pokedex.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.pokedex.configs.ExternalApiException;
import org.example.pokedex.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PokemonService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TranslationService translationService;

    public Pokemon getPokemon(String pokemonName){
        ObjectNode rawPokemon = restTemplate.getForObject(
                "https://pokeapi.co/api/v2/pokemon-species/" + pokemonName, ObjectNode.class);
        try{
            Pokemon pokemon = new Pokemon();
            pokemon.setName(rawPokemon.get("name").textValue());
            String description = rawPokemon.get("flavor_text_entries").get(0).get("flavor_text").textValue();
            pokemon.setDescription(description);
            pokemon.setHabitat(rawPokemon.get("habitat").get("name").textValue());
            pokemon.setIsLegendary(Boolean.parseBoolean(rawPokemon.get("is_legendary").textValue()));
            return pokemon;
        } catch (Exception ex){
            throw new ExternalApiException(ex.getMessage());
        }
    }

    public Pokemon getTranslatedPokemon(String pokemonName){
        Pokemon pokemon = getPokemon(pokemonName);
        try{
            if(pokemon.getHabitat().equals("cave") || pokemon.getIsLegendary()){
                pokemon.setDescription(translationService.getYodaTranslation(pokemon.getDescription()));
            } else {
                pokemon.setDescription(translationService.getShakespeareTranslation(pokemon.getDescription()));
            }
        } catch (Exception ex){
            // If you can’t translate the Pokemon’s description (for whatever reason 😉) then use the standard
            //description
        }

        return pokemon;
    }
}
