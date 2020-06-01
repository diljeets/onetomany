/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diljeet.onetomany.ejb;

import com.diljeet.onetomany.entity.Customer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author diljeet
 */
@Named
@Stateless
public class CustomerBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private static final Logger logger = Logger.getLogger(CustomerBean.class.getCanonicalName());
    
    @PersistenceContext(name = "my_persistence_unit")
    EntityManager em;
    
    public void addCustomer(Customer customer){
        try{
            if(customer != null){
                em.persist(customer);
            } else {
                return;
            }
           
        } catch(Exception e) {
            logger.log(Level.INFO, "Error saving entity {0}" , e.toString());
        }
    }
    
    public List<Customer> getCustomers(){
        List<Customer> customers = null;
        try{
            customers = em.createNamedQuery("getCustomers").getResultList();
        } catch(Exception e) {
            logger.log(Level.INFO, "Error saving entity {0}" , e.toString());
        }
        
        return customers;
    }
}
