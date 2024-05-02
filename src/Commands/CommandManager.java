package Commands;

import Commands.ConcreteCommands.InsertCommand;
import Commands.ConcreteCommands.SaveToCSVCommand;
import Tickets.Filler;
import Tickets.*;
import CSV.CSVCommands;

import java.io.File;
import java.util.*;

/**
 * A class that stores implementations of all program commands.
 * It can work with commands both from the console and from a script,
 * responsible for this {@link CommandManager#isScriptWorking}
 */
public class CommandManager {
    /**
     * A field that refers to an object whose fields contain the collection with which the program works.
     */
    private TicketCollection collection;
    private Set<String> scriptHistory = new HashSet<>();
    /**
     * A field that refers to an object whose methods work with the xml file.
     */
    private CSVCommands xmlCommands;
    /**
     * A field that refers to a HashMap with keys - command names and the possibility of calling them
     */
    private Map<String, Command> commands;
    /**
     * An array of strings into which an array of commands from the script is passed{@link ScriptManager#executeScript()}
     */
    private LinkedList<String> commandsHistory;
    private String[] compositeCommand = new String[9];
    /**
     * A field that can be used to change the implementation of commands for working with a script
     */
    private boolean isScriptWorking = false;
    /**
     * String array to which commands are sent from the console
     */
    private String[] tokens;

    public CommandManager(TicketCollection collection, CSVCommands xmlCommands, Map<String, Command> commands) {
        this.collection = collection;
        this.xmlCommands = xmlCommands;
        this.commands = commands;
        commandsHistory = new LinkedList<>();
    }

    public void setTokens(String[] tokens) {
        this.tokens = tokens;
    }

    /**
     * The method resets the variable {@link CommandManager#compositeCommand}
     */
    public void clearCompositeCommand() {
        compositeCommand = new String[9];
    }

    public void setCompositeCommand(String[] compositeCommand) {
        this.compositeCommand = compositeCommand;
    }

    /**
     * Command to call up descriptions of all commands {@link Commands.ConcreteCommands.HelpCommand}
     */
    public void help() {
        for (Command com : commands.values()) {
            System.out.println(com.description());
        }
    }

    /**
     * Command that saves the current collection instance to a file {@link SaveToCSVCommand}
     *
     * @see CSVCommands
     */
    public void save() {
        if (xmlCommands.toSaveToCSV() < 0) {
            System.out.println("Specified file is not exist");
            return;
        }
        System.out.println("Saved successfully");
    }

    /**
     * Command to exit the application {@link Commands.ConcreteCommands.ExitCommand}
     */
    public void exit() {
        System.out.println("Session ended");
        System.exit(0);
    }

    /**
     * Command to clear a collection {@link Commands.ConcreteCommands.ClearCollectionCommand}
     */
    public void clear() {
        collection.getCollection().clear();
        collection.updateData();
        System.out.println("Collection cleared");
    }

    /**
     * A command that prints to the console all objects in a collection and their fields {@link Commands.ConcreteCommands.ShowCollectionCommand}
     */
    public void show() {
        System.out.println(collection);
    }

    /**
     * A command that adds a new object to the collection, created with {@link Filler} class methods
     * {@link InsertCommand}
     */
    public void insert() {
        try {
            long key = 0;
            if (isScriptWorking) {
                key = Long.parseLong(compositeCommand[0]);
                String[] data = Arrays.copyOfRange(compositeCommand, 1, compositeCommand.length);
                Ticket ticket = Filler.toBuildTicket(data);
                collection.getCollection().put(key, ticket);
                clearCompositeCommand();
            } else {
                key = Long.parseLong(tokens[1]);
                Ticket ticket = Filler.fill();
                collection.getCollection().put(key, ticket);
            }
            collection.updateData();
            System.out.println("Ticket successfully added");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please, enter the key in the command");
        } catch (NumberFormatException e) {
            System.out.println("Key must be integer");
        } catch (ArithmeticException e) {
            System.out.println("Key cannot be negative");
        }
    }

