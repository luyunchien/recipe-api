package com.luyunchien.recipe.repositories;

import com.luyunchien.recipe.entity.RecipeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<RecipeEntity, Integer> {
}