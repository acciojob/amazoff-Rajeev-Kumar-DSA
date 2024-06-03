package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        if (id == null || deliveryTime == null || !deliveryTime.matches("\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Invalid id or deliveryTime format");
        }
        this.id = id;
        this.deliveryTime = covertDeliveryTime(deliveryTime);
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    private int covertDeliveryTime(String deliveryTime){
        // split the string with respect to colon ->  :
        String[] timeParts = deliveryTime.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int mint = Integer.parseInt(timeParts[1]);
        return hour*60 + mint;
    }
}
