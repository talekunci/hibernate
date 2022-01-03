package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.CompanyDao;
import ua.goit.dao.DeveloperDao;
import ua.goit.dao.ProjectDao;
import ua.goit.dao.SkillDao;
import ua.goit.model.Company;
import ua.goit.model.Developer;
import ua.goit.model.Project;
import ua.goit.model.Skill;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class DevelopersCommand implements Command {

    private static final DeveloperDao developerDao = DeveloperDao.getInstance();
    private static final SkillDao skillDao = SkillDao.getInstance();
    private static final ProjectDao projectDao = ProjectDao.getInstance();

    @Override
    public void handle(String params, Consumer<Command> setActive) {
        String command = params.split(" ")[0];

        String subParams = params.replace(command + " ", "");
        switch (command) {
            case "getAll" -> getAll();
            case "get" -> get(subParams);
            case "getSkills" -> getSkills(subParams);
            case "getProjects" -> getProjects(subParams);
            case "getCompanies" -> getCompanies(subParams);
            case "getSumSalaryByProjectId" -> getSalary(subParams);
            case "projectByProjectId" -> getDevelopersByProjectId(subParams);
            case "listBySkillId" -> getDevelopersBySkill(subParams);
            case "listBySkillBranch" -> getDevelopersBySkillBranch(subParams);
            case "listBySkillLevel" -> getDevelopersBySkillLevel(subParams);
            case "addSkill" -> addSkill(subParams);
            case "removeSkill" -> removeSkill(subParams);
            case "addCompany" -> addCompany(subParams);
            case "removeCompany" -> removeCompany(subParams);
            case "create" -> create(subParams);
            case "delete" -> delete(subParams);
            case "update" -> update(subParams);
        }
    }

    private void get(String params) {  // developers get ID
        String paramsArray = params.split(" ")[0];

        if (paramsArray.length() == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n");
            return;
        }

        developerDao.get(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.printf("Developer with ID=%d not found.%n", id)
        );
    }

    private void getSkills(String params) {  // developers getSkills ID
        String paramsArray = params.split(" ")[0];

        if (paramsArray.length() == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n");
            return;
        }

        developerDao.get(id).ifPresentOrElse(
                d -> {
                    Set<Skill> skills = d.getSkills();
                    if (skills.isEmpty()) {
                        System.out.printf("Developer with ID=%d has no skills.%n", id);
                    } else {
                        skills.forEach(System.out::println);
                    }
                },
                () -> System.out.printf("Developer with ID=%d not found.%n", id)
        );
    }

    private void getCompanies(String params) {  // developers getSkills ID
        String paramsArray = params.split(" ")[0];

        if (paramsArray.length() == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n");
            return;
        }

        developerDao.get(id).ifPresentOrElse(
                d -> {
                    Set<Company> companies = d.getCompanies();
                    if (companies.isEmpty()) {
                        System.out.printf("Developer with ID=%d has no companies.%n", id);
                    } else {
                        companies.forEach(System.out::println);
                    }
                },
                () -> System.out.printf("Developer with ID=%d not found.%n", id)
        );
    }

    private void getProjects(String params) {  // developers getSkills ID
        String paramsArray = params.split(" ")[0];

        if (paramsArray.length() == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n");
            return;
        }

        developerDao.get(id).ifPresentOrElse(
                d -> {
                    Set<Project> projects = d.getProjects();
                    if (projects.isEmpty()) {
                        System.out.printf("Developer with ID=%d has no projects.%n", id);
                    } else {
                        projects.forEach(System.out::println);
                    }
                },
                () -> System.out.printf("Developer with ID=%d not found.%n", id)
        );
    }

    private void getSalary(String params) {
        String paramsArray = params.split(" ")[0];

        if (paramsArray.length() == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n");
            return;
        }

        int salarySum = 0;

        Optional<Project> projectOptional = projectDao.get(id);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();

            salarySum = project.getDevelopers()
                    .stream()
                    .map(Developer::getSalary)
                    .reduce(0, Integer::sum);
        }

        System.out.printf("Sum salaries of all project developers by project_ID [%s]: %d\n", params.split(" ")[0], salarySum);
    }

    private void getDevelopersByProjectId(String params) {
        String paramsArray = params.split(" ")[0];

        if (paramsArray.length() == 0) return;

        long projectId;

        try {
            projectId = Long.parseLong(paramsArray);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format.***\n");
            return;
        }

        Optional<Project> projectOptional = projectDao.get(projectId);

        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            System.out.println("Project: " + project);

            if (project.getDevelopers().isEmpty()) {
                System.out.println("The project has no developers.");
            } else {
                project.getDevelopers()
                        .forEach(System.out::println);
            }
        } else {
            System.out.printf("Project with ID=%d not found.\n", projectId);
        }
    }

    private void getDevelopersBySkill(String params) {
        String paramsArray = params.split(" ")[0];

        if (paramsArray.length() == 0) return;

        Long skillId;

        try {
            skillId = Long.parseLong(paramsArray);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format.***\n\tDefault ID = 0");
            return;
        }

        Optional<Skill> skillOptional = skillDao.get(skillId);

        if (skillOptional.isPresent()) {
            Skill skill = skillOptional.get();

            System.out.println("Skill: " + skill);

            List<Developer> bySkill = developerDao.getBySkill(skill);

            if (bySkill.isEmpty()) {
                System.out.printf("Developers by skill -->%s<-- not found.%n", skill);
            } else {
                bySkill.forEach(System.out::println);
            }
        } else {
            System.out.printf("Skill with ID=%d not found.\n", skillId);
        }
    }

    private void getDevelopersBySkillBranch(String params) {
        String branch = params.split(" ")[0];

        if (branch.length() == 0) return;

        List<Skill> skills = skillDao.getByBranch(branch);
        if (skills.isEmpty()) {
            System.out.printf("Skills by branch=>%s< not found.%n", branch);
            return;
        } else {
            System.out.println("Skills by branch:\n");
            skills.forEach(System.out::println);
        }

        List<Developer> bySkillBranch = developerDao.getBySkillBranch(branch);

        if (bySkillBranch.isEmpty()) {
            System.out.printf("Developers by skill branch -->%s<-- not found.%n", branch);
        } else {
            bySkillBranch.forEach(System.out::println);
        }
    }

    private void getDevelopersBySkillLevel(String params) {
        String skillLevel = params.split(" ")[0];

        if (skillLevel.length() == 0) return;

        List<Skill> skillsByLevel = skillDao.getByLevel(skillLevel);
        if (skillsByLevel.isEmpty()) {
            System.out.printf("Skills by skill level=>%s< not found.%n", skillLevel);
            return;
        } else {
            System.out.println("\nSkills by skill_level:");
            skillsByLevel.forEach(System.out::println);
        }

        System.out.println("\nDevelopers:");

        List<Developer> bySkillLevel = developerDao.getBySkillLevel(skillLevel);

        if (bySkillLevel.isEmpty()) {
            System.out.printf("Developers by skill level -->%s<-- not found.%n", skillLevel);
        } else {
            bySkillLevel.forEach(System.out::println);
        }
    }

    private void addSkill(String subParams) { // developer addSkill DEVELOPER_ID SKILL_ID
        String[] paramsArray = subParams.split(" ");

        if (paramsArray.length < 2) return;

        try {
            Optional<Developer> developer = DeveloperDao.getInstance().get(Long.parseLong(paramsArray[0]));
            Optional<Skill> skill = SkillDao.getInstance().get(Long.parseLong(paramsArray[1]));

            if (developer.isEmpty() || skill.isEmpty()) {
                System.out.println("Developer or Skill does not found.");
                return;
            }

            developerDao.addSkill(developer.get(), skill.get());
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
            return;
        }
        System.out.println("Skill added to developer.");

    }

    private void removeSkill(String subParams) { // developer removeSkill DEVELOPER_ID SKILL_ID
        String[] paramsArray = subParams.split(" ");

        if (paramsArray.length < 2) return;

        try {
            Optional<Developer> developer = DeveloperDao.getInstance().get(Long.parseLong(paramsArray[0]));
            Optional<Skill> skill = SkillDao.getInstance().get(Long.parseLong(paramsArray[1]));

            if (developer.isEmpty() || skill.isEmpty()) {
                System.out.println("Developer or Skill does not found.");
                return;
            }

            developerDao.removeSkill(developer.get(), skill.get());
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
            return;
        }
        System.out.println("Skill removed by developer.");

    }

    private void addCompany(String params) { // developer addCompany DEVELOPER_ID COMPANY_ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        try {
            Optional<Developer> developerOptional = DeveloperDao.getInstance().get(Long.parseLong(paramsArray[0]));
            Optional<Company> company = CompanyDao.getInstance().get(Long.parseLong(paramsArray[1]));

            if (developerOptional.isEmpty() || company.isEmpty()) {
                System.out.println("Developer or Company does not found.");
                return;
            }

            developerDao.addCompany(developerOptional.get(), company.get());
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
            return;
        }

        System.out.println("Company added to developer.");
    }

    private void removeCompany(String params) { // developer removeCompany DEVELOPER_ID COMPANY_ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        try {
            Optional<Developer> developerOptional = DeveloperDao.getInstance().get(Long.parseLong(paramsArray[0]));
            Optional<Company> company = CompanyDao.getInstance().get(Long.parseLong(paramsArray[1]));

            if (developerOptional.isEmpty() || company.isEmpty()) {
                System.out.println("Developer or Company does not found.");
                return;
            }

            developerDao.removeCompany(developerOptional.get(), company.get());
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
            return;
        }

        System.out.println("Company removed by developer.");
    }

    private void create(String params) { // developers create NAME [age] [gender] [description] [salary]
        if ("create".equals(params)) {
            System.out.println("Empty parameters.");
            return;
        }
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        Developer developer = new Developer(paramsArray[0]);

        try {
            developer.setAge(Integer.parseInt(paramsArray[1]));
            developer.setGender(paramsArray[2]);
            developer.setDescription(paramsArray[3]);
            developer.setSalary(Integer.parseInt(paramsArray[4]));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ignore) {
        }

        developerDao.create(developer);
        System.out.println("Developer was created.");
    }

    private void getAll() { // developers getAll
        developerDao.getAll().forEach(System.out::println);
    }

    private void delete(String subParams) {    // developers delete ID
        String[] paramsArray = subParams.split(" ");

        if (paramsArray.length == 0) return;

        long developerId;

        try {
            developerId = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***");
            return;
        }

        developerDao.get(developerId).ifPresent(developer -> {
            developerDao.delete(developer);
            System.out.println("Developer was deleted.");
        });
    }

    private void update(String subParams) {    // developers update ID NAME [age] [gender] [description] [salary]
        String[] paramsArray = subParams.split(" ");

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

        Optional<Developer> developerOptional = developerDao.get(id);

        if (developerOptional.isPresent()) {
            Developer developer = developerOptional.get();

            try {
                developer.setName(paramsArray[1]);

                developer.setAge(Integer.parseInt(paramsArray[2]));
                developer.setGender(paramsArray[3]);
                developer.setDescription(paramsArray[4]);
                developer.setSalary(Integer.parseInt(paramsArray[5]));
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException ignore) {
            }

            developerDao.update(developer);
            System.out.printf("Developer with ID=%s was updated.%n", paramsArray[0]);
        } else {
            System.out.printf("Developer with ID=%s not found.%n", paramsArray[0]);
        }
    }

    @Override
    public void printActiveMenu() {
        System.out.println("--------Developer menu--------");
        System.out.println("Commands: ");
        System.out.println("\t* create NAME [age] [gender] [description] [salary]");
        System.out.println("\t* get ID");
        System.out.println("\t* getSkills ID");
        System.out.println("\t* getCompanies ID");
        System.out.println("\t* getSumSalaryByProjectId PROJECT_ID");
        System.out.println("\t* projectDevelopersByProjectId PROJECT_ID");
        System.out.println("\t* listBySkillId SKILL_ID");
        System.out.println("\t* listBySkillBranch SKILL_BRANCH");
        System.out.println("\t* listBySkillLevel SKILL_LEVEL");
        System.out.println("\t* getAll");
        System.out.println("\t* addSkill DEVELOPER_ID SKILL_ID");
        System.out.println("\t* removeSkill DEVELOPER_ID SKILL_ID");
        System.out.println("\t* addCompany DEVELOPER_ID SKILL_ID");
        System.out.println("\t* removeCompany DEVELOPER_ID SKILL_ID");
        System.out.println("\t* update ID NAME [age] [gender] [description] [salary]");
        System.out.println("\t* delete ID");
    }
}
