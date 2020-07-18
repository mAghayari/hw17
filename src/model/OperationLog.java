package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name = "logs")
public class OperationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String operation;
    private String authority;
    private Timestamp operationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Timestamp getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Timestamp operationDate) {
        this.operationDate = operationDate;
    }

    @Override
    public String toString() {
        return "operation " + operation +
                " performed by " + authority;
    }
}