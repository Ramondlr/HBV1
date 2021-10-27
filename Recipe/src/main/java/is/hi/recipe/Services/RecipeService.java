package is.hi.recipe.Services;

import is.hi.recipe.Persistence.Entities.Recipe;

import java.util.List;

public interface RecipeService {
    Recipe findByRecipeTitle(String title);
    List<Recipe> findAll();
    Recipe findByID(long ID);
    Recipe save(Recipe recipe);
    void delete(Recipe recipe);
}