    /**
     * Command showing information about the current state of a collection {@link Commands.ConcreteCommands.InfoCommand}
     *
     * @see TicketCollection
     */
    public void info() {
        System.out.print("Collection information:\n");
        System.out.println(collection.toShowInfo());
    }

    public void minByPrice() {
        System.out.println(collection.getCollection().entrySet().stream().min(new Comparator<Map.Entry<Long, Ticket>>() {
            @Override
            public int compare(Map.Entry<Long, Ticket> o1, Map.Entry<Long, Ticket> o2) {
                return Double.compare(o1.getValue().getPrice(), o2.getValue().getPrice());
            }
        }).get());
    }

    public void filterByPrice() {
        long price;
        try {
            if (isScriptWorking) {
                price = Long.parseLong(compositeCommand[0]);
                clearCompositeCommand();
            } else {
                price = Long.parseLong(tokens[1]);
            }
            List<Map.Entry<Long, Ticket>> list = collection.getCollection().entrySet().stream().filter(t -> t.getValue().getPrice() == price).toList();
            if (list.isEmpty()) {
                System.out.println("There are no items in the collection with this price");
                return;
            }
            for (Map.Entry<Long, Ticket> entry : list) {
                System.out.println(entry.getKey() + ") " + entry.getValue().toString());
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please, enter the price in the command");
        } catch (NumberFormatException e) {
            System.out.println("Price must be integer");
        } catch (ArithmeticException e) {
            System.out.println("Price cannot be negative");
        }
    }

    public void averagePrice() {
        int size = collection.getCollection().values().size();
        double priceSum = 0;
        for (Ticket ticket : collection.getCollection().values()) {
            priceSum += ticket.getPrice();
        }
        System.out.println("Average price: " + priceSum / size);
    }

    /**
     * A command that allows you to update an object with a given id {@link Commands.ConcreteCommands.UpdateCommand}
     */
    public void update() {
        long id;
        Optional<Map.Entry<Long, Ticket>> entryOptional;
        Ticket ticket;
        try {
            if (isScriptWorking) {
                id = Long.parseLong(compositeCommand[0]);
                compositeCommand = Arrays.copyOfRange(compositeCommand, 1, compositeCommand.length);
            } else {
                id = Long.parseLong(tokens[1]);
            }
            if (id < 0) {
                throw new ArithmeticException();
            }
            entryOptional = collection.getCollection().entrySet().stream().filter(e -> e.getValue().getId() == id).findFirst();
            if (entryOptional.isEmpty()) {
                System.out.println("There is no element in the collection with this key");
                return;
            }
            if (isScriptWorking) {
                ticket = Filler.toBuildTicket(compositeCommand);
                ticket.setId(id);
                entryOptional.get().setValue(ticket);
                clearCompositeCommand();
            } else {
                ticket = Filler.fill();
                ticket.setId(id);
                entryOptional.get().setValue(ticket);
            }
            System.out.println("Organization with ID: " + id + " successfully updated");
        } catch (NumberFormatException e) {
            System.out.println("Key must be integer");
        } catch (ArithmeticException e) {
            System.out.println("Key cannot be negative");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please, enter the key in the command");
        }
    }

    public void removeGreaterKey() {
        long key;
        try {
            if (isScriptWorking) {
                key = Long.parseLong(compositeCommand[0]);
                clearCompositeCommand();
            } else {
                key = Long.parseLong(tokens[1]);
            }
            if (key <= 0) {
                throw new ArithmeticException();
            }
            Optional<Map.Entry<Long, Ticket>> entryOptional = collection.getCollection().entrySet().stream().filter(e -> e.getKey() == key).findFirst();
            if (entryOptional.isEmpty()) {
                System.out.println("There is no element in the collection with this key");
                return;
            }
            int count = 0;
            Set<Map.Entry<Long, Ticket>> ticketEntry = collection.getCollection().entrySet();
            List<Map.Entry<Long, Ticket>> list = ticketEntry.stream().toList();
            for (Map.Entry<Long, Ticket> entry : list) {
                if (entry.getKey() > key) {
                    collection.getCollection().remove(entry.getKey());
                    count++;
                }
            }
            System.out.println(count + " tickets has been removed");
        } catch (NumberFormatException e) {
            System.out.println("Key must be integer");
        } catch (ArithmeticException e) {
            System.out.println("Key cannot be negative and null");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please, enter the key in the command");
        }
    }

    /**
     * A command that creates an object of the {@link ScriptManager} class and starts the script process
     *
     * @see Commands.ConcreteCommands.ExecuteScriptCommand
     */
    public void executeScript() {
        try {
            File script;
            if (isScriptWorking) {
                script = new File(compositeCommand[0]);
            } else {
                script = new File(tokens[1]);
            }
            if (!script.exists()) {
                System.out.println("Specified file is not exist");
                return;
            }
            if (scriptHistory.contains(compositeCommand[0])) {
                System.out.println("This script has already been executed");
                return;
            }
            if (isScriptWorking) {
                scriptHistory.add(compositeCommand[0]);
            }
            else {
                scriptHistory.add(tokens[1]);
            }
            isScriptWorking = true;
            ScriptManager scriptManager = new ScriptManager(script, commands, this);
            System.out.println("Script is executing");
            if (isScriptWorking) {
                clearCompositeCommand();
            }
            scriptManager.executeScript();
            isScriptWorking = false;
            System.out.println("Script executed successfully");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please, enter the file name in the command");
        }
    }

    public void replaceIfGreater() {
        long key;
        try {
            if (isScriptWorking) {
                key = Long.parseLong(compositeCommand[0]);
                clearCompositeCommand();
            } else {
                key = Long.parseLong(tokens[1]);
            }
            if (key <= 0) {
                throw new ArithmeticException();
            }
            Optional<Map.Entry<Long, Ticket>> entryOptional = collection.getCollection().entrySet().stream().filter(e -> e.getKey() == key).findFirst();
            if (entryOptional.isEmpty()) {
                System.out.println("There is no element in the collection with this key");
                return;
            }
            if (isScriptWorking) {
                Ticket ticket = Filler.toBuildTicket(compositeCommand);
                if (ticket.compareTo(entryOptional.get().getValue()) <= 0) throw new NoSuchElementException();
                collection.getCollection().put(key, ticket);
                clearCompositeCommand();
            } else {
                Ticket ticket = Filler.fill();
                if (ticket.compareTo(entryOptional.get().getValue()) <= 0) throw new NoSuchElementException();
                collection.getCollection().put(key, ticket);
            }
            collection.updateData();
            System.out.println("Ticket successfully added");
        } catch (NumberFormatException e) {
            System.out.println("Key must be integer");
        } catch (ArithmeticException e) {
            System.out.println("Key cannot be negative and null");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please, enter the key in the command");
        } catch (NoSuchElementException e) {
            System.out.println("The ticket with the given key is greater than the new ticket entered\n" +
                    "No new element was added");
        }
    }

    /**
     * A command that allows you to delete an object by its key {@link Commands.ConcreteCommands.RemoveByKeyCommand}
     */
    public void removeByKey() {
        try {
            long key;
            if (isScriptWorking) {
                key = Long.parseLong(compositeCommand[0]);
                clearCompositeCommand();
            } else {
                key = Long.parseLong(tokens[1]);
            }
            if (key < 0) {
                throw new ArithmeticException();
            }
            if (collection.getCollection().remove(key) == null) {
                System.out.println("There is no ticket with this key in the collection");
            } else {
                System.out.println("Organization with key: " + key + " was successfully removed");
            }
        } catch (NumberFormatException e) {
            System.out.println("Id must be integer");
        } catch (ArithmeticException e) {
            System.out.println("Id cannot be negative");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please, enter the id in the command");
        }
    }

    public void history() {
        System.out.println(commandsHistory);
    }

    public LinkedList<String> getCommandsHistory() {
        return commandsHistory;
    }
}





