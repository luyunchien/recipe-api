package com.roinnsenn.recipe.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Recipe {
    String name;
    List<String> ingredients;
    List<String> steps;
    int id;
}
