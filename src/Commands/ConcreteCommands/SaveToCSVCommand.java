package Commands.ConcreteCommands;

import Commands.Command;
import Commands.CommandManager;

public class SaveToCSVCommand implements Command {
    /**
     * A field that refers to an object with implementations of all commands
     */
    CommandManager commandManager;

    public SaveToCSVCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    /**
     * The command that calls the required method from {@link CommandManager}
     */
    @Override
    public void execute() {
        commandManager.save();
    }
    /**
     * Method that returns command description
     * @return Command description
     */
    @Override
    public String description() {
        return "save: сохранить коллекцию в файл";
    }
}
