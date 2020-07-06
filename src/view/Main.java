package view;

import dao.ProductDao;
import model.*;
import org.apache.log4j.Logger;
import services.OperationLogServices;
import services.OrderServices;
import services.UserServices;

import java.util.List;
import java.util.Objects;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        OrderServices orderServices = new OrderServices();
        OrderView orderView = new OrderView();
        UserServices userServices = new UserServices();
        UserView userView = new UserView();
        while (true) {
            User user;
            Order order = new Order();
            System.out.println("1)SignIn\n2)SignUp");
            int enterItem = GetUserInputs.getInBoundDigitalInput(2);
            if (enterItem == 1) {
                OperationLog operationLog = null;
                System.out.println("1-Admin SignIn \n2-Customer SignIn");
                int signInItem = GetUserInputs.getInBoundDigitalInput(2);
                if (signInItem == 1) {
                    user = userServices.adminSignIn(userView.getSignInInfo());
                    if (!Objects.equals(user.getUserName(), null)) {
                        System.out.println("✔ Welcome " + user.getUserName() + "\n--------------------------");
                        OperationLogServices operationLogServices = new OperationLogServices();
                        operationLog =
                                operationLogServices.getOperationLog("admin " + user.getUserName(), "signIn");
                        LOGGER.info(operationLog.toString());
                    } else
                        System.out.println("❌ InCorrect UserName Or Password");
                } else {
                    user = userServices.customerSignIn(userView.getSignInInfo());
                    if (!Objects.equals(user, null)) {
                        System.out.println("✔ Welcome " + user.getUserName() + "\n--------------------------");

                        OperationLogServices operationLogServices = new OperationLogServices();
                        operationLog =
                                operationLogServices.getOperationLog("customer " + user.getUserName(), "signIn");
                        LOGGER.info(operationLog.toString());
                    } else
                        System.out.println("❌ InCorrect UserName Or Password");
                    order.setCustomer(user);
                }
            } else {
                System.out.println("Customer SignUp:");
                user = userServices.customerSignUp(userView.getRegisterInfo());
                order.setCustomer(user);
            }
            if (!Objects.equals(order.getCustomer(), null)) {
                ProductDao productDao = new ProductDao();
                List<Product> products;
                mainMenu:
                while (true) {
                    System.out.println("Main Menu:");
                    System.out.println("1-Products category's List\n2-Delete Order Items\n3-Print Order\n4-Finalize Order\n5-SignOut\n6-Exit\nChoose an item:");
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
                                        orderServices.addProductToOrder(order, productDao, products, "ELECTRONICS");
                                        break;
                                    case 2:
                                        orderServices.addProductToOrder(order, productDao, products, "READINGS");
                                        break;
                                    case 3:
                                        orderServices.addProductToOrder(order, productDao, products, "SHOES");
                                        break;
                                    case 4:
                                        break subMenu;
                                }
                                break;
                            case 2:
                                if (order.getOrderItems().isEmpty())
                                    System.out.println("Your Order is empty\n");
                                else
                                    order = orderServices.deleteOperation(order, products);
                                break subMenu;
                            case 3:
                                orderView.printOrder(order);
                                break subMenu;
                            case 4:
                                if (order.getOrderItems().isEmpty())
                                    System.out.println("Your Order is empty\n");
                                else
                                    orderServices.finalizeOrder(order);
                                break subMenu;
                            case 5:
                                if (!order.getOrderItems().isEmpty())
                                    orderServices.cancelOrder(order);
                                OperationLogServices operationLogServices = new OperationLogServices();
                                OperationLog operationLog =
                                        operationLogServices.getOperationLog("customer " + order.getCustomer().getUserName(), "signOut");
                                LOGGER.info(operationLog.toString());
                                break mainMenu;
                            case 6:
                                System.exit(0);
                        }
                    }
                }
            } else if (!Objects.equals(user.getUserName(), null) && user.isAdmin())
                userServices.adminOperations(user);
        }
    }
}