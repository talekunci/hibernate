package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.SkillDao;
import ua.goit.model.Skill;

import java.util.Optional;
import java.util.function.Consumer;

public class SkillsCommand implements Command {

    private static final SkillDao dao = SkillDao.getInstance();

    @Override
    public void handle(String params, Consumer<Command> setActive) {
        String command = params.split(" ")[0];

        String subParams = params.replace(command + " ", "");
        switch (command) {
            case "getAll" -> getAll();
            case "get" -> get(subParams);
            case "create" -> create(subParams);
            case "delete" -> delete(subParams);
            case "update" -> update(subParams);
        }
    }

    private void create(String params) { // skills create BRANCH SKILL_LEVEL
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        dao.create(new Skill(paramsArray[0], paramsArray[1]));

        System.out.println("Skill was created.");
    }

    private void get(String params) {  // developers get ID
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
                () -> System.out.printf("Skill with ID=%d not found.\n", id)
        );
    }

    private void getAll() { // developers getAll
        dao.getAll().forEach(System.out::println);
    }

    private void delete(String params) {    // developers delete ID
        String[] paramsArray = params.split(" ");

        if(paramsArray.length == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format.***");
            return;
        }

        dao.get(id).ifPresent(skill -> {
            dao.delete(skill);
            System.out.printf("Skill with ID=%s was deleted.%n", paramsArray[0]);
        });
    }

    private void update(String params) {    // developers update ID BRANCH SKILL_LEVEL
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) {
            System.out.println("Not enough arguments.");
            return;
        }

        long id;

        try {
            id = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format.***");
            return;
        }

        Optional<Skill> skillOptional = dao.get(id);

        if (skillOptional.isPresent()) {
            Skill skill = skillOptional.get();

            try {
                skill.setBranch(paramsArray[1]);
                skill.setSkillLevel(paramsArray[2]);
            } catch (ArrayIndexOutOfBoundsException ignored) {}

            dao.update(skill);
            System.out.printf("Skill with ID=%s was updated.%n", paramsArray[0]);
        } else {
            System.out.printf("Skill with ID=%s not found.%n", paramsArray[0]);
        }
    }

    @Override
    public void printActiveMenu() {
        System.out.println("-------Skills menu-------");
        System.out.println("Commands: ");
        System.out.println("\t* create BRANCH SKILL_LEVEL");
        System.out.println("\t* get ID");
        System.out.println("\t* getAll");
        System.out.println("\t* update ID BRANCH SKILL_LEVEL");
        System.out.println("\t* delete ID");
    }
}

