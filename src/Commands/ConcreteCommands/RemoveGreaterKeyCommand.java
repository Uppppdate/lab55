package Commands.ConcreteCommands;

import Commands.Command;
import Commands.CommandManager;

public class RemoveGreaterKeyCommand implements Command {
    /**
     * A field that refers to an object with implementations of all commands
     */
    CommandManager commandManager;

    public RemoveGreaterKeyCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    /**
     * The command that calls the required method from {@link CommandManager}
     */
    @Override
    public void execute() {
        commandManager.removeGreaterKey();
    }
    /**
     * Method that returns command description
     * @return Command description
     */
    @Override
    public String description() {
        return "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный";
    }
}