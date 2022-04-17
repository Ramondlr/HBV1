package is.hi.recipe.Controllers.Rest;

import is.hi.recipe.Controllers.Rest.Response.RecipeResponse;
import is.hi.recipe.Persistence.Entities.Recipe;
import is.hi.recipe.Persistence.Entities.User;
import is.hi.recipe.Services.RecipeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
/*
    @GetMapping("/api/{id}/recipeList")
    @ResponseBody
    public RecipeResponse recipeList(@PathVariable Long id) {

        List<Recipe> recipes = new ArrayList<>();
        recipes = recipeService.findUserRecipes(id);

        for (Recipe r : recipes) {

        }

        return new RecipeResponse(recipeService.findUserRecipes(id));
    }
    */

    @PostMapping("/api/saveRecipe")
    @ResponseBody
    public RecipeResponse saveRecipe (@RequestBody Recipe recipe) {
        recipeService.save(recipe);
        return new RecipeResponse(recipeService.findUserRecipes(recipe.getUserID()));
        //return new RecipeResponse(recipeService.findByID(recipe.getID()));
        //return null;
    }

    /*
    @GetMapping(value = "/api/delete/{id}")
    public void deleteRecipe(@PathVariable("id") long id){

    }

    @RequestMapping(value = "/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable("id") long id) throws InterruptedException {

        Recipe recipes = recipeService.findByID(id);

        return recipes;
    }


    @RequestMapping(value = "/api/(delete/{id}")
    public boolean deleteRecipe(@PathVariable("id") long id){
        //Business Logic is always on the service that's why we let the recipeService do all the work
        Recipe recipeToDelete = recipeService.findByID(id);
        recipeService.delete(recipeToDelete);

        return recipeService.findByID(id) != null;
    }

     */

}
