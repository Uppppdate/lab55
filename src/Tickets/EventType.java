package Tickets;

public enum EventType {
    E_SPORTS("E_SPORTS"),
    BASEBALL("BASEBALL"),
    BASKETBALL("BASKETBALL");
    public String name;

    EventType(String name) {
        this.name = name;
    }
}
