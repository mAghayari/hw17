package model;

public class OperationLog {
    private String operation;
    private String authority;

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
                " performed by " + authority;
    }
}