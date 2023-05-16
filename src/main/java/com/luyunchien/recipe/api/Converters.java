package com.luyunchien.recipe.api;

import com.luyunchien.recipe.api.model.Recipe;
import com.luyunchien.recipe.entity.RecipeEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Converters {

    public Recipe recipe(RecipeEntity entity) {
        return new Recipe()
                .id(entity.getId())
                .name(entity.getName())
                .ingredients(entity.getIngredients())
                .steps(entity.getSteps());
    }
}
