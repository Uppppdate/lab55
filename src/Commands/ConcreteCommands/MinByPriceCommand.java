package Commands.ConcreteCommands;

import Commands.Command;
import Commands.CommandManager;

public class MinByPriceCommand implements Command {
    /**
     * A field that refers to an object with implementations of all commands
     */
    CommandManager commandManager;

    public MinByPriceCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    /**
     * The command that calls the required method from {@link CommandManager}
     */
    @Override
    public void execute() {
        commandManager.minByPrice();
    }
    /**
     * Method that returns command description
     * @return Command description
     */
    @Override
    public String description() {
        return "min_by_price : вывести любой объект из коллекции, значение поля price которого является минимальным";
    }
}