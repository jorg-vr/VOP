package controller;

import dao.interfaces.UserDAO;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.account.User;

/**
 * Created by Billie Devolder on 5/04/2017.
 */
public class UserController extends AbstractController<User> {

    public UserController(Function function) {
        super(BackendApplication.getProvider().getUserDAO(), Resource.USER,function);
    }

    @Override
    public boolean isOwner(User user, Function function) {
        return false;
    }
}
