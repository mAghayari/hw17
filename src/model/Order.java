package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<OrderItem> orderItems = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private User customer;
    private Address address;
    private double totalCost = 0;
    private Timestamp orderDate;

    public int getId() {
        return id;
    }

    public void setId(int orderID) {
        this.id = orderID;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User user) {
        this.customer = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double cost) {
        totalCost += cost;
        // orderedItems.forEach(orderItem -> totalCost += orderItem.getCount() * orderItem.getProduct().getPrice());
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderedItems) {
        this.orderItems = orderedItems;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setDate() {
        java.util.Date now = new java.util.Date();
        orderDate = new Timestamp(now.getTime());
    }

    @Override
    public String toString() {
        String orderString = "order: " + id +
                "\ntotalCost: " + totalCost +
                "\norderDate: " + orderDate +
                "\norderItems: ";
        for (OrderItem orderItem : orderItems) {
            orderString += orderItem.getId() + " ";
        }
        orderString += "\n*********************************\n";
        return orderString;
    }
}