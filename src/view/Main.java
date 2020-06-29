package view;

import dao.ProductDao;
import model.Admin;
import model.Order;
import model.Customer;
import model.Product;
import services.OrderServices;

import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        OrderItemView orderItemView = new OrderItemView();
        OrderServices orderServices = new OrderServices();
        CustomerView customerView = new CustomerView();
        AdminView adminView = new AdminView();

        while (true) {
            Customer customer;
            Order order = new Order();
            Admin admin = new Admin();
            System.out.println("1)SignIn\n2)SignUp");
            int enterItem = GetUserInputs.getInBoundDigitalInput(2);
            if (enterItem == 1) {
                System.out.println("1-Admin SignIn \n2-User SignIn");
                int signInItem = GetUserInputs.getInBoundDigitalInput(2);
                if (signInItem == 1) {
                    admin = adminView.adminSignIn();
                } else {
                    customer = customerView.customerSignIn();
                    order.setCustomer(customer);
                }
            } else {
                System.out.println("1-Admin SignUp\n2-User SignUp");
                int signUpItem = GetUserInputs.getInBoundDigitalInput(2);
                if (signUpItem == 1) {
                    admin = adminView.adminSignUp();
                } else {
                    customer = customerView.customerSignUp();
                    order.setCustomer(customer);
                }
            }
            if (!Objects.equals(order.getCustomer(), null)) {
                ProductDao productDao = new ProductDao();
                List<Product> products;
                mainMenu:
                while (true) {
                    System.out.println("Main Menu:");
                    System.out.println("1-Products category's List\n2-Delete Cart Items\n3-Print Cart\n4-Finalize Cart\n5-SignOut\n6-Exit\nChoose an item:");
                    int mainMenuItem = GetUserInputs.getInBoundDigitalInput(6);
                    subMenu:
                    while (true) {
                        products = productDao.getAllProducts();
                        switch (mainMenuItem) {
                            case 1:
                                System.out.println("1-Electronics\n2-Readings\n3-Shoes\n4-Main Menu");
                                int categoryItem = GetUserInputs.getInBoundDigitalInput(4);
                                switch (categoryItem) {
                                    case 1:
                                        orderItemView.addProductToCart(order, productDao, products, "ELECTRONICS");
                                        break;
                                    case 2:
                                        orderItemView.addProductToCart(order, productDao, products, "READINGS");
                                        break;
                                    case 3:
                                        orderItemView.addProductToCart(order, productDao, products, "SHOES");
                                        break;
                                    case 4:
                                        break subMenu;
                                }
                                break;
                            case 2:
                                if (order.getOrderItems().isEmpty())
                                    System.out.println("your Cart is empty\n");
                                else {
                                    OrderView orderView = new OrderView();
                                    order = orderView.deleteOperation(order, products);
                                }
                                break subMenu;
                            case 3:
                                OrderView.printCart(orderServices, order);
                                break subMenu;
                            case 4:
                                if (order.getOrderItems().isEmpty())
                                    System.out.println("your Cart is empty\n");
                                else {
                                    OrderView orderView = new OrderView();
                                    orderView.finalizeCart(order);
                                }
                                break subMenu;
                            case 5:
                                break mainMenu;
                            case 6:
                                System.exit(0);
                        }
                    }
                }
            } else if (!Objects.equals(admin.getAdminName(), null))
                adminView.adminMenu();
        }
    }
}