package org.example.pokedex.model.yoda;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TranslationResponse {
    @JsonProperty("success")
    private Success success;
    @JsonProperty("contents")
    private Contents contents;
}
