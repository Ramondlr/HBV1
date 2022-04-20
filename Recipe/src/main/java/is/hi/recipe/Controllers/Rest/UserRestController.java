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

    @PostMapping("/api/isLoggedIn")
    @ResponseBody
    public UserResponse isLoggedIn (@RequestBody UserRequest userRequest) {
        User user = userService.findByUsername(userRequest.getUsername());
        String uss = user.getPassword();
        String usss = userRequest.getPassword();
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
            //return null;
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

    /*
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void loginPOST(User user){

        userService.login(user);
    }

     */


    /*

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String signupGET(User user){
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPOST(User user, BindingResult result){
        if(result.hasErrors()){
            return "redirect:/signup";
        }

        User exists = userService.findByUsername(user.getUsername());

        // Koma með almennilegt villuskilaboð, seinna meir.
        // Skoðar ef notandanafn er núþegar á skrá þegar nýr notandi er að reyna að sign up
        if(exists != null){
            return "redirect:/signup";
        }
        userService.save(user);

        // TODO búa til if condition til að checka ef exists != null þ.e.a.s. ef það er núþegar til user með sama
        //  username að meðhöndla þá villu.

        if(exists == null){
            userService.save(user);
        }
        return "redirect:/";
    }
     */
/**
    @GetMapping("/api/{id}/recipeList")
    @ResponseBody
    public RecipeResponse recipeList(@PathVariable("id") Long id) {
        return new RecipeResponse(recipeService.findUserRecipes(id));
    }
*/
}
