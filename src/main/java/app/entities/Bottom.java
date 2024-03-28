package app.entities;

public class Bottom {

    private int bottomId;
    private String flavour;

    private double price;

    public Bottom(int bottom_id, String name, double price) {
        this.bottomId = bottom_id;
        this.flavour = name;
        this.price = price;
    }

    public int getBottomId() {
        return bottomId;
    }

    public String getFlavour() {
        return flavour;
    }

    public double getPrice() {
        return price;
    }
}
