package org.example.pokedex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {
    String name;
    String description;
    String habitat;
    Boolean isLegendary;
}
