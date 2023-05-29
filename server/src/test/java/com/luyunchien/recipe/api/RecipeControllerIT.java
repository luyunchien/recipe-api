package com.luyunchien.recipe.api;

import com.luyunchien.recipe.client.RecipesApi;
import com.luyunchien.recipe.client.handler.ApiClient;
import com.luyunchien.recipe.client.handler.ApiException;
import com.luyunchien.recipe.client.model.CreateRecipeDetails;
import com.luyunchien.recipe.client.model.UpdateRecipeDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Clean the database before each test
// https://stackoverflow.com/questions/34617152/how-to-re-create-database-before-each-test-in-spring
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecipeControllerIT {

    @LocalServerPort
    int port;

    RecipesApi recipesApiClient;

    @BeforeEach
    public void setup() {
        var client = new ApiClient().setHost("localhost").setPort(port).setScheme("http");
        recipesApiClient = new RecipesApi(client);
    }

    @Test
    public void testCreateRecipe() throws ApiException {
        // Create 1 recipe
        var recipe = recipesApiClient.createRecipe(new CreateRecipeDetails()
                .name("recipe1")
                .addIngredientsItem("ingredient1")
                .addStepsItem("step1")
        );
        assertThat(recipe.getName()).isEqualTo("recipe1");
        assertThat(recipe.getIngredients()).containsExactly("ingredient1");
        assertThat(recipe.getSteps()).containsExactly("step1");
        // Test the recipe is saved
        assertThat(recipesApiClient.getRecipe(recipe.getId())).isEqualTo(recipe);
    }

    @Test
    public void testListRecipes() throws ApiException {
        // To start with, the list is empty
        assertThat(recipesApiClient.listRecipe()).isEmpty();
        // Now we create 3 recipes
        for (int i = 1; i <= 3; i++) {
            recipesApiClient.createRecipe(new CreateRecipeDetails()
                    .name("recipe" + i)
                    .addIngredientsItem("ingredient" + i)
                    .addStepsItem("step" + i)
            );
        }
        assertThat(recipesApiClient.listRecipe().size()).isEqualTo(3);
    }

    @Test
    public void testDeleteRecipe() throws ApiException {
        // Create 1 recipe
        var newRecipe = recipesApiClient.createRecipe(new CreateRecipeDetails()
                .name("recipe1")
                .addIngredientsItem("ingredient1")
                .addStepsItem("step1")
        );
        // Check the recipe exists
        assertThat(recipesApiClient.getRecipe(newRecipe.getId())).isEqualTo(newRecipe);
        assertThat(recipesApiClient.listRecipe()).containsExactly(newRecipe);
        // Delete the recipe
        recipesApiClient.deleteRecipe(newRecipe.getId());
        // Check the recipe was deleted
        assertThat(recipesApiClient.listRecipe().size()).isEqualTo(0);
        var ex = assertThrows(ApiException.class, () -> recipesApiClient.getRecipe(newRecipe.getId()));
        var problem = ProblemParser.convertException(ex);
        assertThat(problem.getStatus()).isEqualTo(404);
        assertThat(problem.getDetail()).isEqualTo("Recipe not found");
    }

    @Test
    public void testUpdateRecipeName() throws ApiException {
        // Create 1 recipe
        var newRecipe = recipesApiClient.createRecipe(new CreateRecipeDetails()
                .name("recipe1")
                .addIngredientsItem("ingredient1")
                .addStepsItem("step1")
        );
        // Check the response to update
        var updated = recipesApiClient.updateRecipe(newRecipe.getId(), new UpdateRecipeDetails().name("updated recipe1"));
        assertThat(updated.getName()).isEqualTo("updated recipe1");
        assertThat(updated.getIngredients()).isEqualTo(newRecipe.getIngredients());
        assertThat(updated.getSteps()).isEqualTo(newRecipe.getSteps());

        // Test the recipe is saved
        assertThat(recipesApiClient.getRecipe(newRecipe.getId())).isEqualTo(updated);
    }

    @Test
    public void testUpdateRecipeIngredients() throws ApiException {
        // Create 1 recipe
        var newRecipe = recipesApiClient.createRecipe(new CreateRecipeDetails()
                .name("recipe1")
                .addIngredientsItem("ingredient1")
                .addStepsItem("step1")
        );
        // Check the response to update
        var updated = recipesApiClient.updateRecipe(newRecipe.getId(), new UpdateRecipeDetails()
                .addIngredientsItem("updated ingredient1")
                .addIngredientsItem("another one"));
        assertThat(updated.getName()).isEqualTo(newRecipe.getName());
        assertThat(updated.getIngredients()).containsExactly("updated ingredient1", "another one");
        assertThat(updated.getSteps()).isEqualTo(newRecipe.getSteps());

        // Test the recipe is saved
        assertThat(recipesApiClient.getRecipe(newRecipe.getId())).isEqualTo(updated);
    }

    @Test
    public void testUpdateRecipeSteps() throws ApiException {
        // Create 1 recipe
        var newRecipe = recipesApiClient.createRecipe(new CreateRecipeDetails()
                .name("recipe1")
                .addIngredientsItem("ingredient1")
                .addStepsItem("step1")
        );
        // Check the response to update
        var updated = recipesApiClient.updateRecipe(newRecipe.getId(), new UpdateRecipeDetails()
                .addStepsItem("step1")
                .addStepsItem("step2"));
        assertThat(updated.getName()).isEqualTo(newRecipe.getName());
        assertThat(updated.getIngredients()).isEqualTo(newRecipe.getIngredients());
        assertThat(updated.getSteps()).containsExactly("step1", "step2");

        // Test the recipe is saved
        assertThat(recipesApiClient.getRecipe(newRecipe.getId())).isEqualTo(updated);
    }
}