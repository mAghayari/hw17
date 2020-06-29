package services;

import dao.OrderDao;
import model.Order;
import model.OrderDto;
import model.OrderItem;

import java.util.Date;
import java.util.List;

public class OrderServices {

    public Order cancelOrder(Order order) {
        ProductServices productServices = new ProductServices();
        List<OrderItem> orderItems = order.getOrderItems();

        productServices.getAllSoledItemsBack(orderItems);
        return order;
    }

    public double getTotalCost(Order order) {
        order.setTotalCost(order.getOrderItems());
        return order.getTotalCost();
    }

    public void addingOrder(Order order) {
        OrderDao orderDao = new OrderDao();
        orderDao.addAnOrder(order);
    }

    public List<OrderDto> getOrdersOfAPeriod(int customerId, Date startDate, Date endDate) {
        OrderDao orderDao = new OrderDao();
        return orderDao.findOrders(customerId, startDate, endDate);
    }
}