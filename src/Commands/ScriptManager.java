package Commands;

import Tickets.TicketCollection;

import java.io.*;
import java.util.Arrays;
import java.util.Map;

/**
 * This class works with the script
 */
public class ScriptManager {
    private TicketCollection collection;
    /**
     * Field that links to a script file
     */
    private File script;
    /**
     * A field that refers to a HashMap with keys - command names and the possibility of calling them
     */
    private Map<String, Command> commands;
    /**
     * A field that refers to an object with implementations of all commands
     */
    private CommandManager commandManager;

    public ScriptManager(File script, Map<String, Command> commands, CommandManager commandManager, TicketCollection collection) {
        this.script = script;
        this.commands = commands;
        this.commandManager = commandManager;
        this.collection = collection;
    }

    /**
     * A method that calls the commands specified in the array received from the method {@link ScriptManager#srciptToTokens()}
     */
    public void executeScript() {
        String[] tokens = srciptToTokens();
        for (int i = 0; i < tokens.length; i++) {
            try {
                Command command = commands.get(tokens[i]);
                if (tokens[i].equalsIgnoreCase("insert") || tokens[i].equalsIgnoreCase("filter_by_price") ||
                        tokens[i].equalsIgnoreCase("update") || tokens[i].equalsIgnoreCase("remove_by_id") ||
                        tokens[i].equalsIgnoreCase("execute_script") || tokens[i].equalsIgnoreCase("remove_greater_key") ||
                        tokens[i].equalsIgnoreCase("replace_if_greater")) {
                    commandManager.setCompositeCommand(Arrays.copyOfRange(tokens, i + 1, tokens.length));
                }
                boolean isTokenCommand = commands.containsKey(tokens[i]);
                if (!isTokenCommand) {
                    continue;
                }
                command.execute();
                if(commandManager.getCommandsHistory().size()==6) {
                    commandManager.getCommandsHistory().removeFirst();
                    commandManager.getCommandsHistory().addLast(tokens[i]);
                }
                else {commandManager.getCommandsHistory().addLast(tokens[i]);}
                collection.updateData();
                collection.updateNumeration();
            } catch (NullPointerException e) {
                System.err.println("There is an mistake in the script");
            }
        }
    }

    /**
     * A method that reads a script file and converts it to a char array and return result of the {@link ScriptManager#getStrings(char[])}
     *
     * @return Command array
     */
    private String[] srciptToTokens() {
        char[] inputChar = new char[4096];
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(script))) {
            reader.read(inputChar);
            reader.close();
            return getStrings(inputChar);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return new String[0];
        } catch (IOException e) {
            System.out.println("IO exception");
            return new String[0];
        }
    }

    /**
     * Private method that turns an array of characters into an array of strings with individual commands
     *
     * @param inputChar An array of characters read from a file with a script method{@link ScriptManager#srciptToTokens()}
     * @return Command array
     */
    private static String[] getStrings(char[] inputChar) {
        StringBuilder charBuilder = new StringBuilder();
        for (int i = 0; i < inputChar.length; i++) {
            if (inputChar[i] == '\0') {
                break;
            }
            if (inputChar[i] == '\r') {
                continue;
            }
            if (inputChar[i] == '\n') {
                charBuilder.append(" ");
                continue;
            }
            charBuilder.append(inputChar[i]);
        }
        String result = String.valueOf(charBuilder);
        String[] tokens = result.split(" ");
        return tokens;
    }
}
