package is.hi.recipe.Controllers.Rest.Response;

import is.hi.recipe.Persistence.Entities.User;

public class UserResponse {

    private long id;
    private String username;

    public UserResponse(User user) {
        this.id = user.getID();
        this.username = user.getUsername();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
