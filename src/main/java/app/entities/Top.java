package app.entities;

public class Top {

    private int topId;
    private String flavour;
    private double price;

    public Top(int top_id, String name, double price) {
        this.topId = top_id;
        this.flavour = name;
        this.price = price;
    }

    public int getTopId() {
        return topId;
    }

    public String getFlavour() {
        return flavour;
    }

    public double getPrice() {
        return price;
    }
}
