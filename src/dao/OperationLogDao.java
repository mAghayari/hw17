package dao;

import model.OperationLog;
import model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.util.List;

public class OperationLogDao {
    SessionFactory sessionFactory = new Configuration().configure("config/hibernate.cfg.xml").buildSessionFactory();

    public List<OperationLog> getOperationLogs(int userId, Timestamp start, Timestamp end) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<OperationLog> operationLogs;
        Query query = session.createQuery("FROM logs l WHERE l.authority = :authority AND l.operationDate >= ?1 AND  l.operationDate<= ?2", OperationLog.class);
        query.setParameter("authority", userId);
        query.setParameter(1, start);
        query.setParameter(2, end);
        operationLogs = query.list();
        transaction.commit();
        session.close();
        return operationLogs;
    }
}