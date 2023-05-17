package com.luyunchien.recipe.api;

import com.luyunchien.recipe.api.model.CreateRecipeDetails;
import com.luyunchien.recipe.api.model.Recipe;
import com.luyunchien.recipe.api.model.UpdateRecipeDetails;
import com.luyunchien.recipe.entity.RecipeEntity;
import com.luyunchien.recipe.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@AllArgsConstructor
public class RecipeController implements RecipesApi {

    private final RecipeRepository recipeRepository;

    @Override
    public ResponseEntity<List<Recipe>> listRecipe() {
        var recipeStream = StreamSupport.stream(recipeRepository.findAll().spliterator(), false);
        return ResponseEntity.ok(recipeStream.map(Converters::recipe).collect(Collectors.toList()));
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
                .build();
        recipeRepository.save(recipe);
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
        recipeRepository.save(entity);
        return ResponseEntity.ok(Converters.recipe(entity));
    }

    @Override
    public ResponseEntity<Void> deleteRecipe(Integer id) {
        var entity = findByID(id);
        recipeRepository.delete(entity);
        return ResponseEntity.ok().build();
    }

    private RecipeEntity findByID(int id){
        return recipeRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "entity doesn't exist"));
    }
}
