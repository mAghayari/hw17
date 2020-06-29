package model;

import util.Utility;

import java.util.Date;

public class OrderDto {
    private Date date;
    private String orderedItems;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrderedItems() {
        return orderedItems;
    }

    public void setCartItems(String orderedItems) {
        this.orderedItems = orderedItems;
    }

    @Override
    public String toString() {
        return "on " + Utility.getDate(date) +
                " at " + Utility.getTime(date) +
                " bought:\n" + orderedItems +
                "--------------------------------\n";
    }
}