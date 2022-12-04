package org.example.pokedex.model.yoda;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TranslationRequest {
    String text;
}
