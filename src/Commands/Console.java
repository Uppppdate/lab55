package Commands;

import CSV.CSVCommands;
import Commands.ConcreteCommands.*;
import Tickets.*;

import java.util.*;

/**
 * Class in which all objects necessary for work are created and the program starts working
 */
public class Console {
    /**
     * A field that refers to a HashMap with keys - command names and the possibility of calling them
     */
    Map<String, Command> commands = new HashMap<>();

    /**
     * The starting point of the program. A method in which a database file is specified, commands are specified, and commands are entered.
     */
    public void toStart() {
        TicketCollection collection = new TicketCollection(Objects.requireNonNull(CSVCommands.toParse()));
        CSVCommands csv = new CSVCommands(collection);
        CommandManager commandManager = new CommandManager(collection, csv, commands);
        commands.put("help", new HelpCommand(commandManager));
        commands.put("save", new SaveToCSVCommand(commandManager));
        commands.put("clear", new ClearCollectionCommand(commandManager));
        commands.put("show", new ShowCollectionCommand(commandManager));
        commands.put("exit", new ExitCommand(commandManager));
        commands.put("insert", new InsertCommand(commandManager));
        commands.put("info", new InfoCommand(commandManager));
        commands.put("update", new UpdateCommand(commandManager));
        commands.put("execute_script", new ExecuteScriptCommand(commandManager));
        commands.put("remove_key", new RemoveKeyCommand(commandManager));
        commands.put("min_by_price", new MinByPriceCommand(commandManager));
        commands.put("filter_by_price", new FilterByPriceCommand(commandManager));
        commands.put("average_of_price", new AveragePriceCommand(commandManager));
        commands.put("remove_greater_key", new RemoveGreaterKeyCommand(commandManager));
        commands.put("replace_if_greater", new ReplaceIfGreaterCommand(commandManager));
        commands.put("history", new HistoryCommand(commandManager));
        System.out.println("Print the command");
        while (true) {
            String request = null;
            try{
            Scanner scan = new Scanner(System.in);
            request = scan.nextLine().toLowerCase();
            }
            catch (NoSuchElementException e){
                System.out.println("No line, exit from the program");
                break;
            }
            String[] tokens = request.split(" ");
            if (tokens.length == 0) {
                tokens = request.split("\n");
            }
            commandManager.setTokens(tokens);
            Command command = commands.get(tokens[0]);
            try {
                command.execute();
                if(commandManager.getCommandsHistory().size()==6) {
                    commandManager.getCommandsHistory().removeFirst();
                    commandManager.getCommandsHistory().addLast(tokens[0]);
                }
                else {commandManager.getCommandsHistory().addLast(tokens[0]);}
                collection.updateData();
                collection.updateNumeration();
            } catch (NullPointerException e) {
                System.out.println("The entered command does not exist");
            } catch (NoSuchElementException e){
                System.out.println("No line, exit from the program");
                break;
            }
        }
    }
}