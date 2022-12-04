package org.example.pokedex.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.pokedex.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PokemonService {

    @Autowired
    RestTemplate restTemplate;

    public Pokemon getPokemon(String pokemonName){
        ObjectNode rawPokemon = restTemplate.getForObject(
                "https://pokeapi.co/api/v2/pokemon-species/" + pokemonName, ObjectNode .class);
        Pokemon pokemon = new Pokemon();
        pokemon.setName(rawPokemon.get("name").textValue());
        String description = rawPokemon.get("flavor_text_entries").get(0).get("flavor_text").textValue();
        pokemon.setDescription(description);
        pokemon.setHabitat(rawPokemon.get("habitat").get("name").textValue());
        pokemon.setIsLegendary(Boolean.parseBoolean(rawPokemon.get("is_legendary").textValue()));
        return pokemon;
    }

}
