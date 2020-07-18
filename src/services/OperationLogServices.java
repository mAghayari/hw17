package services;

import dao.OperationLogDao;
import dao.UserDao;
import model.OperationLog;
import model.User;
import util.Utility;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class OperationLogServices {

    public List<OperationLog> getOperationLogs(String userName, int months) {
        UserDao customerDao = new UserDao();
        User desiredCustomer = customerDao.findCustomerByUserName(userName);
        if (!Objects.equals(desiredCustomer, null)) {
            return getOperations(desiredCustomer.getId(), months);
        } else
            return null;
    }

    private List<OperationLog> getOperations(int userId, int months) {
        OperationLogDao operationLogDao = new OperationLogDao();
        Timestamp start = (Timestamp) Utility.minusMonths(months);
        Timestamp end = new Timestamp(new Date().getTime());
        return operationLogDao.getOperationLogs(userId, start, end);
    }
}