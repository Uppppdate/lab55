package Commands.ConcreteCommands;

import Commands.Command;
import Commands.CommandManager;

public class AveragePriceCommand implements Command {
    /**
     * A field that refers to an object with implementations of all commands
     */
    CommandManager commandManager;

    public AveragePriceCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    /**
     * The command that calls the required method from {@link CommandManager}
     */
    @Override
    public void execute() {
        commandManager.averagePrice();
    }
    /**
     * Method that returns command description
     * @return Command description
     */
    @Override
    public String description() {
        return "average_of_price : вывести среднее значение поля price для всех элементов коллекции";
    }
}

