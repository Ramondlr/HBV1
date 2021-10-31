package is.hi.recipe.Persistence.Repositories;

import is.hi.recipe.Persistence.Entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // It will create an entry in the table of recipe, with a new ID, return that new ID as the entity to you.
    // You can give it an empty recipe with maybe just a name and a few attributes, it will save that to the database
    // and return to you a new instance of that recipe with the ID that id generated for you.
    Recipe save(Recipe recipe);
    // As long as we have a recipe with an ID it will take that and delete it for you
    void delete(Recipe recipe);
    List<Recipe> findAll();
    List<Recipe> findByRecipeTitle(String recipeTitle);
    Recipe findByID(long id);
}
