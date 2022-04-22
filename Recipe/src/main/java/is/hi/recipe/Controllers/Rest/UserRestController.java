package is.hi.recipe.Controllers.Rest;

import is.hi.recipe.Controllers.Rest.Request.UserRequest;
import is.hi.recipe.Controllers.Rest.Response.UserResponse;
import is.hi.recipe.Persistence.Entities.User;
import is.hi.recipe.Services.RecipeService;
import is.hi.recipe.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/api/isLoggedIn")
    @ResponseBody
    public UserResponse isLoggedIn (@RequestBody UserRequest userRequest) {
        User user = userService.findByUsername(userRequest.getUsername());
        if (user.getPassword().compareTo(userRequest.getPassword()) == 0) {
            return new UserResponse(user);
        }
        return null;
    }

    @PostMapping("/api/signUp")
    @ResponseBody
    public UserResponse signUp (@RequestBody UserRequest userRequest) {
        String userName = userRequest.getUsername();
        String pwd = userRequest.getPassword();
        if (userName == null) {
            throw new ResponseStatusException(HttpStatus.IM_USED);
        }
        if (userName == "" || pwd == "") {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        User user = userService.signUp(userRequest.getUsername(), userRequest.getPassword());
        return new UserResponse(user);
    }

    @GetMapping("/api/{id}/deleteUser")
    @ResponseBody
    public UserResponse deleteUser (@PathVariable Long id) {
        User user = userService.findByID(id);
        userService.delete(user);
        if (userService.findByID(id) == null) {
            return new UserResponse(user);
        }
        return null;
    }

    @PostMapping("/api/changePassword")
    @ResponseBody
    public UserResponse changePassword (@RequestBody UserRequest userRequest) {
        User user = userService.findByUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        userService.save(user);

        return new UserResponse(user);
    }
}
