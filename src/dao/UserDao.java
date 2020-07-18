package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class UserDao {
    SessionFactory sessionFactory = new Configuration().configure("config/hibernate.cfg.xml").buildSessionFactory();
    private int customerCounter = 0;

    public void addCustomer(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public User getCustomer(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From user c Where c.userName = :userName And c.password= :password");
        query.setParameter("userName", user.getUserName());
        query.setParameter("password", user.getPassword());
        user = (User) query.uniqueResult();
        transaction.commit();
        session.close();
        return user;
    }

    public List<User> getAllCustomers() {
        List<User> users;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From user u where u.isAdmin = 0");
        users = query.list();
        transaction.commit();
        session.close();
        customerCounter = users.size();
        return users;
    }

    public User findCustomerByUserName(String userName) {
        User user;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From user c Where c.userName = :userName");
        query.setParameter("userName", userName);
        user = (User) query.uniqueResult();
        transaction.commit();
        session.close();
        return user;
    }

    public User getAdmin(User admin) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("From user admin Where " +
                    "admin.userName = :userName And " +
                    "admin.password= :password And " +
                    "admin.isAdmin = 1");
            query.setParameter("userName", admin.getUserName());
            query.setParameter("password", admin.getPassword());
            admin = (User) query.uniqueResult();
            transaction.commit();
            session.close();
        }
        return admin;
    }

    public int getCustomerCounter() {
        return customerCounter;
    }
}