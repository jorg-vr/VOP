package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;

import dao.interfaces.DAOManager;
import dao.interfaces.UserDAO;
import model.account.Function;
import model.account.Resource;
import model.account.User;
import model.insurance.VehicleInsurance;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Billie Devolder on 5/04/2017.
 */
public class UserController extends AbstractController<User> {

    private UserDAO dao;
    private ControllerManager controllerManager;

    public UserController(Function function, DAOManager manager, ControllerManager controllerManager) {
        super(manager, manager.getUserDAO(), Resource.USER, function);
        this.dao = manager.getUserDAO();
        this.controllerManager = controllerManager;
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

    /**
     * @param email     Only returns users for which the email contains the pattern email
     * @param firstName Only returns users for which the firstName contains the pattern firstName
     * @param lastName  Only returns users for which the lastName contains the pattern lastName
     * @return all users, filtered on the arguments
     * @throws DataAccessException
     * @throws UnAuthorizedException
     */
    public Collection<User> getFiltered(String email, String firstName, String lastName) throws DataAccessException, UnAuthorizedException {
        return getAll(
                dao.byEmail(email),
                dao.byFirstName(firstName),
                dao.byLastName(lastName));
    }

    @Override
    public void archive(UUID uuid) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException {
        for (Function function : get(uuid).getFunctions()) {
            controllerManager.getFunctionController().archive(function.getUuid());
        }
        super.archive(uuid);
    }
}
