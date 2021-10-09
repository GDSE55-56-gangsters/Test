package lk.ijse.thogakade.bo.custom.impl;

import lk.ijse.thogakade.bo.SuperBO;
import lk.ijse.thogakade.dto.CustomerDTO;

import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    public boolean addCustomer(CustomerDTO customer) throws Exception;

    public boolean updateCustomer(CustomerDTO customer) throws Exception;

    public boolean deleteCustomer(String id) throws Exception;

    CustomerDTO searchCustomer(String id) throws Exception;

    ArrayList<CustomerDTO> getAllCustomers() throws Exception;
}
