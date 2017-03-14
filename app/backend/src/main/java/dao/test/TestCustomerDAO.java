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
public class TestCustomerDAO extends TestDAO<Customer> implements CustomerDAO {

    private Map<UUID,Customer> customers=new HashMap<>();

    public TestCustomerDAO() {
        UUID one=UUID.randomUUID();
        customers.put(one,new Customer(one,new Address("mystreet","1","tomtown","9000","tomland"),"tom@mail.com","047777777","tomcompany","123","BE456", CompanyType.TYPE1));

    }

    @Override
    public Customer get(UUID id) throws DataAccessException {
        return customers.get(id);
    }


    @Override
    public void remove(UUID id) throws DataAccessException {
        setMapping(customers);
    }

    @Override
    public Collection<Customer> listFiltered(Filter... filters) throws DataAccessException {
        List<Customer> result=new ArrayList<>();
        for(Customer customer:customers.values()){
            boolean b=true;
            for(Filter<Customer> f:filters){
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
    public Customer create(String name, Address address, String phonenumber, String btwNumber, Collection<Fleet> fleets) throws DataAccessException {
        Customer customer = new Customer();
        customer.setUuid(UUID.randomUUID());
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhoneNumber(phonenumber);
        customer.setBtwNumber(btwNumber);
        customer.setFleets(fleets);
        customers.put(customer.getUuid(), customer);
        return customer;
    }

    @Override
    public Customer update(UUID id,String name, Address address, String phonenumber, String btwNumber) throws DataAccessException {
        if (! customers.containsKey(id)) {
            throw new DataAccessException();
        }
        Customer customer = customers.get(id);
        customer.setUuid(id);
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhoneNumber(phonenumber);
        customer.setBtwNumber(btwNumber);
        return customer;
    }

    @Override
    public Filter<Customer> containsFleet(Fleet fleet) {
        return null;
    }

    @Override
    public Filter<Customer> byName(String name) {
        return null;
    }

    @Override
    public Filter<Customer> containsName(String name) {
        return null;
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
        return null;
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
