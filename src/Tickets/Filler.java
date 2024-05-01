package Tickets;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalField;
import java.util.*;
import java.time.ZonedDateTime;


/**
 * A class with which objects of the Ticket type are created using console input and using commands from a script.
 */
public class Filler {
    /**
     * Field, created to ensure the uniqueness of the created id
     */
    public static long idCount = 1;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd;HH:mm:ss");

    /**
     * A method that build the Ticket object from console input using the method {@link Filler#toBuildTicket(String[])}
     *
     * @return new Ticket object
     */
    public static Ticket fill() {
        String[] output = new String[9];
        Scanner scanner = new Scanner(System.in);
        //name
        while (true) {
            System.out.println("Enter name");
            String name = scanner.nextLine();
            if (name == null || name.isEmpty()) {
                System.out.println("Name can't be null");
            } else {
                output[0] = name;
                break;
            }
        }
        //coordinates
        while (true) {
            try {
                System.out.println("Enter x");
                String request = scanner.nextLine();
                Long x = Long.parseLong(request);
                if (x == 0) {
                    System.out.println("x can't be 0");
                } else {
                    output[1] = request;
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Coordinates must be numbers");
            }
        }
        while (true) {
            try {
                System.out.println("Enter y");
                String request = scanner.nextLine();
                Long.parseLong(request);
                output[2] = request;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Coordinates must be numbers");
            }
        }
        //price
        while (true) {
            try {
                System.out.println("Enter price");
                String price = scanner.nextLine();
                if (Long.parseLong(price) <= 0) {
                    System.out.println("Price must be more than 0");
                } else {
                    output[3] = price;
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Price must be a number");
            }
        }
        //refundable
        while (true) {
            System.out.println("Enter whether the ticket is refundable (true/false)");
            String employeesCount = scanner.nextLine();
            if (employeesCount.equalsIgnoreCase("true") || employeesCount.equalsIgnoreCase("false")) {
                boolean refund = Boolean.parseBoolean(employeesCount);
                output[4] = Boolean.toString(refund);
                break;
            } else {
                System.out.println("You entered an incorrect value");
            }
        }

        boolean check = true;
        //type
        while (check) {
            System.out.println("Choose type of ticket: ");
            System.out.println("1.USUAL");
            System.out.println("2.BUDGETARY");
            System.out.println("3.CHEAP");
            String typeName = scanner.nextLine();
            for (TicketType type : TicketType.values()) {
                if (typeName.equalsIgnoreCase(type.name)) {
                    String Ttype = typeName.toUpperCase();
                    output[5] = Ttype;
                    check = false;
                    break;
                }
            }
            if (check) {
                System.out.println("You entered the wrong type name");
            }
        }

        //event name
        while (true) {
            System.out.println("Enter event name");
            String name = scanner.nextLine();
            if (name == null || name.isEmpty()) {
                System.out.println("Name can't be null");
            } else {
                output[6] = name;
                break;
            }
        }
        //event date
        while (true) {
            System.out.println("Enter the date and time in the format yyyy-MM-dd;HH:mm:ss");
            String request = scanner.nextLine();
            try {
                output[7] = LocalDateTime.parse(request, formatter).format(formatter);
                break;
            } catch (DateTimeParseException e) {
                if ((request.equals("\n"))) {
                    System.out.println("Blank date entered");
                    output[7] = "";
                    break;
                }
                System.out.println("Invalid date and time format. Please re-enter.");
            }
        }

        check = true;
        //event type
        while (check) {
            System.out.println("Enter event type");
            System.out.println("1. E_SPORTS");
            System.out.println("2. BASEBALL");
            System.out.println("3. BASKETBALL");
            String evType = scanner.nextLine();
            for (EventType type : EventType.values()) {
                if (evType.equalsIgnoreCase(type.name)) {
                    String finalEvType = evType.toUpperCase();
                    output[8] = finalEvType;
                    check = false;
                    break;
                }
            }
            if (check) {
                System.out.println("You entered the wrong type name");
            }
        }
        return toBuildTicket(output);
    }

    public static Ticket toBuildTicketWithAllData(String[] commands) {
        try {
            Ticket ticket = new Ticket();
            ticket.setId(Long.parseLong(commands[0]));
            ticket.setName(commands[1]);
            ticket.setCoordinates(new Coordinates(Long.parseLong(commands[2]), Long.parseLong(commands[3])));
            LocalDateTime localDateTime = LocalDateTime.parse(commands[4], formatter);
            ZonedDateTime date = localDateTime.atZone(ZoneId.systemDefault());
            ticket.setCreationDate(date);
            ticket.setPrice(Double.parseDouble(commands[5]));
            ticket.setRefundable(Boolean.parseBoolean(commands[6]));
            ticket.setType(TicketType.valueOf(commands[7].toUpperCase()));
            ticket.setEvent(new Event());
            ticket.getEvent().setName(commands[8]);
            ticket.getEvent().setId(Integer.parseInt(commands[9]));
            ticket.getEvent().setDate(LocalDateTime.parse(commands[10], formatter));
            ticket.getEvent().setEventType(EventType.valueOf(commands[11].toUpperCase()));
            idCount++;
            return ticket;
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return new Ticket();
        }
    }

    public static Ticket toBuildTicket(String[] commands) {
        try {
            Ticket ticket = new Ticket();
            Random random = new Random();
            ticket.setId((random.nextLong(100000)));
            ticket.setName(commands[0]);
            ticket.setCoordinates(new Coordinates(Long.parseLong(commands[1]), Long.parseLong(commands[2])));
            LocalDateTime localDateTime = LocalDateTime.now();
            ZonedDateTime date = localDateTime.atZone(ZoneId.systemDefault());
            ticket.setCreationDate(date);
            ticket.setPrice(Double.parseDouble(commands[3]));
            ticket.setRefundable(Boolean.parseBoolean(commands[4]));
            ticket.setType(TicketType.valueOf(commands[5].toUpperCase()));
            ticket.setEvent(new Event());
            ticket.getEvent().setName(commands[6]);
            ticket.getEvent().setId(random.nextInt(100000));
            ticket.getEvent().setDate(LocalDateTime.parse(commands[7], formatter));
            ticket.getEvent().setEventType(EventType.valueOf(commands[8].toUpperCase()));
            idCount++;
            return ticket;
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return new Ticket();
        }
    }
}

