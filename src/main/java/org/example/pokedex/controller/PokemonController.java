package org.example.pokedex.controller;

import org.example.pokedex.model.Pokemon;
import org.example.pokedex.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/pokemon")
public class PokemonController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PokemonService pokemonService;

    @GetMapping("/{pokemonName}")
    @ResponseBody
    public Pokemon getPokemon(@PathVariable String pokemonName){
        return pokemonService.getPokemon(pokemonName);
    }
}
