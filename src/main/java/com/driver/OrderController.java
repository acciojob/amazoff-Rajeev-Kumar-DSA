package com.driver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/add-order")
    public ResponseEntity<String> addOrder(@RequestBody Order order){
        try {
            orderService.addOrder(order);
            return new ResponseEntity<>("New order added successfully", HttpStatus.CREATED);
        }catch (Exception e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-partner/{partnerId}")
    public ResponseEntity<String> addPartner(@PathVariable String partnerId){
        try {
            orderService.addPartner(partnerId);
            return new ResponseEntity<>("New delivery partner added successfully", HttpStatus.CREATED);
        }catch (Exception e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/add-order-partner-pair")
    public ResponseEntity<String> addOrderPartnerPair(@RequestParam String orderId, @RequestParam String partnerId){
        try {
            orderService.createOrderPartnerPair(orderId, partnerId);
            //This is basically assigning that order to that partnerId
            return new ResponseEntity<>("New order-partner pair added successfully", HttpStatus.CREATED);
        }catch (Exception e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId){
        try {
            Order order= orderService.getOrderById(orderId);;
            //order should be returned with an orderId.
            return new ResponseEntity<>(order, HttpStatus.OK);
        }catch (Exception e){
//            e.getMessage();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get-partner-by-id/{partnerId}")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable String partnerId){
        try {
            DeliveryPartner deliveryPartner = orderService.getPartnerById(partnerId);;
            //deliveryPartner should contain the value given by partnerId
            return new ResponseEntity<>(deliveryPartner, HttpStatus.CREATED);
        }catch (Exception e){
//            e.getMessage();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public ResponseEntity<Integer> getOrderCountByPartnerId(@PathVariable String partnerId){
        try {
            Integer orderCount = orderService.getOrderCountByPartnerId(partnerId);;
            //orderCount should denote the orders given by a partner-id
            return new ResponseEntity<>(orderCount, HttpStatus.CREATED);
        }catch (Exception e){
//            e.getMessage();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable String partnerId){
        try {
            List<String> orders = orderService.getOrdersByPartnerId(partnerId);
            //orders should contain a list of orders by PartnerId
            return new ResponseEntity<>(orders, HttpStatus.CREATED);
        }catch (Exception e){
//            e.getMessage();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<String>> getAllOrders(){
        try {
            List<String> orders = orderService.getAllOrders();
            //Get all orders
            return new ResponseEntity<>(orders, HttpStatus.CREATED);
        }catch (Exception e){
//            e.getMessage();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get-count-of-unassigned-orders")
    public ResponseEntity<Integer> getCountOfUnassignedOrders(){
        try {
            Integer countOfOrders = orderService.getCountOfUnassignedOrders();
            //Count of orders that have not been assigned to any DeliveryPartner
            return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
        }catch (Exception e){
//            e.getMessage();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get-count-of-orders-left-after-given-time/{partnerId}")
    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(@PathVariable String time, @PathVariable String partnerId){
        try {
            Integer countOfOrders = orderService.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
            //countOfOrders that are left after a particular time of a DeliveryPartner
            return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
        }catch (Exception e){
//            e.getMessage();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(@PathVariable String partnerId){
        try {
            String time = orderService.getLastDeliveryTimeByPartnerId(partnerId);
            //Return the time when that partnerId will deliver his last delivery order.
            return new ResponseEntity<>(time, HttpStatus.CREATED);
        }catch (Exception e){
//            e.getMessage();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> deletePartnerById(@PathVariable String partnerId){
        try {
            //Delete the partnerId
            //And push all his assigned orders to unassigned orders.
            orderService.deletePartner(partnerId);
            return new ResponseEntity<>(partnerId + " removed successfully", HttpStatus.CREATED);
        }catch (Exception e){
//            e.getMessage();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId){
        try {
            //Delete an order and also
            // remove it from the assigned order of that partnerId
            orderService.deleteOrder(orderId);
            return new ResponseEntity<>(orderId + " removed successfully", HttpStatus.CREATED);
        }catch (Exception e){
//            e.getMessage();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
