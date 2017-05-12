package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Resource;
import model.account.User;

/**
 * Created by Billie Devolder on 5/04/2017.
 */
public class UserController extends AbstractController<User> {

    public UserController(Function function, DAOManager manager) {
        super(manager, manager.getUserDAO(), Resource.USER,function);
    }

    @Override
    public boolean isOwner(User user, Function function) {
        return function.getUser().equals(user);
    }

    @Override
    public User update(User user) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException {
        // If the updated user object doesn't have a password, set it equal to the current password of the user
        if (user.getPassword() == null) {
            User old = get(user.getUuid());
            user.setPassword(old.getPassword());
        }
        return super.update(user);
    }
}
