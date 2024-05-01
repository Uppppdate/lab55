package Commands.ConcreteCommands;

import Commands.Command;
import Commands.CommandManager;

public class InsertCommand implements Command {
    /**
     * A field that refers to an object with implementations of all commands
     */
    CommandManager commandManager;

    public InsertCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }


    /**
     * The command that calls the required method from {@link CommandManager}
     */
    @Override
    public void execute() {
        commandManager.insert();
    }
    /**
     * Method that returns command description
     * @return Command description
     */
    @Override
    public String description() {
        return "insert null {element} : добавить новый элемент с заданным ключом";
    }
}
