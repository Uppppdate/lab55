package Commands.ConcreteCommands;

import Commands.Command;
import Commands.CommandManager;

public class HistoryCommand implements Command {

    private final CommandManager commandManager;

    public HistoryCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * The command that calls the required method from {@link Commands.CommandManager}
     */
    @Override
    public void execute() {
        commandManager.history();
    }

    /**
     * Method that returns command description
     *
     * @return Command description
     */
    @Override
    public String description() {
        return "history : вывести последние 6 команд (без их аргументов)";
    }
}