package services;

import model.OperationLog;
import util.Utility;

import java.util.Date;

public class OperationLogServices {

    public OperationLog getOperationLog(String authority) {
        OperationLog operationLog = new OperationLog();
        OrderServices orderServices = new OrderServices();

        Date startDate = Utility.minusAMonth();
        Date endDate = Utility.addADay(new Date());

        operationLog.setAuthority(authority);

        return operationLog;
    }
}