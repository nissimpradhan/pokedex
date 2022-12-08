package org.example.pokedex.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.pokedex.configs.ExternalApiException;
import org.example.pokedex.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;

@Service
public class PokemonService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TranslationService translationService;

    public Pokemon getPokemon(String pokemonName){
        try{
            ObjectNode rawPokemon = restTemplate.getForObject(
                    "https://pokeapi.co/api/v2/pokemon-species/" + pokemonName, ObjectNode.class);
            Pokemon pokemon = new Pokemon();
            pokemon.setName(rawPokemon.get("name").textValue());
            Iterator<JsonNode> it =  rawPokemon.withArray("flavor_text_entries").iterator();
            String englishDescription = "";
            while (it.hasNext()) {
                JsonNode node = it.next();
                if (node.has("language") && node.get("language").get("name").textValue().equals("en")) {
                    englishDescription = node.get("flavor_text").textValue();
                    break;
                }
            }
            //String englishDescription = rawPokemon.get("flavor_text_entries").get(0).get("flavor_text").textValue();
            pokemon.setDescription(englishDescription);
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
