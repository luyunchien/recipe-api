package com.luyunchien.recipe.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * In future this will be stored in a Database
 */
@Data
@Builder
public class RecipeEntity {
    String name;
    List<String> ingredients;
    List<String> steps;
    int id;
}
