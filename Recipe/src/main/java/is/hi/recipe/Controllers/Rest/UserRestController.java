package is.hi.recipe.Controllers.Rest;

import is.hi.recipe.Controllers.Rest.Request.UserRequest;
import is.hi.recipe.Controllers.Rest.Response.RecipeResponse;
import is.hi.recipe.Controllers.Rest.Response.UserResponse;
import is.hi.recipe.Persistence.Entities.User;
import is.hi.recipe.Services.RecipeService;
import is.hi.recipe.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@RestController
public class UserRestController {

    private UserService userService;
    private RecipeService recipeService;

    @Autowired
    public UserRestController(UserService userService, RecipeService recipeService) {
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @PostMapping("/api/login")
    @ResponseBody
    public UserResponse logIn (@RequestBody UserRequest userRequest) {

        User user = userService.login(new User(userRequest.getUsername(), userRequest.getPassword()));

        return new UserResponse(user);
    }

    @PostMapping("/api/signUp")
    @ResponseBody
    public UserResponse signUp (@RequestBody UserRequest userRequest) {
        User user = userService.signUp(userRequest.getUsername(), userRequest.getPassword());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.IM_USED);
        }
        return new UserResponse(user);
    }


    @GetMapping("/api/{id}/recipeList")
    @ResponseBody
    public RecipeResponse recipeList(@PathVariable("id") Long id) {
        return new RecipeResponse(recipeService.findUserRecipes(id));
    }
}
