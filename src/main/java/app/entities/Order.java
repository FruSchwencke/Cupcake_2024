package app.entities;

import java.time.LocalDate;
import java.util.List;

public class Order {
 private int orderId;
 private User user;

 private List<Cupcake> Orderlist;
 private LocalDate pickuptime;

 private double totalPrice;

 public Order(int orderId, User user, double totalPrice) {
  this.orderId = orderId;
  this.user = user;
  this.totalPrice = totalPrice;
 }

 public Order(int orderId, List<Cupcake> orderlist, double totalPrice) {
  this.orderId = orderId;
  Orderlist = orderlist;
  this.totalPrice = totalPrice;
 }

 public Order(int orderId, double totalPrice) {
  this.orderId = orderId;
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