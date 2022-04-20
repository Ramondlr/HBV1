package is.hi.recipe.Services;

import is.hi.recipe.Persistence.Entities.User;

import java.util.List;

public interface UserService {
    User save(User user);
    void delete(User user);
    List<User> findAll();

    User findByID(Long ID);

    User findByUsername(String username);
    // For us to check when user is logging in if that user exists by trying to match the username and password to
    // something that exists in the database.
    User login(User user);

    User signUp(String username, String password);

}
