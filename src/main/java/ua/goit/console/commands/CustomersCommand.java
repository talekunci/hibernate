package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.CustomerDao;
import ua.goit.dao.ProjectDao;
import ua.goit.model.Customer;
import ua.goit.model.Project;

import java.util.Optional;
import java.util.function.Consumer;

public class CustomersCommand implements Command {

    private static final CustomerDao dao = CustomerDao.getInstance();

    @Override
    public void handle(String params, Consumer<Command> setActive) {
        String command = params.split(" ")[0];

        String subParams = params.replace(command + " ", "");
        switch (command) {
            case "getAll" -> getAll();
            case "get" -> get(subParams);
            case "getProjects" -> getProjects(subParams);
            case "addProject" -> addProject(subParams);
            case "removeProject" -> removeProject(subParams);
            case "create" -> create(subParams);
            case "delete" -> delete(subParams);
            case "update" -> update(subParams);
        }
    }

    private void create(String params) { // customers create NAME [description]
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        Customer customer = new Customer(paramsArray[0]);

        try {
            customer.setDescription(paramsArray[1]);
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }

        dao.create(customer);
    }

    private void get(String params) {  // customers get ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n");
            return;
        }

        dao.get(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.printf("Customer with ID=%d not found.%n", id)
        );
    }

    private void getProjects(String params) {
        String paramsArray = params.split(" ")[0];

        if (paramsArray.length() == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n");
            return;
        }

        dao.get(id).ifPresentOrElse(
                customer -> customer.getProjects().forEach(System.out::println),
                () -> System.out.printf("Customer with ID=%d not found.%n", id)
        );
    }

    private void addProject(String params) { // customers addProject ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        try {
            Optional<Customer> customer = CustomerDao.getInstance().get(Long.parseLong(paramsArray[0]));
            Optional<Project> project = ProjectDao.getInstance().get(Long.parseLong(paramsArray[1]));

            if (customer.isEmpty() || project.isEmpty()) {
                System.out.println("Customer or Project does not found.");
                return;
            }

            dao.addProject(customer.get(), project.get());
            System.out.println("Project added to customer.");
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
        }
    }

    private void removeProject(String params) { // customers removeProject ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        try {
            Optional<Customer> customer = CustomerDao.getInstance().get(Long.parseLong(paramsArray[0]));
            Optional<Project> project = ProjectDao.getInstance().get(Long.parseLong(paramsArray[1]));

            if (customer.isEmpty() || project.isEmpty()) {
                System.out.println("Customer or Project does not found.");
                return;
            }

            dao.removeProject(customer.get(), project.get());
            System.out.println("Project removed by customer.");
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
        }
    }

    private void getAll() { // projects getAll
        dao.getAll().forEach(System.out::println);
    }

    private void delete(String params) {    // customers delete ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***");
            return;
        }

        dao.get(id).ifPresent(customer -> {
            dao.delete(customer);
            System.out.println("Customer was deleted.");
        });
    }

    private void update(String params) {    // customers update ID NAME [description]
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) {
            System.out.println("Not enough arguments.");
            return;
        }

        long id;

        try {
            id = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***");
            return;
        }

        Optional<Customer> customer = dao.get(id);

        if (customer.isPresent()) {
            Customer result = customer.get();

            try {
                result.setName(paramsArray[1]);
                result.setDescription(paramsArray[2]);
            } catch (ArrayIndexOutOfBoundsException ignored) {}

            dao.update(result);
            System.out.printf("Customer with ID=%s was updated.%n", paramsArray[0]);
        } else {
            System.out.printf("Customer with ID=%s not found.%n", paramsArray[0]);
        }
    }

    @Override
    public void printActiveMenu() {
        System.out.println("--------Customer menu--------");
        System.out.println("Commands: ");
        System.out.println("\t* create NAME [description]");
        System.out.println("\t* get ID");
        System.out.println("\t* getAll");
        System.out.println("\t* getProjects ID");
        System.out.println("\t* addProject ID");
        System.out.println("\t* removeProject ID");
        System.out.println("\t* update ID NAME [description]");
        System.out.println("\t* delete ID");
    }
}
