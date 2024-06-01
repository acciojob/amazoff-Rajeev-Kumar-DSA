package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        orderMap.put(order.getId(), order);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        partnerMap.put(partnerId, deliveryPartner);
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order

            orderToPartnerMap.put(orderId, partnerId);

            partnerToOrderMap.computeIfAbsent(partnerId, k->new HashSet<>()).add(orderId);
            DeliveryPartner partner = partnerMap.get(partnerId);
            partner.setNumberOfOrders(partner.getNumberOfOrders()+1);

        }
    }

    public Order findOrderById(String orderId){
        // your code here
        if (orderMap.containsKey(orderId)){
            return orderMap.get(orderId);
        }
        return null;
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        if(partnerMap.containsKey(partnerId)){
            return partnerMap.get(partnerId);
        }
        return null;
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        if (partnerMap.containsKey(partnerId)){
            DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
            return deliveryPartner.getNumberOfOrders();
        }
        return 0;
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        if(partnerToOrderMap.containsKey(partnerId)){
            return new ArrayList<>(partnerToOrderMap.get(partnerId));
        }
        return new ArrayList<>();
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        if(!orderMap.isEmpty()){
            return new ArrayList<>(orderMap.keySet());
        }
        return new ArrayList<>();
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID

        // first I have to check in partnerMap . given id is present or not
        // jis partner ko remove karna tha agar uske liye koi order assign hai tab usko bhi remove karna hoga
        // because partner available nahi hai toh uska order bhi remove hoga
        if(partnerMap.containsKey(partnerId)){
            DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
            partnerMap.remove(partnerId);
            if(partnerToOrderMap.containsKey(partnerId)){
                for(String orderId : partnerToOrderMap.get(partnerId)){
                    orderToPartnerMap.remove(orderId);
                }
                partnerToOrderMap.remove(partnerId);
            }
        }
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        // I have to check orderId is present in ordermap or not
        // then remove from ordertoPartnerMap ,who partner person took the order, remove from their
        // after delation we have to decrease the count of numberOfOrder
        if (orderMap.containsKey(orderId)){
            String partnerId = orderToPartnerMap.get(orderId);
            orderMap.remove(orderId);
            orderToPartnerMap.remove(orderId);
            if(partnerId != null && partnerToOrderMap.containsKey(partnerId)){
                partnerToOrderMap.get(partnerId).remove(orderId);
                DeliveryPartner partner = partnerMap.get(partnerId);
                partner.setNumberOfOrders(partner.getNumberOfOrders() - 1);
            }
        }
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        // I have to check all order -> particular for each order is mapped in ordertopartnermap or not
        //
        int count = 0;
        for(String orderId : orderMap.keySet()){
            if(!orderToPartnerMap.containsKey(orderId)){
                count++;
            }
        }
        return count;
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        int count = 0;
        int time = parseTime(timeString);
        if(partnerToOrderMap.containsKey(partnerId)){
            for(String orderId : partnerToOrderMap.get(partnerId)){
                Order order = orderMap.get(partnerId);
                if(order != null && order.getDeliveryTime() > time){
                    count ++;
                }
            }
        }
        return count;
    }

    private int parseTime(String timeString){
        String[] timeDivide = timeString.split(":");
        int hours = Integer.parseInt(timeDivide[0]);
        int minutes = Integer.parseInt(timeDivide[1]);
        return hours*60 + minutes;
    }

    private String convertTimeToString(int time){
        int hours = time / 60;
        int minutes = time % 60;
        return String.format("%02d:%02d", hours, minutes);
    }


    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
        int lastTime = -1;
        if(partnerToOrderMap.containsKey(partnerId)){
            for(String orderId : partnerToOrderMap.get(partnerId)){
                Order order = orderMap.get(orderId);
                if(order != null && order.getDeliveryTime() > lastTime){
                    lastTime = order.getDeliveryTime();
                }
            }
        }
        return null;
    }
}