package ua.goit.dao;

import ua.goit.model.Customer;
import ua.goit.model.Developer;
import ua.goit.model.Project;

public class ProjectDao extends AbstractDao<Project> {

    private static ProjectDao instance;

    private ProjectDao() {
        super(Project.class);
    }

    public static ProjectDao getInstance() {
        return instance == null ? instance = new ProjectDao() : instance;
    }

    public void addDeveloper(Project project, Developer developer) {
        em.getTransaction().begin();
        project.getDevelopers().add(developer);
        em.merge(project);
        em.getTransaction().commit();
    }

    public void removeDeveloper(Project project, Developer developer) {
        em.getTransaction().begin();
        project.getDevelopers().remove(developer);
        em.merge(project);
        em.getTransaction().commit();
    }

    public void addCustomer(Project project, Customer customer) {
        project.getCustomers().add(customer);
        CustomerDao.getInstance().addProject(customer, project);
    }

    public void removeCustomer(Project project, Customer customer) {
        project.getCustomers().remove(customer);
        CustomerDao.getInstance().removeProject(customer, project);
    }
}
