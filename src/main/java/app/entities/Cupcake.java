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

    public Cupcake(Bottom bottom, Top top, int quantity) {
        this.bottom = bottom;
        this.top = top;
        this.quantity = quantity;
    }



    public Bottom getBottom() {
        return bottom;
    }

    public Top getTop() {
        return top;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return (this.bottom.getPrice() + this.top.getPrice()) * quantity;
    }
}
