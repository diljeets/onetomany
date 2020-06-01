/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diljeet.onetomany.web;

import com.diljeet.onetomany.ejb.CustomerBean;
import com.diljeet.onetomany.entity.Customer;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author diljeet
 */
@Named(value = "onetomanyController")
@RequestScoped
public class OnetomanyController {
    
    @EJB
    private CustomerBean customerBean;
    
    private Customer customer;
    
    private List<Customer> customers;

    /**
     * Creates a new instance of onetomanyController
     */
    public OnetomanyController() {
        customer = new Customer();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomers() {
        return customerBean.getCustomers();
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    
    
}
