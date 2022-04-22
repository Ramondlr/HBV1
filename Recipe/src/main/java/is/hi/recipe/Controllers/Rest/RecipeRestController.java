package is.hi.recipe.Controllers.Rest;

import is.hi.recipe.Controllers.Rest.Response.RecipeResponse;
import is.hi.recipe.Persistence.Entities.Recipe;
import is.hi.recipe.Services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class RecipeRestController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/api/recipes")
    public List<Recipe> getRecipes() throws InterruptedException {

        List<Recipe> recipes = recipeService.findAll();

        return recipes;
    }

    @GetMapping("/api/{id}/recipeList")
    @ResponseBody
    public RecipeResponse recipeList(@PathVariable Long id) {
        return new RecipeResponse(recipeService.findUserRecipes(id));
    }

    @PostMapping("/api/saveRecipe")
    @ResponseBody
    public RecipeResponse saveRecipe (@RequestBody Recipe recipe) {
        if (recipe.getRecipeTitle() == "" || (recipe.getRecipeText() == "" && recipe.getRecipeImage() == "")) {
            return null;
        }
        recipeService.save(recipe);
        return new RecipeResponse(recipeService.findUserRecipes(recipe.getUserID()));
    }

    @GetMapping("/api/{id}/deleteRecipe")
    @ResponseBody
    public RecipeResponse deleteRecipe (@PathVariable Long id) {
        Recipe recipe = recipeService.findByID(id);
        recipeService.delete(recipe);
        return new RecipeResponse(recipeService.findUserRecipes(recipe.getUserID()));
    }
}
