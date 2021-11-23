package is.hi.recipe.Services.Implementation;

import is.hi.recipe.Persistence.Entities.Recipe;
import is.hi.recipe.Persistence.Repositories.RecipeRepository;
import is.hi.recipe.Services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImplementation implements RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImplementation(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe findByRecipeTitle(String recipeTitle) {
        // Þurfum að lagfæra þetta seinna meir
        return (Recipe) recipeRepository.findByRecipeTitle(recipeTitle);
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
        deleteRecipeImage(recipe);
        recipeRepository.delete(recipe);
    }

    @Override
    public void deleteRecipeImage(Recipe recipe) {
        String fileToDeletePath = "src/main/resources/static/" + recipe.getRecipeImagePath();
        try {
            Files.delete(Path.of(fileToDeletePath));
        } catch (IOException e) {
            System.out.println("error deleting recipeImage");
        }
    }
    // TODO editRecipe

}
