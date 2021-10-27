package is.hi.recipe.Persistence.Entities;


import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String recipeTitle;
    private String recipeText;
    private String recipeTag;
    private static Image image;


    // Many recipes for a user
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    // Gauti er með milliveg (rental) fyrir user or book (hjá okkur user og recipe) ættum við að gera svipað?
    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    public Recipe() {
    }

    public Recipe(String title, String text, String tag, User id) {
        this.recipeTitle = title;
        this.recipeText = text;
        this.recipeTag = tag;
    }

    /*
    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }
     */

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getRecipeText() {
        return recipeText;
    }

    public void setRecipeText(String recipeText) {
        this.recipeText = recipeText;
    }

    public String getRecipeTag() {
        return recipeTag;
    }

    public void setRecipeTag(String recipeTag) {
        this.recipeTag = recipeTag;
    }

    public static Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
