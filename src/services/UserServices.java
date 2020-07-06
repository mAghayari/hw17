package services;

import dao.OrderDao;
import dao.UserDao;
import model.OperationLog;
import model.OrderDto;
import model.User;
import org.apache.log4j.Logger;
import util.Utility;
import view.GetUserInputs;
import view.OrderDtoView;
import view.UserView;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UserServices {
    private static final Logger LOGGER = Logger.getLogger(UserServices.class);
    UserDao userDao = new UserDao();

    public User signUp(User user) {
        userDao.addCustomer(user);
        user.setId(userDao.getCustomerCounter());
        return user;
    }

    public User findCustomer(User signedInUser) {
        return userDao.findCustomerByUserName(signedInUser.getUserName());
    }

    public List<User> getCustomersList() {
        List<User> customers = userDao.getAllCustomers();
        Utility.sortingCustomers(customers);
        return customers;
    }

    public boolean checkEmailRepetition(String email) {
        List<User> users = getCustomersList();
        for (User user : users) {
            if (Objects.equals(user.getEmail(), email))
                return true;
        }
        return false;
    }

    public boolean checkMobileRepetition(String mobileNumber) {
        List<User> users = getCustomersList();
        for (User user : users) {
            if (Objects.equals(user.getMobileNumber(), mobileNumber))
                return true;
        }
        return false;
    }

    public User customerSignUp(User customer) {
        UserView userView = new UserView();
        if (!Objects.equals(customer, null)) {
            customer = signUp(customer);
            System.out.println("✔ Welcome " + customer.getUserName() + "\n--------------------------");
            OperationLogServices operationLogServices = new OperationLogServices();
            OperationLog operationLog =
                    operationLogServices.getOperationLog("customer " + customer.getUserName(), "signUp");
            LOGGER.info(operationLog.toString());
        }
        return customer;
    }

    public User customerSignIn(User customer) {
        customer = findCustomer(customer);
        return customer;
    }

    public User adminSignIn(User admin) {
        admin = userDao.getAdmin(admin);
        return admin;
    }

    public void adminOperations(User admin) {
        UserView userView = new UserView();
        adminMenu:
        while (true) {
            System.out.println("1-Customers report according to their age\n2-User's last month report\n3-SignOut\n4-Exit");
            int adminMenuItem = GetUserInputs.getInBoundDigitalInput(3);
            switch (adminMenuItem) {
                case 1:
                    userView.printCustomersReport(getCustomersList());
                    break;
                case 2:
                    OrderDtoView orderDtoView = new OrderDtoView();
                    System.out.println("Enter customer's userName:");
                    String userName = GetUserInputs.getUserNameString();
                    User desiredCustomer = userDao.findCustomerByUserName(userName);
                    if (Objects.nonNull(desiredCustomer)) {
                        System.out.println("How many month's report do you want?");
                        int months = GetUserInputs.getInteger();
                        Date startDate = Utility.minusMonths(months);
                        OrderDao orderDao = new OrderDao();
                        List<OrderDto> orders = orderDao.findOrders(desiredCustomer.getId(), startDate, new Date());
                        orderDtoView.printOrderDtos(orders);
                    } else
                        System.out.println("❌ UserName not found");
                    break;
                case 3:
                    OperationLogServices operationLogServices = new OperationLogServices();
                    OperationLog operationLog =
                            operationLogServices.getOperationLog("customer " + admin.getUserName(), "signOut");
                    LOGGER.info(operationLog.toString());
                    break adminMenu;
                case 4:
                    System.exit(0);
            }
        }
    }
}