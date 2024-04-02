package app.entities;

public class Bottom {

    private int bottomId;
    private String flavour;

    private double price;

    public Bottom(int bottomId, String name, double price) {
        this.bottomId = bottomId;
        this.flavour = name;
        this.price = price;
    }

    public int getBottomId() {
        return bottomId;
    }
    public String getBottomIdAsString() {
        return String.valueOf(bottomId);
    }
    public String getFlavour() {
        return flavour;
    }

    public double getPrice() {
        return price;
    }
}
