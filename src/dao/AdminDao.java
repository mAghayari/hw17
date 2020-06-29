package dao;

import model.Admin;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class AdminDao {
    SessionFactory sessionFactory = new Configuration().configure("config/hibernate.cfg.xml").buildSessionFactory();
    public static final Logger LOGGER = Logger.getLogger(AdminDao.class);

    public void addAdmin(Admin admin) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(admin);
        transaction.commit();
        session.close();
        LOGGER.info("Admin " + admin.getAdminName() + " Signed up");
    }

    public Admin getAdmin(Admin admin) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From Admin a Where a.userName = :userName And a.password= :password");
        query.setParameter("userName", admin.getAdminName());
        query.setParameter("password", admin.getPassword());
        admin = (Admin) query.uniqueResult();
        transaction.commit();
        session.close();
        return admin;
    }
}