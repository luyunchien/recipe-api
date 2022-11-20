package com.roinnsenn.recipe.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roinnsenn.recipe.entity.Recipe;
import lombok.Builder;

import java.util.List;

@Builder
public class RecipeResponse {
    @JsonProperty
    String name;
    @JsonProperty
    List<String> ingredients;
    @JsonProperty
    List<String> steps;
    @JsonProperty
    int id;

    public static RecipeResponse fromEntity(Recipe entity){
        return RecipeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ingredients(entity.getIngredients())
                .steps(entity.getSteps())
                .build();
    }
}
