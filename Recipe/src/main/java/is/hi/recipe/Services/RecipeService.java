package is.hi.recipe.Services;

import is.hi.recipe.Persistence.Entities.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeService {
    Recipe findByRecipeTitle(String recipeTitle);
    List<Recipe> findAll();
    Recipe findByID(long ID);
    Recipe save(Recipe recipe);
    void delete(Recipe recipe);
    void deleteRecipeImage(Recipe recipe);
    public boolean hasUserRecipe(List<Recipe> allRecipes, long id);

    @Query(value = "select * from recipes r where r.recipe_Title like '%keyword%' or r.recipe_text like '%keyword%' or r.recipe_tag like '%keyword%'", nativeQuery = true)
    List<Recipe> findByKeyword(@Param("keyword") String keyword);

}
