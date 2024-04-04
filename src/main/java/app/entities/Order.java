package app.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
 private int orderId;
 private User user;

 private List<Cupcake> Orderlist;
 private LocalDate pickuptime;

 private double totalPrice;

 public Order(int orderId, LocalDate pickuptime, double totalPrice) {
  this.orderId = orderId;
  this.pickuptime = pickuptime;
  this.totalPrice = totalPrice;
 }

 public int getOrderId() {
  return orderId;
 }

public double getTotalPrice() {
  return totalPrice;
}

 public List<Cupcake> getOrderlist() {
  return Orderlist;
 }

 public LocalDate getPickuptime() {
  return pickuptime;
 }


}