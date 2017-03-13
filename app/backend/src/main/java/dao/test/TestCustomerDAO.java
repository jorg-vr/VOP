package dao.test;

import dao.interfaces.CustomerDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.fleet.Fleet;
import model.identity.Address;
import model.identity.CompanyType;
import model.identity.Customer;

import java.util.*;

/**
 * Created by jorg on 3/9/17.
 */
public class TestCustomerDAO implements CustomerDAO {

    private Map<UUID,Customer> customers=new HashMap<>();

    public TestCustomerDAO() {
        UUID one=UUID.randomUUID();
        customers.put(one,new Customer(one,new Address("mystreet","1","tomtown","9000","tomland"),"tom@mail.com","047777777","tomcompany","123","BE456", CompanyType.TYPE1));
    }

    @Override
    public Customer create(Customer customer) throws DataAccessException {
        UUID uuid=UUID.randomUUID();
        customer.setUuid(uuid);
        customers.put(uuid,customer);
        return customer;
    }

    @Override
    public Customer get(UUID id) throws DataAccessException {
        return customers.get(id);
    }

    @Override
    public void update(Customer customer) throws DataAccessException {
        customers.put(customer.getUuid(),customer);
    }

    @Override
    public void remove(Customer customer) throws DataAccessException {
        customers.remove(customer.getUuid());
    }

    @Override
    public void remove(UUID id) throws DataAccessException {

    }

    @Override
    public Collection<Customer> listFiltered(Filter... filters) throws DataAccessException {
        List<Customer> result=new ArrayList<>();
        for(Customer customer:customers.values()){
            boolean b=true;
            for(Filter<Customer> f:filters){
                b&=f.filter(customer);
            }
            if(b){
                result.add(customer);
            }
        }
        return result;
    }

    @Override
    public void close() {

    }

    @Override
    public Filter<Customer> containsFleet(Fleet fleet) {
        return (customer -> customer.getFleets().contains(fleet));
    }

    @Override
    public Filter<Customer> byName(String name) {
        return (customer->customer.getName().equals(name));
    }

    @Override
    public Filter<Customer> containsName(String name) {
        return customer -> customer.getName().contains(name);
    }

    @Override
    public Filter<Customer> byVatNumber(String vatNumber) {
        return null;
    }

    @Override
    public Filter<Customer> byPhoneNumber(String email) {
        return null;
    }

    @Override
    public Filter<Customer> byAddress(Address address) {
        return customer -> true;
    }

    @Override
    public Filter<Customer> byBankAccountNummber(String bankAccountNumber) {
        return null;
    }

    @Override
    public Filter<Customer> byEmail(String email) {
        return null;
    }
}
