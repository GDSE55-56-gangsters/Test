package lk.ijse.thogakade.bo.custom.impl;


import lk.ijse.thogakade.dao.DAOFactory;
import lk.ijse.thogakade.dao.custom.CustomerDAO;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.model.Customer;

import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);


    @Override
    public boolean addCustomer(CustomerDTO customer) throws Exception {
        return customerDAO.add(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customer) throws Exception {
        return customerDAO.update(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
    }

    @Override
    public boolean deleteCustomer(String id) throws Exception {
        return customerDAO.delete(id);
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws Exception {
        Customer search = customerDAO.search(id);
        return  new CustomerDTO(search.getcID(),search.getName(),search.getAddress());
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws Exception {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer customer  : all) {
            allCustomers.add(new CustomerDTO(customer.getcID(),customer.getName(),customer.getAddress()));
        }
        return allCustomers;
    }


}
