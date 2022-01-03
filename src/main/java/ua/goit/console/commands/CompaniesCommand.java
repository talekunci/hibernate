package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.CompanyDao;
import ua.goit.dao.DeveloperDao;
import ua.goit.model.Company;
import ua.goit.model.Developer;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class CompaniesCommand implements Command {

    private static final CompanyDao dao = CompanyDao.getInstance();
    private static final DeveloperDao developerDao = DeveloperDao.getInstance();

    @Override
    public void handle(String params, Consumer<Command> setActive) {
        String command = params.split(" ")[0];

        String subParams = params.replace(command + " ", "");
        switch (command) {
            case "getAll" -> getAll();
            case "get" -> get(subParams);
            case "getDevelopers" -> getDevelopers(subParams);
            case "create" -> create(subParams);
            case "addDeveloper" -> addDeveloper(subParams);
            case "removeDeveloper" -> removeDeveloper(subParams);
            case "delete" -> delete(subParams);
            case "update" -> update(subParams);
        }
    }

    private void create(String params) { // company create NAME [description]
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        Company company = new Company(paramsArray[0]);

        try {
            company.setDescription(paramsArray[1]);
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }

        dao.create(company);
        System.out.println("Company was created.");
    }

    private void get(String params) {  // company get ID
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
                () -> System.out.printf("Company with ID=%d not found.%n", id)
        );
    }

    private void getDevelopers(String params) {
        String idStr = params.split(" ")[0].trim();

        long id;

        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n");
            return;
        }

        dao.get(id).ifPresentOrElse(
                c -> {
                    Set<Developer> developers = c.getDevelopers();
                    if (developers.isEmpty()) {
                        System.out.printf("Company with ID=%d has no developers.%n", id);
                    } else {
                        developers.forEach(System.out::println);
                    }
                },
                () -> System.out.printf("Company with ID=%s not found.%n", idStr)
        );
    }

    private void addDeveloper(String params) {
        String[] paramsArray = params.split(" ");

        long companyId;
        long developerId;

        try {
            companyId = Long.parseLong(paramsArray[0]);
            developerId = Long.parseLong(paramsArray[1]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n");
            return;
        }

        dao.get(companyId).ifPresentOrElse(
                company -> {
                    developerDao.get(developerId).ifPresentOrElse(
                            developer -> {
                                dao.addDeveloper(company, developer);
                                System.out.println("Developer added to company.");
                            },
                            () -> System.out.printf("Developer with ID=%s not found.%n", paramsArray[1]));
                },
                () -> System.out.printf("Company with ID=%s not found.%n", paramsArray[0])
        );
    }

    private void removeDeveloper(String params) {
        String[] paramsArray = params.split(" ");

        long companyId;
        long developerId;

        try {
            companyId = Long.parseLong(paramsArray[0]);
            developerId = Long.parseLong(paramsArray[1]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n");
            return;
        }

        dao.get(companyId).ifPresentOrElse(
                company -> {
                    developerDao.get(developerId).ifPresentOrElse(
                            developer -> {
                                dao.removeDeveloper(company, developer);
                                System.out.println("Developer removed by company.");
                            },
                            () -> System.out.printf("Developer with ID=%s not found.%n", paramsArray[1]));
                },
                () -> System.out.printf("Company with ID=%s not found.%n", paramsArray[0])
        );
    }

    private void getAll() { // company getAll
        dao.getAll().forEach(System.out::println);
    }

    private void delete(String params) {    // company delete ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***");
            return;
        }

        dao.get(id).ifPresent(company -> {
            dao.delete(company);
            System.out.printf("Company with ID=%s was deleted.%n", paramsArray[0]);
        });

    }

    private void update(String params) {    // company update ID NAME [description]
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

        Optional<Company> company = dao.get(id);

        if (company.isPresent()) {
            Company result = company.get();

            try {
                result.setName(paramsArray[1]);
                result.setDescription(paramsArray[2]);
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }

            dao.update(result);
            System.out.printf("Company with ID=%s was updated.%n", paramsArray[0]);
        } else {
            System.out.printf("Company with ID=%s not found.%n", paramsArray[0]);
        }
    }

    @Override
    public void printActiveMenu() {
        System.out.println("--------Company menu--------");
        System.out.println("Commands: ");
        System.out.println("\t* create NAME [description]");
        System.out.println("\t* get ID");
        System.out.println("\t* getDevelopers ID");
        System.out.println("\t* addDeveloper COMPANY_ID DEVELOPER_ID");
        System.out.println("\t* removeDeveloper COMPANY_ID DEVELOPER_ID");
        System.out.println("\t* getAll");
        System.out.println("\t* update ID NAME [description]");
        System.out.println("\t* delete ID");
    }
}
