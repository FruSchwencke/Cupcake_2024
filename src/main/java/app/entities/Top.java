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

    public Top(int topId, String flavour) {
        this.topId = topId;
        this.flavour = flavour;
    }

    public int getTopId() {
        return topId;
    }

    public String getTopIdAsString() {
        return String.valueOf(topId);
    }

    public String getFlavour() {
        return flavour;
    }

    public double getPrice() {
        return price;
    }
}
