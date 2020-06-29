package view;

import dao.ProductDao;
import model.Order;
import model.OrderItem;
import model.Product;
import services.ProductServices;

import java.util.ArrayList;
import java.util.List;

public class OrderItemView {

    public void deleteAnCartItem(Order order, List<Product> products) {
        ProductServices productServices = new ProductServices();
        List<OrderItem> orderItems = order.getOrderItems();

        printCartItems(orderItems);

        System.out.println("Enter id of a product to delete it");
        int id = GetUserInputs.getInBoundDigitalInput(products.size());

        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == id) {
                productServices.getASoledItemBack(orderItem);
                orderItems.remove(orderItem);
                order.setCartItems(orderItems);
                order.setTotalCost(orderItems);

                System.out.println("✔ Item deleted");
                return;
            }
        }
        System.out.println("❌ No such ID was found in your cart" +
                "\n");
    }

    public void printCartItems(List<OrderItem> orderItems) {
        System.out.println("Your ordered:\n");

        for (OrderItem orderItem : orderItems) {
            System.out.println(orderItem.orderItemForSavingOrder());
        }
    }

    public void addProductToCart(Order order, ProductDao productDao, List<Product> products, String category) {
        if (order.getOrderItems().size() >= 5) {
            System.out.println("❌ There are 5 products in your cart,\n" +
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
                    boolean isInList = orderItems.stream().anyMatch(cartItem -> cartItem.getProduct().getId() == id);
                    if (isInList) {
                        updateAnCartItem(id, count, orderItems);
                    } else {
                        addNewCartItem(order, products, id, count, orderItems);
                    }
                    products.get(id - 1).setStock(stock - count);
                    productDao.updateProduct(products.get(id - 1));
                    System.out.println("✔ This product added to your cart\n");
                } else
                    System.out.println("❌ Sorry... we're ran out of this product\n");
            }
        }
    }

    private void addNewCartItem(Order order, List<Product> products, int id, int count, List<OrderItem> orderItems) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCount(count);
        orderItem.setOrder(order);
        orderItem.setProduct(products.get(id - 1));
        orderItems.add(orderItem);
    }

    private void updateAnCartItem(int id, int count, List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == id) {
                int index = orderItems.indexOf(orderItem);
                count += orderItems.get(index).getCount();
                orderItems.get(index).setCount(count);
                break;
            }
        }
    }
}