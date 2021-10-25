package is.hi.recipe.Services.Implementation;

import is.hi.recipe.Persistence.Entities.Recipe;
import is.hi.recipe.Persistence.Repositories.RecipeRepository;
import is.hi.recipe.Services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImplementation implements RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImplementation(RecipeRepository recipeRepository) {
        // Create 3 random recipes for our dummy repo.  To be removed when JPA added.
        this.recipeRepository = recipeRepository;
        //JPA gives each recipe an ID, but here we add them manually
    }

    @Override
    public Recipe findByRecipeTitle(String recipeTitle) {
        // Þurfum að lagfæra þetta seinna meir
        return recipeRepository.findByRecipeTitle(recipeTitle).get(0);
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe findByID(long ID) {
        return recipeRepository.findByID(ID);
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }
}
