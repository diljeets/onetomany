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
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
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
@Stateless
//@SecurityDomain("other")
//@DeclareRoles({"Administrator" , "Manager"})
////@RunAs("Administrator")
//@RolesAllowed("Administrator")
public class CustomerBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private static final Logger logger = Logger.getLogger(CustomerBean.class.getCanonicalName());

    @Inject
    HttpServletRequest req;

    private Client client;

    public CustomerBean() {
    }

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
    }

    @PreDestroy
    private void clean() {
        client.close();
    }

//    @PersistenceContext(name = "my_persistence_unit")
//    EntityManager em;
    public void addCustomer(Customer customer) {
        if (customer == null) {
            logger.info("Could not find Customer");
        }

        Response response = client.target("http://localhost:8080/onetomany/webapi/Customer")
                .request(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .header("Authorization", req.getHeader("Authorization"))
                .post(Entity.entity(customer, MediaType.APPLICATION_XML), Response.class);

        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            logger.info("Customer inserted successfully");
        } else {
            logger.info("Problem inserting Customer");
        }
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = null;
        //try {
        customers = client.target("http://localhost:8080/onetomany/webapi/Customer")
                .path("all")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", req.getHeader("Authorization"))
                .accept(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Customer>>() {
                });
//        } catch (WebApplicationException e) {
//            logger.log(Level.SEVERE, e.toString());
//            e.getResponse().close();
//        }

        return customers;
    }

    public void updateByCustomerId(Customer customer) {
        logger.log(Level.SEVERE, "inside customerbean update method");
        int custId = customer.getCustomerId();
        String id = String.valueOf(custId);

        Response response = client.target("http://localhost:8080/onetomany/webapi/Customer")
                .path(id)
                .request(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .header("Authorization", req.getHeader("Authorization"))
                .put(Entity.entity(customer, MediaType.APPLICATION_XML), Response.class);

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            logger.log(Level.SEVERE, "Customer updated successfully");
//            logger.info("Customer updated successfully");
        } else {
            logger.log(Level.SEVERE, "Problem updating Customer");
//            logger.info("Problem updating Customer");
        }

    }

    public void deleteCustomerById(int custId) {
        String id = String.valueOf(custId);

        Response response = client.target("http://localhost:8080/onetomany/webapi/Customer")
                .path(id)
                .request(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .header("Authorization", req.getHeader("Authorization"))
                .delete();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            logger.info("Customer deleted successfully");
        } else {
            logger.info("Problem deleting Customer");
        }
    }

}
