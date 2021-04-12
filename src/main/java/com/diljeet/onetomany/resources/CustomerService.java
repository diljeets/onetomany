/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diljeet.onetomany.resources;

import com.diljeet.onetomany.ejb.CustomerBean;
import com.diljeet.onetomany.entity.Customer;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.resource.spi.SecurityPermission;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.jboss.ejb3.annotation.RunAsPrincipal;
import org.jboss.ejb3.annotation.SecurityDomain;

/**
 *
 * @author diljeet
 */
@Stateless
@Path("/Customer")
//@SecurityDomain("other")
//@DeclareRoles({"Administrator" , "Manager"})
////@RunAs("Administrator")
////@RunAsPrincipal("Administrator")
@RolesAllowed("Administrator")
public class CustomerService {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private static final Logger logger = Logger.getLogger(CustomerBean.class.getCanonicalName());

    @PersistenceContext(name = "my_persistence_unit")
    EntityManager em;

    public CustomerService() {
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addCustomer(Customer customer) {

        int custId = 0;
        try {
            if (customer != null) {                    
                customer.setDateCustomerCreated(new Date());
                em.persist(customer);
                custId = customer.getCustomerId();
            } else {
                logger.info("Could not find Customer");
            }

        } catch (Exception e) {
            logger.log(Level.INFO, "Error saving entity {0}", e.toString());
        }

        return Response.created(URI.create("/" + custId)).build();
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @RolesAllowed("Administrator")
    public List<Customer> getCustomers() {
        List<Customer> customers = null;
        try {
            customers = em.createNamedQuery("getCustomers").getResultList();
        } catch (Exception e) {
            logger.log(Level.INFO, "Error saving entity {0}", e.toString());
        }

        return customers;
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateByCustomerId(@PathParam("id") int custId, Customer customer) {        
        //int custId = customer.getCustomerId();
        try {
            Customer existingCustomer = em.find(Customer.class, custId);
            if (existingCustomer != null) {
                existingCustomer.setCustomerName(customer.getCustomerName());
                existingCustomer.setDateCustomerUpdated(new Date());
            } else {
                logger.severe("Could not find Customer");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating customer {0}", e.toString());
        }

        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteCustomerById(@PathParam("id") int custId) {
        try {
            Customer existingCustomer = em.find(Customer.class, custId);
            if (existingCustomer != null) {
                em.remove(existingCustomer);
            } else {
                logger.info("Could not find Customer");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting customer {0}", e.toString());
        }

        return Response.ok().status(200).build();
    }
}
