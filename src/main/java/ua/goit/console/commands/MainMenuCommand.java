package ua.goit.console.commands;

import ua.goit.console.Command;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class MainMenuCommand implements Command {

    private final Map<String, Command> commands = Map.of(
            "developers", new DevelopersCommand(),
            "skills", new SkillsCommand(),
            "projects", new ProjectsCommand(),
            "companies", new CompaniesCommand(),
            "customers", new CustomersCommand()
    );

    @Override
    public void handle(String params, Consumer<Command> setActive) {
        Optional<String> commandString = getCommandString(params);

        commandString.map(commands::get)
                .ifPresent(command -> {
                    setActive.accept(command);
                    command.handle(params.replace(commandString.get(),
                            "").trim(), setActive);
                });
    }

    @Override
    public void printActiveMenu() {
        System.out.println("------Global Commands------");
        System.out.println("\t*main - go to Main menu");
        System.out.println("\t*active - show current menu");
        System.out.println("---------------------------\n");

        System.out.println("----------Main menu----------");
        System.out.println("Menu list: ");
        commands.forEach((k, v) -> System.out.println("\t*" + k));
    }
}
