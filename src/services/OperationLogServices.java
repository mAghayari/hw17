package services;

import model.OperationLog;

public class OperationLogServices {

    public OperationLog getOperationLog(String authority, String operation) {
        OperationLog operationLog = new OperationLog();
        operationLog.setAuthority(authority);
        operationLog.setOperation(operation);
        return operationLog;
    }
}