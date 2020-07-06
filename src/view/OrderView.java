package view;

import model.*;
import org.apache.log4j.Logger;
import services.OperationLogServices;
import services.OrderServices;

import java.util.*;

public class OrderView {

    public void printOrder(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        if (orderItems.isEmpty())
            System.out.println("❌ Your order is empty\n");
        else {
            Comparator<OrderItem> comparator = Comparator.comparing(OrderItem::getProduct);
            orderItems.sort(comparator);
            printOrderItems(order.getOrderItems());
            System.out.println("Total Cost: " + order.getTotalCost() + "\n");
        }
    }

    public void printOrderItems(List<OrderItem> orderItems) {
        System.out.println("Your ordered:\n");
        for (OrderItem orderItem : orderItems) {
            System.out.println(orderItem.toString());
        }
    }
}