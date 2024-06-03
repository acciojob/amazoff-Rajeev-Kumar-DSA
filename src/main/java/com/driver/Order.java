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
        if (id == null || id.isEmpty() || deliveryTime == null || deliveryTime.isEmpty()) {
            // Initialize with default values or handle accordingly
            this.id = "defaultId";
            this.deliveryTime = 0;  // Default to midnight (00:00)
        } else {
            this.id = id;
            this.deliveryTime = covertDeliveryTime(deliveryTime);
        }
//        this.id = id;
//        this.deliveryTime = covertDeliveryTime(deliveryTime);
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    private int covertDeliveryTime(String deliveryTime){
        // split the string with respect to colon ->  :
        try {
            String[] timeParts = deliveryTime.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int mint = Integer.parseInt(timeParts[1]);
            return hour*60 + mint;
        }catch (Exception e){
            return 0;   // midnight time
        }
    }
}
