package dao;

import model.Customer;
import model.Order;
import model.OrderDto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao {
    SessionFactory sessionFactory = new Configuration().configure("config/hibernate.cfg.xml").buildSessionFactory();

    public void addAnOrder(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(order);
        transaction.commit();
        session.close();
    }

    public List<Order> getCustomerOrders(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Order> orders;
        Query query = session.createQuery("From orders o Where o.customer.id = :customerId");
        query.setParameter("customerId", customer.getId());
        orders = query.list();
        transaction.commit();
        session.close();
        return orders;
    }

    public List<OrderDto> findOrders(int customerId, Date startDate, Date endDate) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
        List<OrderDto> orderDtoList;
        Query query = session.createQuery("SELECT `orders`.orderDate , `orders`.orderItems FROM `orders` " +
                "WHERE customerID = ? AND orderDate BETWEEN ? AND ?");
        query.setParameter(1, customerId);
        query.setParameter(2, sqlStartDate);
        query.setParameter(3, sqlEndDate);

        orderDtoList = query.list();
        transaction.commit();
        session.close();
        return orderDtoList;
    }
}