package model;

import java.sql.Timestamp;

public class OperationLog {
    private Timestamp dateTime;
    private String operation;
    private String authority;

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operations) {
        this.operation = operations;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "operation " + operation +
                " performed by " + authority +
                " on " + dateTime;
    }
}