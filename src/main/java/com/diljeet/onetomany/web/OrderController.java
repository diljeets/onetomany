/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diljeet.onetomany.web;

import com.diljeet.onetomany.ejb.OrderBean;
import com.diljeet.onetomany.entity.Order;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author diljeet
 */
@Named(value = "orderController")
@SessionScoped
public class OrderController implements Serializable{
    
    private static final long serialVersionUID = 2142383151318489373L;
    
    @EJB
    private OrderBean orderBean;
    
    private Order order;
    
    private List<Order> orders;

    /**
     * Creates a new instance of OrderController
     */
    public OrderController() {
        order = new Order();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
    
}
