package app.entities;

public class Cupcake {

    private Bottom bottom;
    private Top top;
    private int quantity;
    private double price;

    public Cupcake(Bottom bottom, Top top, int quantity, double price) {
        this.bottom = bottom;
        this.top = top;
        this.quantity = quantity;
        this.price = price;
    }


    public double getPrice() {
        return (this.bottom.getPrice() + this.top.getPrice()) * quantity;
    }
}
