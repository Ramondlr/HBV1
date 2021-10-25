package is.hi.recipe.Controllers;

import is.hi.recipe.Persistence.Entities.Recipe;
import is.hi.recipe.Persistence.Entities.User;
import is.hi.recipe.Services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class RecipeController {
    private RecipeService recipeService;

    User user;

    @Autowired
    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    // Strengurinn inní 'RequestMapping' segir okkur að þegar vefsíðan er stödd á forsíðu þá á hún að skila 'home'
    // sem er 'return "home"' og þetta 'home' er nafnið á html skjalinu okkar í templates
    @RequestMapping("/")
    public String RecipeController(Model model){

        //Call a method in a service class
        List<Recipe> allRecipes = recipeService.findAll();
        //Add some data to the model
        model.addAttribute("recipes", allRecipes);

        return "home";
    }

    @RequestMapping(value = "/addrecipe", method = RequestMethod.GET)
    public String addRecipe(Recipe recipe){

        return "newRecipe";
    }

    @RequestMapping(value = "/addrecipe", method = RequestMethod.POST)
    public String addRecipe(Recipe recipe, BindingResult result, Model model){
        if(result.hasErrors()){
            return "newRecipe";
        }
        recipeService.save((recipe));
        // Redirects the page to our home page
        return "redirect:/";

    }

    // GET means we're reading something, POST means we're creating something.
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteRecipe(@PathVariable("id") long id, Model model){
        //Business Logic is always on the service that's why we let the recipeService do all the work
        Recipe recipeToDelete = recipeService.findByID(id);
        recipeService.delete(recipeToDelete);
        // Redirects the user to the home page
        return "redirect:/";
    }
}
