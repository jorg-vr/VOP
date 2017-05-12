package controller.insurance;

import controller.AbstractController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAO;
import dao.interfaces.DAOManager;
import model.CommissionContainer;
import model.account.Function;
import model.account.Resource;
import model.account.Role;
import model.insurance.SuretyType;

import javax.persistence.criteria.Root;
import java.util.Map;
import java.util.UUID;

import static model.account.Action.*;

/**
 * Created by jorg on 5/10/17.
 */
public abstract class CommissionContainerController<T extends CommissionContainer> extends AbstractController<T> {

    private Function function;
    private Role role;
    private Resource resource;

    /**
     * @param dao      a DAO object on which the get, listFiltered and archive methods should be called.
     * @param resource Resource the function should have to be able to get,create,update and/or delete an object.
     * @param function of the user. This is used to determine the user has rights to do a certain operation
     */
    public CommissionContainerController(DAOManager manager, DAO<T> dao, Resource resource, Function function) {
        super(manager,dao, resource, function);
        this.function=function;
        this.role = function.getRole();
        this.resource=Resource.COMMISSION;
    }

    /***
     * @return Map containing a commission double for each suretytype
     */
    public Map<SuretyType, Double> getCommissions(UUID uuid) throws DataAccessException, ObjectNotFoundException, UnAuthorizedException {
        T t = getDao().get(uuid);
        if (role.hasAccess(resource, READ_ALL) ||
                (role.hasAccess(resource, READ_MINE) && isOwner(t, function))) {
            return t.getCommissions();
        } else {
            throw new UnAuthorizedException();
        }
    }

    /***
     * @param commissions Map containing a commission double for each suretytype
     */
    public Map<SuretyType, Double> setCommissions(UUID uuid,Map<SuretyType, Double> commissions) throws DataAccessException, ObjectNotFoundException, ConstraintViolationException, UnAuthorizedException {
        T t = getDao().get(uuid);
        if (role.hasAccess(resource, UPDATE_ALL) ||
                (role.hasAccess(resource, UPDATE_MINE) &&
                        isOwner(t, function))) {
            t.setCommissions(commissions);
            return getDao().update(t).getCommissions();
        } else {
            throw new UnAuthorizedException();
        }
    }


    @Override
    public T update(T t) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException {
        T old = get(t.getUuid());
        t.setCommissions(old.getCommissions());
        return super.update(t);
    }
}
