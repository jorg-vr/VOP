package controller;

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
}
