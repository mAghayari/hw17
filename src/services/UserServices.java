package services;

import dao.UserDao;
import model.OperationLog;
import model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.Utility;
import view.GetUserInputs;
import view.OperationLogView;
import view.UserView;

import java.util.List;
import java.util.Objects;

public class UserServices {
    private static Logger LOGGER;
    UserDao userDao = new UserDao();

    public User signUp(User user) {
        userDao.addCustomer(user);
        user.setId(userDao.getCustomerCounter());
        return user;
    }

    public User findCustomer(User signedInUser) {
        return userDao.getCustomer(signedInUser);
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
        if (!Objects.equals(customer, null)) {
            customer = signUp(customer);
            LOGGER = LogManager.getLogger(customer.getUserName());
            LOGGER.info("sign up");
            System.out.println("✔ Welcome " + customer.getUserName() + "\n--------------------------");
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

    public void adminOperations() {
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
                    System.out.println("Enter customer's userName:");
                    String userName = GetUserInputs.getUserNameString();
                    User desiredCustomer = userDao.findCustomerByUserName(userName);
                    if (Objects.nonNull(desiredCustomer)) {
                        System.out.println("How many month's report do you want?");
                        int months = GetUserInputs.getInteger();
                        OperationLogView operationLogView = new OperationLogView();
                        OperationLogServices operationLogServices = new OperationLogServices();
                        List<OperationLog> operationLogs = operationLogServices.getOperationLogs(userName, months);
                        if (operationLogs.isEmpty())
                            System.out.println("❌ No operation found");
                        else
                            operationLogView.print(operationLogs);
                    } else
                        System.out.println("❌ UserName not found");
                    break;
                case 3:
                    break adminMenu;
                case 4:
                    System.exit(0);
            }
        }
    }
}