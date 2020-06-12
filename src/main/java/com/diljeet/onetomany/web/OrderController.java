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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author diljeet
 */
@Named(value = "orderController")
@SessionScoped
public class OrderController implements Serializable {

    private static final Logger logger = Logger.getLogger(OrderController.class.getCanonicalName());

    private static final long serialVersionUID = 2142383151318489373L;

    @EJB
    private OrderBean orderBean;

    private Order order;

    private List<Order> orders;

    private int currentCustomer;

    private int searchCustomer;

    private String queryString;

    @PostConstruct
    public void init() {
        order = new Order();
    }

    /**
     * Creates a new instance of OrderController
     */
    public OrderController() {

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

//    public List<Order> getOrders() {
//        //logger.log(Level.INFO, queryString);
//        if (queryString.equals("all")) {
//            return orderBean.fetchAllOrders();
//        } else if (queryString.equals("search")) {
//            return orderBean.getOrdersById(searchCustomer);
//        } else {
//            return orderBean.getOrdersById(currentCustomer);
//        }
//    }
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(int currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public int getSearchCustomer() {
        return searchCustomer;
    }

    public void setSearchCustomer(int searchCustomer) {
        this.searchCustomer = searchCustomer;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void addOrder(Order order, int customerId) {
        orderBean.addOrder(order, customerId);
        setOrders(orderBean.fetchOrdersById(customerId));
        clear();        
    }

    public void fetchAllOrders() {
        setOrders(orderBean.fetchAllOrders());
    }

    public void fetchOrdersById() {
        setOrders(orderBean.fetchOrdersById(searchCustomer));
    }

    public void fetchOrdersByCurrentCustomer(int currentCustomer) {
        setOrders(orderBean.fetchOrdersById(currentCustomer));
    }

    public void deleteOrderById(int orderId) {
        orderBean.deleteOrderById(orderId);
        if (queryString.equals("all")) {
            setOrders(orderBean.fetchAllOrders());
        } else if (queryString.equals("search")) {
            setOrders(orderBean.fetchOrdersById(searchCustomer));
        } else {
            setOrders(orderBean.fetchOrdersById(currentCustomer));
        }
    }
    
    private void clear() {
        order.setItem(null);
        order.setQuantity(null);
        order.setOrderCreated(null);
    }

//    public void onCellEdit(CellEditEvent event) {
//        logger.log(Level.INFO, "inside oncelledit method");
//        String oldValue = (String)event.getOldValue();
//        Object newValue = event.getNewValue();
//        String orderId = event.getComponent().getId();
//        logger.log(Level.INFO, "value is ", oldValue);
//        logger.log(Level.INFO, "value is ", newValue);
//        logger.log(Level.INFO, "value is ", orderId);
//         
//        if(newValue != null && !newValue.equals(oldValue)) {
////            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
////            FacesContext.getCurrentInstance().addMessage(null, msg);
//              orderBean.updateOrderById(newValue , orderId);
//        }
//    }
    public void onRowEdit(RowEditEvent<Order> event) {
        orderBean.updateOrderById(event.getObject());

        //logger.log(Level.INFO, "value is {0}", order.getId());
//        if((event.getObject() instanceof Order) && (event.getObject() != null)){
//            orderBean.updateOrderById(order , event.getObject().getId());
//        }
//        FacesMessage msg = new FacesMessage("Item changed to ", event.getObject().getItem());
//        FacesContext.getCurrentInstance().addMessage("msgs", msg);
    }

    public void onRowCancel(RowEditEvent<Order> event) {
//        FacesMessage msg = new FacesMessage("Edit Cancelled", Long.toString(event.getObject().getId()));
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    

}
