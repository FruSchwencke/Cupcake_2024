package app.entities;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
 private int orderId;
 private int userID;

 private List<Cupcake> Orderlist;
 private LocalDateTime pickuptime; // HVORDAN VÆLGES TID?
 private boolean iscompleted;

 private double totalPrice;

 

 public int getOrderId() {
  return orderId;
 }

 public int getUserId() {

  return userID;

 }

 public List<Cupcake> getOrderlist() {
  return Orderlist;
 }

 public LocalDateTime getPickuptime() {
  return pickuptime;
 }

 public boolean isIscompleted() {
  return iscompleted;
 }
}
