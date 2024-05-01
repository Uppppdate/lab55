package Tickets;

public class Coordinates {
    private Long x; //Поле не может быть null
    private long y;


    public Coordinates(Long x, long y) {
        this.x = x;
        this.y = y;
    }


    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x=" + x + " y=" + y;
    }
}