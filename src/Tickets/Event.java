package Tickets;

import java.time.LocalDateTime;

public class Event {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.LocalDateTime date; //Поле может быть null
    private EventType eventType; //Поле может быть null

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        String out;
        try {
            out = "Event id: " + id +
                    ", event name: " + name +
                    ", date: " + date.format(Filler.formatter) +
                    ", event type: " + eventType + "\n";
        } catch (NullPointerException e) {
            out = "Event id: " + id +
                    ", event name: " + name +
                    ", date: null" +
                    ", event type: " + eventType + "\n";
        }
        return out;
    }
}