package view;

import dao.CustomerDao;
import model.Customer;
import model.OperationLog;
import services.OperationLogServices;

import java.util.Objects;

public class OperationLogView {
    public void getCustomerOperations() {
        OperationLogServices operationLogServices = new OperationLogServices();
        CustomerDao customerDao = new CustomerDao();
        System.out.println("Enter customer's userName:");
        String userName = GetUserInputs.getUserNameString();
        Customer desiredCustomer = customerDao.findCustomerByUserName(userName);
        if (!Objects.equals(desiredCustomer, null)) {
            OperationLog operationLog = operationLogServices.getOperationLog(desiredCustomer);
            if (operationLog.getOperation().isEmpty())
                System.out.println("❌ No record found for this customer during last month\n");
            else
                System.out.println(operationLog.toString());
        } else System.out.println("❌ Customer not found");
    }
}