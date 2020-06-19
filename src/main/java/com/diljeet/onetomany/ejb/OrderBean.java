/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diljeet.onetomany.ejb;

import com.diljeet.onetomany.entity.Customer;
import com.diljeet.onetomany.entity.Order;
import com.diljeet.onetomany.web.OrderController;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author diljeet
 */
@Named
@Stateful
public class OrderBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private static final Logger logger = Logger.getLogger(OrderBean.class.getCanonicalName());
    
    private Client client;

//     private int customerId;
//    @PersistenceContext(name = "my_persistence_unit")
//    EntityManager em;
    
    @PostConstruct
    private void init(){
        client = ClientBuilder.newClient();
    }
    
    @PreDestroy
    private void clean(){
        client.close();
    }

    public void addOrder(Order order, int customerId) {
        //logger.log(Level.INFO, "Customer Id is {0}", customerId);
        if(order == null){
            logger.info("Order is null");
        }
        
        Response response = client.target("http://localhost:9090/onetomany/webapi/Order")
                .request(MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML)
                .header("customerId", customerId)
                .post(Entity.entity(order, MediaType.APPLICATION_XML), Response.class);
                
               
        
         if(response.getStatus() == Response.Status.CREATED.getStatusCode()){
            logger.info("Order placed successfully");            
        } else {
            logger.info("Problem placing Order");
        }
    
//        try {
//            if (order != null) {
//                Customer customer = em.find(Customer.class, customerId);
//                customer.addOrder(order);
//                em.persist(customer);
//            } else {
//                return;
//            }
//
//        } catch (Exception e) {
//            //e.printStackTrace();
//            logger.log(Level.INFO, "Error placing Order {0}", e.toString());
//        }
    }

//    public List<Order> getOrders(int customerId) {
//        List<Order> orders = null;   
//        
//            if(customerId > 0){
//                orders = getOrdersById(customerId);
//            } else {
//                orders = fetchAllOrders();
//                logger.log(Level.INFO, "Returned fethAllOrders method result");
//            }
//        
//        return orders;
//    }
    public List<Order> fetchOrdersById(int customerId) {
        //logger.log(Level.INFO, "to fetch orders by currentcustomer {0}", customerId);
        String id  = String.valueOf(customerId);
        List<Order> orders = client.target("http://localhost:9090/onetomany/webapi/Order")
                .path(id)
                .request(MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML)
                .get(new GenericType<List<Order>>(){});

//        try {
//            Query query = em.createNamedQuery("fetchOrdersById");
//            query.setParameter("customerId", customerId);
//            orders = query.getResultList();
//        } catch (Exception e) {
//            logger.log(Level.INFO, "Error retrieving orders {0}", e.toString());
//        }

        return orders;
    }

    public List<Order> fetchAllOrders() {
        //logger.log(Level.INFO, "Entered fethAllOrders method");
        List<Order> orders = client.target("http://localhost:9090/onetomany/webapi/Order")
                .path("all")
                .request(MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML)
                .get(new GenericType<List<Order>>(){});
//        try {
//            orders = em.createNamedQuery("fetchAllOrders").getResultList();
//        } catch (Exception e) {
//            logger.log(Level.INFO, "Error retrieving orders {0}", e.toString());
//        }

        return orders;
    }

    public void deleteOrderById(long orderId) {
        String id  = String.valueOf(orderId);
        Response response = client.target("http://localhost:9090/onetomany/webapi/Order")
                .path(id)
                .request(MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML)
                .delete();
        
        if(response.getStatus() == Response.Status.OK.getStatusCode()){
            logger.info("Order deleted successfully");
        } else {
            logger.info("Error deleting Order");
        }
        //logger.log(Level.INFO, "Entered deleteOrderById method");
        //List<Order> orders = null;
//        try {
//            Order order = em.find(Order.class, orderId);
//            if(order != null){
//               // logger.log(Level.INFO, "Inside if block");
//                em.remove(order);
//            } else {
//                return;
//            }            
//        } catch (Exception e) {
//            logger.log(Level.INFO, "Error deleting order {0}", e.toString());
//        }

    }

    public void updateOrderById(Order updatedOrder) {
        //logger.log(Level.INFO, "inside update method");        
        Long orderId = updatedOrder.getId();
        String id  = String.valueOf(orderId);
        Response response = client.target("http://localhost:9090/onetomany/webapi/Order")
                .path(id)
                .request(MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML)
                .put(Entity.entity(updatedOrder, MediaType.APPLICATION_XML), Response.class);
        
        if(response.getStatus() == Response.Status.OK.getStatusCode()){
            logger.info("Order updated successfully");
        } else {
            logger.info("Error updating Order");
        }
//        try {
//            Order oldOrder = em.find(Order.class, orderId);
//            if (oldOrder != null) {
//                oldOrder.setItem(updatedOrder.getItem());
//                oldOrder.setQuantity(updatedOrder.getQuantity());
//                oldOrder.setOrderUpdated(new Date());
////                em.persist(updatedOrder);
//                //logger.log(Level.INFO, "record updated");
//            } else {
//                return;
//            }
//        } catch (Exception e) {
//            logger.log(Level.INFO, "Error updating Order");
//        }
    }

    

}
