package view;

import model.Admin;
import services.AdminServices;

import java.util.Objects;

public class AdminView {

    public Admin registerAdmin() {
        Admin admin = new Admin();
        System.out.println("Registering a new admin:\nEnter userName:");
        admin.setAdminName(GetUserInputs.getUserNameString());
        System.out.println("Enter password:");
        admin.setPassword(GetUserInputs.getPasswordString());
        return admin;
    }

    public Admin getSignInInfo() {
        Admin admin = new Admin();
        System.out.println("UserName:");
        admin.setAdminName(GetUserInputs.getUserNameString());
        System.out.println("Password:");
        admin.setPassword(GetUserInputs.getPasswordString());
        return admin;
    }

    public Admin adminSignIn() {
        AdminServices adminServices = new AdminServices();
        Admin admin = getSignInInfo();
        admin = adminServices.findAdmin(admin);
        if (!Objects.equals(admin.getAdminName(), null)) {
            System.out.println("Welcome " + admin.getAdminName() + "\n--------------------------");
        } else
            System.out.println("❌ InCorrect UserName Or Password");
        return admin;
    }

    public Admin adminSignUp() {
        AdminServices adminServices = new AdminServices();
        Admin admin = registerAdmin();
        if (!Objects.equals(admin, null)) {
            admin = adminServices.signUp(admin);
            System.out.println("✔ Welcome " + admin.getAdminName() + "\n--------------------------");
        }
        return admin;
    }

    public void adminMenu() {
        CustomerView customerView = new CustomerView();
        OperationLogView operationLogView = new OperationLogView();
        adminMenu:
        while (true) {
            System.out.println("1-Customers report according to their age\n2-Customer's last month report\n3-SignOut\n4-Exit");
            int adminMenuItem = GetUserInputs.getInBoundDigitalInput(3);
            switch (adminMenuItem) {
                case 1:
                    customerView.printReport();
                    break;
                case 2:
                    operationLogView.getCustomerOperations();
                    break;
                case 3:
                    break adminMenu;
                case 4:
                    System.exit(0);
            }
        }
    }
}