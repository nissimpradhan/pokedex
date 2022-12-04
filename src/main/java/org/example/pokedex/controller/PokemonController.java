package org.example.pokedex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pokemon")
public class PokemonController {

    @GetMapping("/{pokemonName}")
    @ResponseBody
    public String getPokemon(@PathVariable String pokemonName){
        return "Hi! " + pokemonName;
    }
}
