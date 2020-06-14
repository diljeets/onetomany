/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diljeet.onetomany.ejb;

import com.diljeet.onetomany.entity.Customer;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author diljeet
 */
@Named
@Stateful
public class CustomerBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private static final Logger logger = Logger.getLogger(CustomerBean.class.getCanonicalName());
    
    private Client client;
    
    @PostConstruct
    private void init(){
        client = ClientBuilder.newClient();
    }
    
    @PreDestroy
    private void clean(){
        client.close();
    }
    
//    @PersistenceContext(name = "my_persistence_unit")
//    EntityManager em;
    
    public void addCustomer(Customer customer){
        if(customer == null){
            logger.info("Could not find Customer");
        }
        
        Response response = client.target("http://localhost:9090/onetomany-1.0/webapi/Customer")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(customer, MediaType.APPLICATION_JSON), Response.class);
        
        if(response.getStatus() == Response.Status.CREATED.getStatusCode()){
            logger.info("Customer inserted successfully");
        } else {
            logger.info("Problem inserting Customer");
        }
    }
    
    public List<Customer> getCustomers(){
        
        List<Customer> customers = client.target("http://localhost:9090/onetomany-1.0/webapi/Customer")
                .path("all")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Customer>>(){});        
     
        return customers;
    }    
    
    public void updateByCustomerId(Customer customer){
        int custId = customer.getCustomerId();
        String id = String.valueOf(custId);
        
        Response response = client.target("http://localhost:9090/onetomany-1.0/webapi/Customer")
                .path(id)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(customer, MediaType.APPLICATION_JSON), Response.class);
        
        if(response.getStatus() == Response.Status.OK.getStatusCode()){
            logger.info("Customer updated successfully");
        } else {
            logger.info("Problem updating Customer");
        }
      
    }

    public void deleteCustomerById(int custId) {
        String id = String.valueOf(custId);
        
        Response response = client.target("http://localhost:9090/onetomany-1.0/webapi/Customer")
                .path(id)
                .request(MediaType.APPLICATION_JSON)
                .delete();
        
        if(response.getStatus() == Response.Status.OK.getStatusCode()){
            logger.info("Customer deleted successfully");
        } else {
            logger.info("Problem deleting Customer");
        }
    }
    
     
    
}
