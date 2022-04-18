package is.hi.recipe.Persistence.Entities;


import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private long userID;
    private String recipeTitle;
    @Column(length = 10000)
    private String recipeText;
    private String recipeTag;
    @Column(nullable = true, length = 64)
    private String recipeImage;



    // Many recipes for a user
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    public Recipe() {
    }

    public Recipe(String title, String text, String tag, String recipeImage) {
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

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }
    /**
    @Transient
    public String getRecipeImagePath() {
        return "/upload/recipeImage/" + this.userID + "/" + this.ID + "/" + this.recipeImage;
    }*/
}
