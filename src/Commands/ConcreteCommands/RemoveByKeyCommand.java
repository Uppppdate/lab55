package Commands.ConcreteCommands;

import Commands.Command;
import Commands.CommandManager;

public class RemoveByKeyCommand implements Command {
    /**
     * A field that refers to an object with implementations of all commands
     */
    CommandManager commandManager;

    public RemoveByKeyCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    /**
     * The command that calls the required method from {@link CommandManager}
     */
    @Override
    public void execute() {
        commandManager.removeByKey();
    }
    /**
     * Method that returns command description
     * @return Command description
     */
    @Override
    public String description() {
        return "remove_key null : удалить элемент из коллекции по его ключу";
    }
}
