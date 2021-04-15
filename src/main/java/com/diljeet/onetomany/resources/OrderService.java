/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diljeet.onetomany.resources;

import com.diljeet.onetomany.ejb.OrderBean;
import com.diljeet.onetomany.entity.Customer;
import com.diljeet.onetomany.entity.Order;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author diljeet
 */
@Stateless
@Path("/Order")
@RolesAllowed("Administrator")
public class OrderService {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private static final Logger logger = Logger.getLogger(OrderBean.class.getCanonicalName());

//     private int customerId;
    @PersistenceContext(name = "my_persistence_unit")
    EntityManager em;

    public OrderService() {
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
    public Response addOrder(Order order, @HeaderParam("customerId") int customerId) {
//        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        customerId = Integer.parseInt(params.get("customerId"));
        //logger.log(Level.INFO, "Customer ID is {0}" , customerId);   
//        int customerId = order.getCustomer().getCustomerId();

        logger.log(Level.INFO, "Customer Id is {0}", customerId);
        try {
            if (order != null) {
                Customer customer = em.find(Customer.class, customerId);
                customer.addOrder(order);
                em.persist(customer);
            } else {
                logger.info("Could not find Order");
            }

        } catch (Exception e) {
            //e.printStackTrace();
            logger.log(Level.INFO, "Error placing Order {0}", e.toString());
        }

        return Response.created(URI.create("/" + customerId)).build();
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
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
    public List<Order> fetchOrdersById(@PathParam("id") int customerId) {
        //logger.log(Level.INFO, "to fetch orders by currentcustomer {0}", customerId);
        List<Order> orders = null;
        Customer customer = em.find(Customer.class, customerId);
        if (customer != null) {
            try {
                Query query = em.createNamedQuery("fetchOrdersById");
                query.setParameter("customerId", customerId);
                orders = query.getResultList();
            } catch (Exception e) {
                logger.log(Level.INFO, "Error retrieving orders {0}", e.toString());
            }
        } else {
            logger.info("Customer does not exist");
        }

        return orders;
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})    
    public List<Order> fetchAllOrders() {
        //logger.log(Level.INFO, "Entered fethAllOrders method");
        List<Order> orders = null;
        try {
            orders = em.createNamedQuery("fetchAllOrders").getResultList();
        } catch (Exception e) {
            logger.log(Level.INFO, "Error retrieving orders {0}", e.toString());
        }

        return orders;
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
    public Response deleteOrderById(@PathParam("id") long orderId) {
        //logger.log(Level.INFO, "Entered deleteOrderById method");
        //List<Order> orders = null;
        try {
            Order order = em.find(Order.class, orderId);
            if (order != null) {
                // logger.log(Level.INFO, "Inside if block");
                em.remove(order);
            } else {
                logger.info("Could not find Order");
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "Error deleting order {0}", e.toString());
        }

        return Response.ok().status(200).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
    public Response updateOrderById(@PathParam("id") Long orderId, Order updatedOrder) {
        //logger.log(Level.INFO, "inside update method");        
        //Long orderId = updatedOrder.getId();
        logger.log(Level.INFO, "orderId is {0}", orderId);
        try {
            Order oldOrder = em.find(Order.class, orderId);
            if (oldOrder != null) {
                oldOrder.setItem(updatedOrder.getItem());
                oldOrder.setQuantity(updatedOrder.getQuantity());
                oldOrder.setOrderUpdated(new Date());
//                em.persist(updatedOrder);
                //logger.log(Level.INFO, "record updated");
            } else {
                logger.info("Could not find Order");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }

        return Response.ok().status(200).build();
    }
}
