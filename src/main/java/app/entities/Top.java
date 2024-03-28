package app.entities;

public class Top {

    private int top_id;
    private String name;
    private double price;

    public Top(int top_id, String name, double price) {
        this.top_id = top_id;
        this.name = name;
        this.price = price;
    }

    public int getTop_id() {
        return top_id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
