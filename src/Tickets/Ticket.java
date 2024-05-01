package Tickets;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Ticket implements Comparable<Ticket> {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double price; //Значение поля должно быть больше 0
    private boolean refundable;
    private TicketType type; //Поле не может быть null
    private Event event; //Поле может быть null

    public Ticket() {
    }

    @Override
    public int compareTo(Ticket o) {
        return Double.compare(price, o.price);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return  "Id: " + id +
                ", name: " + name +
                ", coordinates: " + coordinates +
                ", \ncreationDate: " + creationDate.format(Filler.formatter) +
                ", price: " + price +
                ", refundable: " + refundable +
                ", type: " + type +
                ",\n" + event;
    }
}