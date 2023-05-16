package com.luyunchien.recipe.api;

import com.luyunchien.recipe.api.model.*;
import com.luyunchien.recipe.entity.RecipeEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RecipeController implements RecipesApi {

    List<RecipeEntity> recipeStore = new ArrayList<>();

    @Override
    public ResponseEntity<List<Recipe>> listRecipe() {
        return ResponseEntity.ok(recipeStore.stream().map(Converters::recipe).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<Recipe> getRecipe(Integer id) {
        return ResponseEntity.ok(Converters.recipe(findByID(id)));
    }

    @Override
    public ResponseEntity<Recipe> createRecipe(CreateRecipeDetails createRecipeDetails) {
        var recipe = RecipeEntity.builder()
                .name(createRecipeDetails.getName())
                .ingredients(createRecipeDetails.getIngredients())
                .steps(createRecipeDetails.getSteps())
                .id(nextID())
                .build();
        recipeStore.add(recipe);
        return ResponseEntity.ok(Converters.recipe(recipe));
    }

    @Override
    public ResponseEntity<Recipe> updateRecipe(Integer id, UpdateRecipeDetails updateRecipeDetails) {
        var entity = findByID(id);
        if(updateRecipeDetails.getName() != null){
            entity.setName(updateRecipeDetails.getName());
        }
        if(updateRecipeDetails.getIngredients() != null){
            entity.setIngredients(updateRecipeDetails.getIngredients());
        }
        if(updateRecipeDetails.getSteps() != null){
            entity.setSteps(updateRecipeDetails.getSteps());
        }
        return ResponseEntity.ok(Converters.recipe(entity));
    }

    @Override
    public ResponseEntity<Void> deleteRecipe(Integer id) {
        var entity = findByID(id);
        recipeStore.remove(entity);
        return ResponseEntity.ok().build();
    }

    private int nextID(){
        return recipeStore.stream().mapToInt(RecipeEntity::getId).max().orElse(0) + 1;
    }

    private RecipeEntity findByID(int id){
        return recipeStore.stream().filter((recipe -> recipe.getId() == id)).findFirst().orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "entity doesn't exist"));
    }
}
