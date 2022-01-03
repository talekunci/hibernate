package ua.goit.dao;

import ua.goit.model.Customer;
import ua.goit.model.Project;

public class CustomerDao extends AbstractDao<Customer> {

    private static CustomerDao instance;

    private CustomerDao() {
        super(Customer.class);
    }

    public static CustomerDao getInstance() {
        return instance == null ? instance = new CustomerDao() : instance;
    }

    public void addProject(Customer customer, Project project) {
        em.getTransaction().begin();
        customer.getProjects().add(project);
        em.merge(customer);
        em.getTransaction().commit();
    }

    public void removeProject(Customer customer, Project project) {
        em.getTransaction().begin();
        customer.getProjects().remove(project);
        em.merge(customer);
        em.getTransaction().commit();
    }

}
