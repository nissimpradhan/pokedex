package org.example.pokedex.model.yoda;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranslationResponse {
    @JsonProperty("success")
    private Success success;
    @JsonProperty("contents")
    private Contents contents;
}
