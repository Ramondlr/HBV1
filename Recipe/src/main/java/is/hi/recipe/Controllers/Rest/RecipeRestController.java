package is.hi.recipe.Controllers.Rest;

import is.hi.recipe.Persistence.Entities.Recipe;
import is.hi.recipe.Persistence.Entities.User;
import is.hi.recipe.Services.RecipeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class RecipeRestController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipes")
    public List<Recipe> getRecipes() throws InterruptedException {

        List<Recipe> recipes = recipeService.findAll();

        return recipes;
    }

    @RequestMapping(value = "/recipe/{id}")
    public Recipe getRecipe(@PathVariable("id") long id) throws InterruptedException {

        Recipe recipes = recipeService.findByID(id);

        return recipes;
    }


    @RequestMapping(value = "/delete/{id}")
    public boolean deleteRecipe(@PathVariable("id") long id){
        //Business Logic is always on the service that's why we let the recipeService do all the work
        Recipe recipeToDelete = recipeService.findByID(id);
        recipeService.delete(recipeToDelete);
        if (recipeService.findByID(id) != null) {
            return true;
        }
        return false;
    }

}
