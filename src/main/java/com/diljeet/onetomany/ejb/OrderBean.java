/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diljeet.onetomany.ejb;

import com.diljeet.onetomany.entity.Customer;
import com.diljeet.onetomany.entity.Order;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
    @PersistenceContext(name = "my_persistence_unit")
    EntityManager em;
    
    public void addOrder(Order order){
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int customerId = Integer.parseInt(params.get("customerId"));
        //logger.log(Level.INFO, "Customer ID is {0}" , customerId);        
        try{
            if(order != null){                  
                Customer customer = em.find(Customer.class, customerId);                  
                customer.addOrder(order);
                em.persist(customer);
            } else {
                return;
            }
           
        } catch(Exception e) {
            //e.printStackTrace();
            logger.log(Level.INFO, "Error placing Order {0}" , e.toString());
        }
    }
    
//    public List<Order> getCustomers(){
//        List<Order> customers = null;
//        try{
//            customers = em.createNamedQuery("getCustomers").getResultList();
//        } catch(Exception e) {
//            logger.log(Level.INFO, "Error saving entity {0}" , e.toString());
//        }
//        
//        return customers;
//    }    
}
