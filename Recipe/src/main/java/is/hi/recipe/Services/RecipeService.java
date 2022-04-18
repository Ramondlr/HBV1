package is.hi.recipe.Services;

import is.hi.recipe.Persistence.Entities.Recipe;

import java.util.List;

public interface RecipeService {
    Recipe findByRecipeTitle(String recipeTitle);
    List<Recipe> findAll();
    Recipe findByID(long ID);
    Recipe save(Recipe recipe);
    void delete(Recipe recipe);
    //void deleteRecipeImage(Recipe recipe);
    boolean hasUserRecipe(List<Recipe> allRecipes, long id);

    List<Recipe> findByKeyword(String keyword);

    List<Recipe> findUserRecipes(long userID);
}
