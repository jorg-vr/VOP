package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import dao.interfaces.UserDAO;
import model.account.Function;
import model.account.Resource;
import model.account.User;

import java.util.Collection;

/**
 * Created by Billie Devolder on 5/04/2017.
 */
public class UserController extends AbstractController<User> {

    public UserController(Function function, DAOManager manager) {
        super(manager, manager.getUserDAO(), Resource.USER, function);
    }

    @Override
    public boolean isOwner(User user, Function function) {
        return function.getUser().equals(user);
    }

    public Collection<User> getFiltered(String email, String firstName, String lastName) throws DataAccessException, UnAuthorizedException {
        UserDAO dao = (UserDAO) getDao();

        return getAll(
                dao.byEmail(email),
                dao.byFirstName(firstName),
                dao.byLastName(lastName));
    }
}
