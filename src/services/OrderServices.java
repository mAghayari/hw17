package services;

import dao.OrderDao;
import dao.ProductDao;
import model.*;
import org.apache.log4j.Logger;
import view.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServices {
    private static final Logger LOGGER = Logger.getLogger(OrderServices.class);

    public Order cancelOrder(Order order) {
        ProductServices productServices = new ProductServices();
        List<OrderItem> orderItems = order.getOrderItems();

        productServices.getAllSoledItemsBack(orderItems);
        order.setTotalCost(-order.getTotalCost());
        return order;
    }

    public void addingOrder(Order order) {
        OrderDao orderDao = new OrderDao();
        orderDao.addAnOrder(order);
    }

    public List<OrderDto> getOrdersOfAPeriod(int customerId, Date startDate, Date endDate) {
        OrderDao orderDao = new OrderDao();
        return orderDao.findOrders(customerId, startDate, endDate);
    }

    private void addNewOrderItem(Order order, List<Product> products, int id, int count) {
        List<OrderItem> orderItems = order.getOrderItems();
        OrderItem orderItem = new OrderItem();
        orderItem.setCount(count);
        orderItem.setOrder(order);
        orderItem.setProduct(products.get(id - 1));
        orderItems.add(orderItem);
    }

    private void updateAnOrderItem(int id, int count, List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == id) {
                int index = orderItems.indexOf(orderItem);
                count += orderItems.get(index).getCount();
                orderItems.get(index).setCount(count);
                break;
            }
        }
    }

    public void deleteAnOrderItem(Order order, int id) {
        ProductServices productServices = new ProductServices();
        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == id) {
                productServices.getASoledItemBack(orderItem);
                orderItems.remove(orderItem);
                order.setOrderItems(orderItems);
                order.setTotalCost(-(orderItem.getProduct().getPrice() * orderItem.getCount()));
                System.out.println("✔ Item deleted");
                return;
            }
        }
        System.out.println("❌ No such ID was found in your order" + "\n");
    }

    public void addProductToOrder(Order order, ProductDao productDao, List<Product> products, String category) {
        if (order.getOrderItems().size() >= 5) {
            System.out.println("❌ There are 5 products in your order,\n" +
                    "Finalize them or remove some then continue shopping.");
        } else {
            ArrayList<Product> categoryProducts = ProductView.getProperProducts(category, products);
            ProductView.printProducts(categoryProducts);
            System.out.println("1-Choose a product\n2-Category Menu");
            int item = GetUserInputs.getInBoundDigitalInput(2);
            if (item == 1) {
                System.out.println("Enter a product Id:");
                int id = ProductView.getProperProductID(categoryProducts);
                int stock = products.get(id - 1).getStock();
                if (stock != 0) {
                    System.out.println("How much do you wanna by?(must be in stock's bound)");
                    int count = GetUserInputs.getInBoundDigitalInput(stock);

                    List<OrderItem> orderItems = order.getOrderItems();
                    boolean isInList = orderItems.stream().anyMatch(orderItem -> orderItem.getProduct().getId() == id);
                    if (isInList) {
                        updateAnOrderItem(id, count, orderItems);
                    } else {
                        addNewOrderItem(order, products, id, count);
                        order.setTotalCost(products.get(id - 1).getPrice() * count);
                    }
                    products.get(id - 1).setStock(stock - count);
                    productDao.updateProduct(products.get(id - 1));
                    System.out.println("✔ This product added to your order\n");
                } else
                    System.out.println("❌ Sorry... we're ran out of this product\n");
            }
        }
    }

    public Order deleteOperation(Order order, List<Product> products) {
        OrderView orderView = new OrderView();
        List<OrderItem> orderItems = order.getOrderItems();

        System.out.println("1-Delete An Item\n2-Empty Order\n3-Main Menu");
        int editItem = GetUserInputs.getInBoundDigitalInput(3);

        if (editItem == 1) {
            orderView.printOrderItems(orderItems);
            System.out.println("Enter id of a product to delete it");
            int id = GetUserInputs.getInBoundDigitalInput(products.size());
            deleteAnOrderItem(order, id);
        } else if (editItem == 2) {
            order = cancelOrder(order);
            order.setOrderItems(new ArrayList<>());
        }
        return order;
    }

    public void finalizeOrder(Order order) {
        OperationLogServices operationLogServices = new OperationLogServices();
        GetUserInputs getUserInputs = new GetUserInputs();

        if (order.getOrderItems().isEmpty())
            System.out.println("❌ Your order is empty\n");
        else {
            order.setDate();
            System.out.println("Where do you wanna receive your purchase:\n" +
                    "1-My Account Address\n2-Another Address");
            int addressItem = GetUserInputs.getInBoundDigitalInput(2);
            if (addressItem == 1)
                order.setAddress(order.getCustomer().getAddress());
            else
                order.setAddress(getUserInputs.getAddress());
            addingOrder(order);
            order.setOrderItems(new ArrayList<>());
            System.out.println("✔ Thank you for your shopping");
            OperationLog operationLog =
                    operationLogServices.getOperationLog("customer " + order.getCustomer().getUserName(), "shop");
            LOGGER.info(operationLog.toString());
        }
    }
}