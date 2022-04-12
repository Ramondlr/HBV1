package is.hi.recipe.Controllers.Rest;

import is.hi.recipe.Persistence.Entities.Recipe;
import is.hi.recipe.Persistence.Entities.User;
import is.hi.recipe.Services.RecipeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class RecipeRestController {

    private RecipeService recipeService;

    /*

    @GetMapping(value ="/userRecipe")
    public String userRecipeGET(@NotNull HttpSession session, String keyword){
        //Call a method in a service class
        List<Recipe> allRecipes = recipeService.findAll();
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        // sessionUser.getID() --> svona náum við í userID
        long id = sessionUser.getID();
        //if (allRecipes.isEmpty()) allRecipes = null;
        if (!recipeService.hasUserRecipe(allRecipes, id)) allRecipes = null;

        // model.addAttribute("LoggedInUser", sessionUser);

        if (keyword != null) {
            // TODO:
            // recipeService.findByKeyword(keyword));
        }
        else {
            // model.addAttribute("recipes", allRecipes);
        }

        return "";
    }
*/
}
