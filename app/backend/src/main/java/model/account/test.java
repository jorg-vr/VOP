package model.account;

import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.RoleDAO;

import java.util.*;

/**
 * Created by Billie Devolder on 6/04/2017.
 */
public class test {

    public static void main(String[] args) {
        ProductionProvider.initializeProvider("localtest");
        try (RoleDAO roleDAO = ProductionProvider.getInstance().getRoleDAO()) {
            Role r1 = roleDAO.create(new Role("test1"));
            r1.setAccess(Resource.INSURANCE, Action.READ_ALL);
            r1.setAccess(Resource.INSURANCE, Action.UPDATE_ALL);
            r1.setAccess(Resource.INSURANCE, Action.REMOVE_ALL);
            r1.setAccess(Resource.INSURANCE, Action.CREATE_ALL);
            roleDAO.update(r1);
            Role r11 = roleDAO.get(r1.getUuid());
            r1.setAccess(Resource.BILLING, Action.READ_ALL);
            r1.setAccess(Resource.BILLING, Action.UPDATE_ALL);
            r1.setAccess(Resource.BILLING, Action.REMOVE_ALL);
            r1.setAccess(Resource.BILLING, Action.CREATE_ALL);
            roleDAO.update(r1);
            Role r12 = roleDAO.get(r1.getUuid());
            Map<Resource, Permission> permissionMap = new HashMap<>();
            Permission permission = new Permission();
            permission.setResource(Resource.COMPANY);
            permission.addAction(Action.READ_ALL);
            permission.addAction(Action.CREATE_ALL);
            permissionMap.put(Resource.COMPANY,permission);
            r1.setRights(permissionMap);
            roleDAO.update(r1);
            Role r13 = roleDAO.get(r1.getUuid());
            roleDAO.remove(r1.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
