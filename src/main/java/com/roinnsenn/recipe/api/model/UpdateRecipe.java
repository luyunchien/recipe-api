package com.roinnsenn.recipe.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Value
public class UpdateRecipe {
    @NotBlank
    @JsonProperty
    String name;

    @NotEmpty
    @JsonProperty
    List<String> ingredients;

    @JsonProperty
    @NotEmpty
    List<String> steps;
}
