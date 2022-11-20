package com.roinnsenn.recipe.api;

import com.roinnsenn.recipe.api.model.CreateRecipe;
import com.roinnsenn.recipe.api.model.RecipeResponse;
import com.roinnsenn.recipe.entity.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String getRecipe(@PathVariable int id){
        return "todo - get recipe " + id;
    }

    @PostMapping(value = "/")
    public RecipeResponse createRecipe(@RequestBody @Validated CreateRecipe createRecipe){
        var recipe = Recipe.builder()
                .name(createRecipe.getName())
                .ingredients(createRecipe.getIngredients())
                .steps(createRecipe.getSteps())
                .id(0)
                .build();
        recipeStore.add(recipe);
        return RecipeResponse.fromEntity(recipe);
    }

    @PutMapping("/{id}")
    public String updateRecipe(@PathVariable int id){
        return "todo - update recipe " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteRecipe(@PathVariable int id){
        return "todo - delete recipe " + id;
    }


}
