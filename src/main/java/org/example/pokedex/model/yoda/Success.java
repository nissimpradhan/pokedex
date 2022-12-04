package org.example.pokedex.model.yoda;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Success {

    @JsonProperty("total")
    private Integer total;
}
