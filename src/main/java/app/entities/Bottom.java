package app.entities;

public class Bottom {

    private int bottom_id;
    private String name;

    private double price;

    public Bottom(int bottom_id, String name, double price) {
        this.bottom_id = bottom_id;
        this.name = name;
        this.price = price;
    }

    public int getBottom_id() {
        return bottom_id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
