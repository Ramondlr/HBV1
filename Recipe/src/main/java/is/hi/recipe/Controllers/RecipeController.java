package is.hi.recipe.Controllers;

import is.hi.recipe.Persistence.Entities.Recipe;
import is.hi.recipe.Persistence.Entities.User;
import is.hi.recipe.Services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
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
    public String homePage(){
        return "home";
    }

    @RequestMapping(value = "/userRecipe", method = RequestMethod.GET)
    public String userRecipeGET(Model model, HttpSession session){
        //Call a method in a service class
        List<Recipe> allRecipes = recipeService.findAll();
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        // sessionUser.getID() --> svona náum við í userID

        //Add some data to the model
        model.addAttribute("recipes", allRecipes);
        // Sjáum hér að sessionUser sýnir okkur ID hjá currentlyLoggedInUser, notum það til að sýna uppskriftir sem sá
        // notandi á og tengja saman nýjar uppskriftir við
        // System.out.println("'useRecipeGET'-Session user ID: " + sessionUser.getID());
        model.addAttribute("LoggedInUser", sessionUser);

        return "userRecipe";
    }



    @RequestMapping(value = "/newRecipe", method = RequestMethod.GET)
    public String newRecipeGET(Recipe recipe){

        return "newRecipe";
    }

    @RequestMapping(value = "/newRecipe", method = RequestMethod.POST)
    public String newRecipePOST(Recipe recipe, BindingResult result, HttpSession session, @RequestParam("image_uploads") byte[] imageFile){
        if(result.hasErrors()){
            return "newRecipe";
        }

        // Hér náum við í info um currentlyLoggedInUser
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        // Stillum hér userID undir Recipes, gildið sem currentlyLoggedInUser ID hefur
        recipe.setUserID(sessionUser.getID());
        // Síðan vistum við nýju uppskriftina, með currentlyLoggedInUser ID vistað hjá sér undir userID.
        recipe.setRecipeImage(imageFile);
        recipeService.save((recipe));
        // Redirects the page to our home page
        return "redirect:/userRecipe";
    }


    // GET means we're reading something, POST means we're creating something.
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteRecipe(@PathVariable("id") long id){
        //Business Logic is always on the service that's why we let the recipeService do all the work
        Recipe recipeToDelete = recipeService.findByID(id);
        recipeService.delete(recipeToDelete);
        // Redirects the user to the home page
        return "redirect:/userRecipe";
    }

    @RequestMapping(value = "/viewRecipe/{id}", method = RequestMethod.GET)
    public String getRecipe(@PathVariable("id") long id, Model model){

        model.addAttribute("recipe", recipeService.findByID(id));
        return "viewRecipe";
    }

    // Birtir uppskrift til að breyta
    @RequestMapping(value = "/editRecipe/{id}", method = RequestMethod.GET)
    public String editRecipeGET(@PathVariable("id") long id, Model model){
        model.addAttribute("recipes", recipeService.findByID(id));
        return "editRecipe";
    }

    @RequestMapping(value = "/editRecipe", method = RequestMethod.POST)
    public String editRecipePOST(Recipe recipe, HttpSession session){
        // Hér náum við í info currentlyLoggedInUser
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        // Stillum hér userID undir Recipes sem currentlyLoggedInUser hefur
        recipe.setUserID(sessionUser.getID());
        recipeService.save(recipe);

        // Redirects the page to our designated html page
        return "viewRecipe";
    }

}
