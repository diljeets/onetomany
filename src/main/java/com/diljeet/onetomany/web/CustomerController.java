/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diljeet.onetomany.web;

import com.diljeet.onetomany.ejb.CustomerBean;
import com.diljeet.onetomany.entity.Customer;
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
@Named(value = "customerController")
@SessionScoped
public class CustomerController implements Serializable{
    
    private static final long serialVersionUID = 2142383151318489373L;
    
    @EJB
    private CustomerBean customerBean;
    
    private Customer customer;
    
    private List<Customer> customers;
    
    private int currentCustomer;

    /**
     * Creates a new instance of onetomanyController
     */
    public CustomerController() {
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

    public int getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(int currentCustomer) {
        this.currentCustomer = currentCustomer;
    }
    
    
    
    
}
