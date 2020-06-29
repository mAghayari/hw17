package dao;

import model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDao {
    SessionFactory sessionFactory = new Configuration().configure("config/hibernate.cfg.xml").buildSessionFactory();

    public List<Product> getAllProducts() {
        List<Product> products;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From Product");
        products = query.list();
        transaction.commit();
        session.close();
        return products;
    }

    public void updateProduct(Product product) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("update Product set stock=:stock where id=:id");
        query.setParameter("stock", product.getStock());
        query.setParameter("id", product.getId());
        query.executeUpdate();
        transaction.commit();
        session.close();
    }
}