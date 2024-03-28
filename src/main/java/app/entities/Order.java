package app.entities;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
 private int order_id;
 private int user_id;

 private List<Cupcake> Orderlist;
 private LocalDateTime pickuptime; // HVORDAN VÆLGES TID?
 private boolean iscompleted;

 private double totalPrice;

 public int getOrder_id() {
  return order_id;
 }

 public int getUser_id() {
  return user_id;
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
