package model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private Address address;
    private String userName;
    private String password;
    private int age;
    private boolean isAdmin;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Order> orders = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        String userInfo = "User " + id +
                "\nfirstName: " + firstName +
                "\nlastName: " + lastName +
                "\nmobileNumber: " + mobileNumber +
                "\nemail: " + email +
                "\naddress: " + address +
                "\nage: " + age;
        if (!isAdmin) {
            userInfo += "\nOrders: " + orders.toString() +
                    "\n---------------------------------\n";
        }
        return userInfo;
    }

    public String getCustomerStringForReport() {
        return "User " + id +
                "\nfirstName: " + firstName +
                "\nlastName: " + lastName +
                "\nmobileNumber: " + mobileNumber +
                "\nemail: " + email +
                "\naddress: " + address +
                "\nage: " + age +
                "\n--------------------------------\n";
    }

    public static int compare(User user1, User user2) {
        return Integer.compare(user1.getAge(), user2.getAge());
    }
}