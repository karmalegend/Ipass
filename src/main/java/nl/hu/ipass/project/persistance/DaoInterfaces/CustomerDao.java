package nl.hu.ipass.project.persistance.DaoInterfaces;

import nl.hu.ipass.project.persistance.pojos.Customer;

import java.util.ArrayList;

public interface CustomerDao {
    ArrayList<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomerbyId(int id);
    void updateEmailbyID(int id, String email);
}
