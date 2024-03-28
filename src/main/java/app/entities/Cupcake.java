package app.entities;

public class Cupcake {

    private Bottom bottom;
    private Top top;
    private int quantity;
    private double price;

    public double getPrice() {
        return (this.bottom.getPrice() + this.top.getPrice()) * quantity;
    }
}
