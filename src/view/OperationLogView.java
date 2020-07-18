package view;

import model.OperationLog;

import java.util.List;

public class OperationLogView {
    public void print(List<OperationLog> operationLogs){
        operationLogs.forEach(System.out::println);
    }
}
