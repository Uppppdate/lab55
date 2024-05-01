package Commands.ConcreteCommands;

import Commands.Command;
import Commands.CommandManager;

public class FilterByPriceCommand implements Command {
    /**
     * A field that refers to an object with implementations of all commands
     */
    CommandManager commandManager;

    public FilterByPriceCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    /**
     * The command that calls the required method from {@link CommandManager}
     */
    @Override
    public void execute() {
        commandManager.filterByPrice();
    }
    /**
     * Method that returns command description
     * @return Command description
     */
    @Override
    public String description() {
        return "filter_by_price price : вывести элементы, значение поля price которых равно заданному";
    }
}

