package is.hi.recipe.Controllers;

import is.hi.recipe.FileSaver;
import is.hi.recipe.Persistence.Entities.Recipe;
import is.hi.recipe.Persistence.Entities.User;
import is.hi.recipe.Services.RecipeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Objects;

@Controller
public class RecipeController {
    private RecipeService recipeService;

    /**
     *                      RecipeController calls service when needed to be used with entity Recipe.
     * @param recipeService gets the recipe service class.
     */
    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     *          homePage retrieves the home.html from templates and presents that to the user,
     *          the user then either logs in, or signs up for an account.
     * @return  home page of our website.
     */
    @RequestMapping("/")
    public String homePage(){
        return "home";
    }

    /**
     * userRecipeGet retrieves the userRecipe.html from templates after the user has logged in successfully.
     * @param model   Presents all recipes of a user on the userRecipe.html template, if the user has any.
     * @param session Has been created by each user when a user is logged in.
     * @param keyword A search term to list through the recipes of a user and returns a list of recipes that matches.
     * @return        userRecipe.html template either with a list of recipes or an empty template.
     */
    @GetMapping(value ="/userRecipe")
    public String userRecipeGET(Model model, @NotNull HttpSession session, String keyword){
        //Call a method in a service class
        List<Recipe> allRecipes = recipeService.findAll();
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        // sessionUser.getID() --> svona náum við í userID
        long id = sessionUser.getID();
        //if (allRecipes.isEmpty()) allRecipes = null;
        if (!recipeService.hasUserRecipe(allRecipes, id)) {
            allRecipes = null;
        }

        model.addAttribute("LoggedInUser", sessionUser);

        if (keyword != null) {
            model.addAttribute("recipes", recipeService.findByKeyword(keyword));
        }
        else {
            model.addAttribute("recipes", allRecipes);
        }

        return "userRecipe";
    }


    /**
     *
     * @param recipe Is not used.
     * @return newRecipe.html template where the user makes a new recipe.
     */
    @GetMapping(value = "/newRecipe")
    public String newRecipeGET(Recipe recipe){

        return "newRecipe";
    }

    /**
     * This makes sure to create and store a new recipe in the database for the user.
     * @param recipe        Request of the recipe class from the entity recipe.
     * @param result        Binding results from the post action of the newRecipe.html form(action=POST)
     * @param session       Has been created by each user when a user is logged in.
     * @param multipartFile Takes in a parameter 'image' for when a user has uploaded an image file.
     * @return              returns userRecipe.html template with a new recipe added to the list,
     *                      or newRecipe.html if it had any errors.
     * @throws IOException
     */
    @PostMapping(value = "/newRecipe")
    public String newRecipePOST(Recipe recipe, @NotNull BindingResult result, HttpSession session,
                                @RequestParam(name = "image", required = false) MultipartFile multipartFile) throws IOException {
        if(result.hasErrors()){
            return "newRecipe";
        }


        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        recipe.setRecipeImage(fileName);

        if ((recipe.getRecipeTitle().isEmpty()) && (recipe.getRecipeText().isEmpty())
                && (recipe.getRecipeTag().isEmpty()) && (recipe.getRecipeImage().isEmpty()))
            return "redirect:/newRecipe";

        if ((recipe.getRecipeTitle().isEmpty()) || ((recipe.getRecipeText().isEmpty()) && (recipe.getRecipeImage().isEmpty())))
            return "redirect:/newRecipe";

        // Hér náum við í info um currentlyLoggedInUser
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        // Stillum hér userID undir Recipes, gildið sem currentlyLoggedInUser ID hefur
        recipe.setUserID(sessionUser.getID());
        // Síðan vistum við nýju uppskriftina, með currentlyLoggedInUser ID vistað hjá sér undir userID.

        Recipe savedRecipe = recipeService.save((recipe));
        String uploadDir = "src/main/resources/static/upload/recipeImage/" + savedRecipe.getUserID() + "/" + savedRecipe.getID();

        try {
            FileSaver.saveFile(uploadDir, fileName, multipartFile);
        } catch (IOException exception) {
            return "/newRecipe";
        }

        // Redirects the page to our home page
        return "redirect:/userRecipe";
    }

    /**
     *           Deletes a recipe from a list of recipes from the user.
     * @param id Takes in the id of the recipe to delete.
     * @return   userRecipes.html with the deleted recipe no longer on the list nor in the user database.
     */
    @GetMapping(value = "/delete/{id}")
    public String deleteRecipe(@PathVariable("id") long id){
        //Business Logic is always on the service that's why we let the recipeService do all the work
        Recipe recipeToDelete = recipeService.findByID(id);
        recipeService.delete(recipeToDelete);
        // Redirects the user to the home page
        return "redirect:/userRecipe";
    }

    /**
     *              Shows the whole recipe (along with an image if the recipe has any).
     * @param id    Takes in the id of the recipe to show it in the viewRecipe.html template.
     * @param model Takes in a recipe the user wants to view and sends it to the template.
     * @return      viewRecipe.html with a recipe the user has selected to view.
     */
    @GetMapping(value = "/viewRecipe/{id}")
    public String getRecipe(@PathVariable("id") long id, @NotNull Model model){
        model.addAttribute("recipe", recipeService.findByID(id));
        return "viewRecipe";
    }

    /**
     * 
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "/editRecipe/{id}")
    public String editRecipeGET(@PathVariable("id") long id, @NotNull Model model){
        model.addAttribute("recipes", recipeService.findByID(id));
        return "editRecipe";
    }

    @PostMapping(value = "/editRecipe")
    public String editRecipePOST(Recipe recipe, HttpSession session, @NotNull BindingResult result,
                                 @RequestParam(name = "image", required = false) MultipartFile multipartFile) throws IOException {
        if(result.hasErrors()){
            return "editRecipe";
        }
        // Náum í info um currentlyLoggedInUser
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        // Stillum userID undir Recipes, gildið sem currentlyLoggedInUser ID hefur
        recipe.setUserID(sessionUser.getID());

        String newImage = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        recipe.setRecipeImage((recipeService.findByID(recipe.getID())).getRecipeImage());

        if(!newImage.isEmpty()) {
            recipeService.deleteRecipeImage(recipe);
            recipe.setRecipeImage(newImage);
        }

        // Vistum nýju uppskriftina, með currentlyLoggedInUser ID vistað hjá sér undir userID.
        Recipe savedRecipe = recipeService.save((recipe));
        String uploadDir = "src/main/resources/static/upload/recipeImage/" + savedRecipe.getUserID() + "/" + savedRecipe.getID();

        try {
            FileSaver.saveFile(uploadDir, newImage, multipartFile);
        } catch (IOException exception) {
            return "/viewRecipe";
        }

        // Redirects the page to our designated html page
        return "viewRecipe";
    }

}
