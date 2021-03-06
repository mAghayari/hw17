package dao;

import model.User;
import model.Order;
import model.OrderDto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

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

    public List<Order> getCustomerOrders(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Order> orders;
        Query query = session.createQuery("From orders o Where o.customer.id = :customerId");
        query.setParameter("customerId", user.getId());
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
        Query query = session.createQuery("SELECT o.orderDate , o.orderItems FROM orders o " +
                "WHERE o.customer.id = :customerId AND o.orderDate>= ?1 AND o.orderDate<= ?2");
        query.setParameter("customerId", customerId);
        query.setParameter(1, sqlStartDate);
        query.setParameter(2, sqlEndDate);

        orderDtoList = query.list();
        transaction.commit();
        session.close();
        return orderDtoList;
    }
}