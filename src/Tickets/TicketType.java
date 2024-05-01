package Tickets;

public enum TicketType {
    USUAL("USUAL"),
    BUDGETARY("BUDGETARY"),
    CHEAP("CHEAP");
    public String name;

    TicketType(String name) {
        this.name = name;
    }

}
