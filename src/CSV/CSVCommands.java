package CSV;

import Tickets.*;

import java.io.*;
import java.util.*;

/**
 * The class that is used to work with xml file,
 * including initial parsing and file rewriting while the program is running.
 */
public class CSVCommands {
    /**
     * Field for accessing an object containing in fields a collection with which the program works
     */
    TicketCollection collection;
    /**
     * Static field that specifies the path to the file that is set in this method{@link CSVCommands#toParse()}
     */
    private static String PATH = "dataBase.csv";

    public CSVCommands(TicketCollection collection) {
        this.collection = collection;
    }

    /**
     * This method update csv file with working collection
     * by converting elements of collection to Element objects
     * and building document with this Element's
     */
    public int toSaveToCSV() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH))) {
            Iterator<Map.Entry<Long, Ticket>> ticketIterator = collection.getCollection().entrySet().iterator();
            while (ticketIterator.hasNext()) {
                Map.Entry<Long, Ticket> entry = ticketIterator.next();
                bufferedWriter.write(toStringFromTicket(entry));
            }
            return 1;
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
            return 0;
        } catch (IOException e) {
            System.out.println("IO exception");
            return 0;
        }
    }

    private String toStringFromTicket(Map.Entry<Long, Ticket> entry) {
        Ticket ticket = entry.getValue();
        Long key = entry.getKey();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(key).append(",");
        stringBuilder.append(ticket.getId()).append(",");
        stringBuilder.append(ticket.getName()).append(",");
        stringBuilder.append(ticket.getCoordinates().getX()).append(",");
        stringBuilder.append(ticket.getCoordinates().getY()).append(",");
        stringBuilder.append(ticket.getCreationDate().format(Filler.formatter)).append(",");
        stringBuilder.append(ticket.getPrice()).append(",");
        stringBuilder.append(ticket.isRefundable()).append(",");
        stringBuilder.append(ticket.getType().name).append(",");
        stringBuilder.append(ticket.getEvent().getName()).append(",");
        stringBuilder.append(ticket.getEvent().getId()).append(",");
        stringBuilder.append(ticket.getEvent().getDate().format(Filler.formatter)).append(",");
        stringBuilder.append(ticket.getEvent().getEventType().name).append("\n");
        return new String(stringBuilder);
    }

    /**
     * This static method pars xml file and return LinkedList
     *
     * @return LinkedList that was parsed from XML file
     */
    public static Hashtable<Long, Ticket> toParse() {
        try (Scanner scanner = new Scanner(new File(PATH))) {
            Hashtable<Long, Ticket> hashtable = new Hashtable<>();
            long idCount = 1;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String data[] = line.split(",");
                String tokens[] = Arrays.copyOfRange(data, 1, data.length);
                Long key = Long.parseLong(data[0]);
                Ticket ticket = Filler.toBuildTicketWithAllData(tokens);
                hashtable.put(key, ticket);
                idCount++;
            }
            return hashtable;
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
            return new Hashtable<>();
        }
    }
}
