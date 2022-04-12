package is.hi.recipe.Controllers.Rest;

import is.hi.recipe.Persistence.Entities.Recipe;
import is.hi.recipe.Persistence.Entities.User;
import is.hi.recipe.Services.RecipeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class RecipeRestController {

    private RecipeService recipeService;

    @Autowired
    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
}
