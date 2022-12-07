package com.roinnsenn.recipe.api;

import com.roinnsenn.recipe.api.model.CreateRecipe;
import com.roinnsenn.recipe.api.model.RecipeResponse;
import com.roinnsenn.recipe.api.model.UpdateRecipe;
import com.roinnsenn.recipe.entity.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/recipes")
@RestController
public class RecipeController {

    List<Recipe> recipeStore = new ArrayList<>();

    @GetMapping("/")
    public List<RecipeResponse> listRecipes(){
        return recipeStore.stream().map(RecipeResponse::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RecipeResponse getRecipe(@PathVariable int id){
        return RecipeResponse.fromEntity(findByID(id));
    }

    @PostMapping(value = "/")
    public RecipeResponse createRecipe(@RequestBody @Validated CreateRecipe createRecipe){
        var recipe = Recipe.builder()
                .name(createRecipe.getName())
                .ingredients(createRecipe.getIngredients())
                .steps(createRecipe.getSteps())
                .id(nextID())
                .build();
        recipeStore.add(recipe);
        return RecipeResponse.fromEntity(recipe);
    }

    @PutMapping("/{id}")
    public RecipeResponse updateRecipe(@PathVariable int id, @RequestBody @Validated UpdateRecipe updateRecipe){
        var entity = findByID(id);
        if(updateRecipe.getName()!=null){
            entity.setName(updateRecipe.getName());
        }
        if(updateRecipe.getIngredients()!=null){
            entity.setIngredients(updateRecipe.getIngredients());
        }
        if(updateRecipe.getSteps()!=null){
            entity.setSteps(updateRecipe.getSteps());
        }
        return RecipeResponse.fromEntity(entity);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable int id){
        var entity = findByID(id);
        recipeStore.remove(entity);
    }

    private int nextID(){
        return recipeStore.stream().mapToInt(recipe -> recipe.getId()).max().orElse(0) + 1;
    }

    private Recipe findByID(int id){
        return recipeStore.stream().filter((recipe -> recipe.getId()==id)).findFirst().orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "entity doesn't exist"));
    }
}
