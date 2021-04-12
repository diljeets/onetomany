/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diljeet.onetomany.web;

import com.diljeet.onetomany.ejb.CustomerBean;
import com.diljeet.onetomany.entity.Customer;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
//import org.jboss.ejb3.annotation.SecurityDomain;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author diljeet
 */
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named(value = "customerController")
@SessionScoped
public class CustomerController implements Serializable{
    private static final Logger logger = Logger.getLogger(CustomerController.class.getCanonicalName());
    private static final long serialVersionUID = 2142383151318489373L;
    
    @EJB    
    private CustomerBean customerBean;
    
    private Customer customer;
    
    private List<Customer> customers;    
    
    @PostConstruct
    public void init() {
        customer = new Customer();         
        setCustomers(customerBean.getCustomers());
    }

    /**
     * Creates a new instance of onetomanyController
     */
    public CustomerController() {
               
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomers() {       
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }   
    
    public void addCustomer(Customer customer){
        customerBean.addCustomer(customer);        
        setCustomers(customerBean.getCustomers()); 
        clear();        
    }
    
    public void deleteCustomerById(int custId){
        customerBean.deleteCustomerById(custId);
        setCustomers(customerBean.getCustomers());
    }
    
     private void clear() {
        this.customer.setCustomerName(null);
        this.customer.setDateCustomerCreated(null);
    }
    
    public void onRowEdit(RowEditEvent<Customer> event) {
        logger.log(Level.SEVERE, "Updated name is {0}", event.getObject().getCustomerName());        
        customerBean.updateByCustomerId(event.getObject()); 
        setCustomers(customerBean.getCustomers());
    }

    public void onRowCancel(RowEditEvent<Customer> event) {
//        FacesMessage msg = new FacesMessage("Edit Cancelled", Long.toString(event.getObject().getId()));
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
//    public void onCellEdit(CellEditEvent event) {
//        Object oldCustName = event.getOldValue();
//        String newCustName = (String)event.getNewValue();        
//        DataTable table = (DataTable)event.getSource();
//        Customer customer = (Customer)table.getRowData();
//        int custId = customer.getCustomerId();
//        
//               
////         logger.log(Level.SEVERE, "CustId is {0} ", custId);
////         logger.log(Level.SEVERE, "newVlaue is {0} ", newValue);
////         logger.log(Level.SEVERE, "oldValue is {0} ", oldValue);
//        if(newCustName != null && !newCustName.equals(oldCustName)) {
//            customerBean.updateByCustomerId(newCustName, custId);
//            setCustomers(customerBean.getCustomers());
//            
////            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
////            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//    }

   
    
}
