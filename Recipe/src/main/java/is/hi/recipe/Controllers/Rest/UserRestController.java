package is.hi.recipe.Controllers.Rest;

import is.hi.recipe.Persistence.Entities.User;
import is.hi.recipe.Services.UserService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    private UserService userService;
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
}
