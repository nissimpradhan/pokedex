package org.example.pokedex.model.yoda;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contents {

    @JsonProperty("translated")
    private String translated;
    @JsonProperty("text")
    private String text;
    @JsonProperty("translation")
    private String translation;
}
