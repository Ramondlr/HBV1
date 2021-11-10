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

    private long userID;
    private String recipeTitle;
    private String recipeText;
    private String recipeTag;
    private byte[] recipeImage;


    // Many recipes for a user
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    // Gauti er með milliveg (rental) fyrir user or book (hjá okkur user og recipe) ættum við að gera svipað?
    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    public Recipe() {
    }

    public Recipe(String title, String text, String tag, byte[] recipeImage) {
        this.recipeTitle = title;
        this.recipeText = text;
        this.recipeTag = tag;
        this.recipeImage = recipeImage;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

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

    public byte[] getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(byte[] image) {
        this.recipeImage = recipeImage;
    }
}
