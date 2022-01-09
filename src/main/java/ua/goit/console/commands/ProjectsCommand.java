package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.CompanyDao;
import ua.goit.dao.CustomerDao;
import ua.goit.dao.DeveloperDao;
import ua.goit.dao.ProjectDao;
import ua.goit.model.Company;
import ua.goit.model.Customer;
import ua.goit.model.Developer;
import ua.goit.model.Project;

import java.sql.Date;
import java.util.Optional;
import java.util.function.Consumer;

public class ProjectsCommand implements Command {

    private static final ProjectDao projectDao = ProjectDao.getInstance();
    private static final DeveloperDao developerDao = DeveloperDao.getInstance();

    @Override
    public void handle(String params, Consumer<Command> setActive) {
        String command = params.split(" ")[0];

        String subParams = params.replace(command + " ", "");
        switch (command) {
            case "getAll" -> getAll();
            case "getAllAndFormattedPrint" -> getFormatAndPrint();
            case "get" -> get(subParams);
            case "getDevelopers" -> getDevelopers(subParams);
            case "getCustomers" -> getCustomers(subParams);
            case "addCustomer" -> addCustomer(subParams);
            case "removeCustomer" -> removeCustomer(subParams);
            case "addDeveloper" -> addDeveloper(subParams);
            case "removeDeveloper" -> removeDeveloper(subParams);
            case "create" -> create(subParams);
            case "delete" -> delete(subParams);
            case "update" -> update(subParams);
        }
    }

    private void create(String params) { // projects create NAME COMPANY_ID [description] [cost] [creation_date]
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        long companyId = Long.parseLong(paramsArray[0]);
        Optional<Company> company = CompanyDao.getInstance().get(companyId);

        if (company.isEmpty()) {
            System.out.printf("Company with id=%d does not exits.%n", companyId);
            return;
        }

        Project project = new Project(paramsArray[1], company.get());

        try {
            project.setDescription(paramsArray[2]);
            project.setCost(Integer.parseInt(paramsArray[3]));
            project.setCreationDate(Date.valueOf(paramsArray[4]));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ignore) {
        }

        projectDao.create(project);
    }

    private void get(String params) {  // projects get ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n");
            return;
        }

        projectDao.get(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.printf("Project with ID=%d not found.%n", id)
        );
    }

    private void getDevelopers(String params) {  // projects getProjects ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n");
            return;
        }

        projectDao.get(id).ifPresentOrElse(
                project -> project.getDevelopers().forEach(System.out::println),
                () -> System.out.printf("Project with ID=%d not found.%n", id)
        );

    }

    private void getCustomers(String params) {  // projects getProjects ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n");
            return;
        }

        projectDao.get(id).ifPresentOrElse(
                project -> project.getCustomers().forEach(System.out::println),
                () -> System.out.printf("Project with ID=%d not found.%n", id)
        );
    }

    private void getFormatAndPrint() {
        projectDao.getAll().forEach(project -> System.out.printf(
                "project creation date: %s - project name: %-6s - amount of project developers: %d\n",
                project.getCreationDate().toString(),
                project.getName(),
                project.getDevelopers().size()
        ));
    }

    private void getAll() { // projects getAll
        projectDao.getAll().forEach(System.out::println);
    }

    private void addCustomer(String params) { // project addCustomer DEVELOPER_ID COMPANY_ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        try {
            Optional<Project> project = projectDao.get(Long.parseLong(paramsArray[0]));
            Optional<Customer> customer = CustomerDao.getInstance().get(Long.parseLong(paramsArray[1]));

            if (project.isEmpty() || customer.isEmpty()) {
                System.out.println("Project or Customer does not found.");
                return;
            }

            projectDao.addCustomer(project.get(), customer.get());
            System.out.println("Customer added to project.");
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
        }
    }

    private void removeCustomer(String params) { // project removeCustomer DEVELOPER_ID COMPANY_ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        try {
            Optional<Project> project = projectDao.get(Long.parseLong(paramsArray[0]));
            Optional<Customer> customer = CustomerDao.getInstance().get(Long.parseLong(paramsArray[1]));

            if (project.isEmpty() || customer.isEmpty()) {
                System.out.println("Project or Customer does not found.");
                return;
            }

            projectDao.removeCustomer(project.get(), customer.get());
            System.out.println("Customer removed by project.");
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
        }
    }

    private void addDeveloper(String params) { // project addDeveloper DEVELOPER_ID COMPANY_ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        try {
            Optional<Project> project = projectDao.get(Long.parseLong(paramsArray[0]));
            Optional<Developer> developer = developerDao.get(Long.parseLong(paramsArray[1]));

            if (project.isEmpty() || developer.isEmpty()) {
                System.out.println("Project or Developer does not found.");
                return;
            }

            projectDao.addDeveloper(project.get(), developer.get());
            System.out.println("Developer added to project.");
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
        }
    }

    private void removeDeveloper(String params) { // project remove DEVELOPER_ID COMPANY_ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        try {
            Optional<Project> project = projectDao.get(Long.parseLong(paramsArray[0]));
            Optional<Developer> developer = developerDao.get(Long.parseLong(paramsArray[1]));

            if (project.isEmpty() || developer.isEmpty()) {
                System.out.println("Project or Developer does not found.");
                return;
            }

            projectDao.removeDeveloper(project.get(), developer.get());
            System.out.println("Developer removed by project.");
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
        }
    }

    private void delete(String params) {    // projects delete ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***");
            return;
        }

        projectDao.get(id).ifPresent(project -> {
            projectDao.delete(project);
            System.out.printf("Project with ID=%s was deleted.%n", paramsArray[0]);
        });

    }

    private void update(String params) {    // projects update ID COMPANY_ID NAME [description] [cost] [creation_date]
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) {
            System.out.println("Not enough arguments.");
            return;
        }

        long projectId;

        try {
            projectId = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format.***");
            return;
        }

        Optional<Project> projectOptional = projectDao.get(projectId);

        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();

            long companyId = Long.parseLong(paramsArray[1]);

            Optional<Company> company = CompanyDao.getInstance().get(companyId);
            if (company.isEmpty()) {
                System.out.printf("Company with id=%d does not exist.%n", companyId);
                return;
            }

            try {
                project.setCompany(company.get());
                project.setName(paramsArray[2]);
                project.setDescription(paramsArray[3]);
                project.setCost(Integer.parseInt(paramsArray[4]));
                project.setCreationDate(Date.valueOf(paramsArray[5]));
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException ignore) {
            }

            projectDao.update(project);
            System.out.println("Project was updated.");
        } else {
            System.out.printf("Project with ID %s not found.%n", paramsArray[0]);
        }
    }

    @Override
    public void printActiveMenu() {
        System.out.println("--------Project menu--------");
        System.out.println("Commands: ");
        System.out.println("\t* create NAME COMPANY_ID [description] [cost] [creation_date]");
        System.out.println("\t* get ID");
        System.out.println("\t* getAll");
        System.out.println("\t* getDevelopers ID");
        System.out.println("\t* getCustomers ID");
        System.out.println("\t* addCustomer PROJECT_ID CUSTOMER_ID");
        System.out.println("\t* removeCustomer PROJECT_ID CUSTOMER_ID");
        System.out.println("\t* addDeveloper PROJECT_ID DEVELOPER_ID");
        System.out.println("\t* removeDeveloper PROJECT_ID DEVELOPER_ID");
        System.out.println("\t* getAllAndFormattedPrint");
        System.out.println("\t* update ID NAME COMPANY_ID [description] [cost] [creation_date]");
        System.out.println("\t* delete ID");
    }
}
