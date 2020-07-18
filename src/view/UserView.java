package view;

import model.User;
import services.UserServices;

import java.util.List;

public class UserView {
    public User getRegisterInfo() {
        UserServices userServices = new UserServices();
        GetUserInputs getUserInputs = new GetUserInputs();
        User user = new User();

        System.out.println("Enter First Name:");
        user.setFirstName(GetUserInputs.getLetteringString());

        System.out.println("Enter last Name:");
        user.setLastName(GetUserInputs.getLetteringString());

        System.out.println("Enter your age:");
        int age = GetUserInputs.getAge();
        if (age == 0)
            return null;
        user.setAge(age);

        System.out.println("Enter Cell Number(a real mobileNumber without 0):");
        String mobileNumber = GetUserInputs.getMobileString();
        if (userServices.checkMobileRepetition(mobileNumber)) {
            System.out.println("❌ This mobileNumber has already been registered\n");
            return null;
        }
        user.setMobileNumber(mobileNumber);

        System.out.println("Enter Email(a real email address):");
        String email = GetUserInputs.getEmailString();
        if (userServices.checkEmailRepetition(email)) {
            System.out.println("❌ This email has already been registered\n");
            return null;
        }
        user.setEmail(email);

        user.setAddress(getUserInputs.getAddress());

        System.out.println("Enter a userName:\n！(userName can just contains letters, digits, \"-\", \"_\" and \".\")");
        user.setUserName(GetUserInputs.getUserNameString());

        System.out.println("Enter a password:\n！(just letters and digits are allowed, 8<=password length=<16 characters)");
        user.setPassword(GetUserInputs.getPasswordString());

        System.out.println("-------------------------------------");
        return user;
    }

    public User getSignInInfo() {
        User user = new User();
        System.out.println("userName:");
        user.setUserName(GetUserInputs.getUserNameString());
        System.out.println("password:");
        user.setPassword(GetUserInputs.getPasswordString());
        return user;
    }

    public void printCustomersReport(List<User> users) {
        System.out.println("Customers Report according to their ages: ");
        for (User user : users) {
            System.out.println(user.getCustomerStringForReport());
        }
    }
}