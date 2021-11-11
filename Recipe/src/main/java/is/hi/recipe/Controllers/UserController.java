package is.hi.recipe.Controllers;

import is.hi.recipe.Persistence.Entities.User;
import is.hi.recipe.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

// prufa
@Controller
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // End points to add
    // Add a way to signup (GET, POST) using a form in html
    // login (GET, POST)
    // loggedin (GET) if not logged in -> redirect to homepage, otherwise it will display my username and possibly recipes?

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGET(User user){
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPOST(User user, BindingResult result, Model model){
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(User user){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPOST(User user, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            return "login";
        }
        User exists = userService.login(user);

        if(exists != null){
            // Þessi kóði þarf að vera til að geta séð notanda sem er skráður inn, inná userRecipe.html
            // Og það þarf að vera til staðar til að geta unnið með nafn notandans inná RecipeController
            session.setAttribute("LoggedInUser", exists);
            // Þessi kóði er óþarfi til að setja upp í model nafn notandans en ég (Ramon) vil halda í hann í bili
            // ef við viljum nota það seinna meir.
            // model.addAttribute("LoggedInUser", exists);
            return "redirect:/userRecipe";
        }
        return "redirect:/userRecipe";
    }

    // Þessi aðferð er óþarfi til að geta séð hver notandinn er sem er skráður inn.
/*
    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public String loggedinGET(HttpSession session, Model model){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if(sessionUser != null){
            model.addAttribute("LoggedInUser", sessionUser);
            return "loggedInUser";
        }
        return "redirect:/";
    }
 */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutGET(HttpSession session){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if(sessionUser != null){
            session.removeAttribute("LoggedInUser");
            // Ef það er ekki redirect:/ á undan 'logout' þá kemur (type=Internal Server Error, status=500)
            return "redirect:/logout";
        }
        return "redirect:/";
    }


    // TODO logoutGET method

    // TODO logoutPOST method
}
