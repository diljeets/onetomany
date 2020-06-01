/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diljeet.onetomany.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author diljeet
 */
@Entity
@Table(name = "customer")
@NamedQuery(
        name = "getCustomers",
        query = "Select c from Customer c order by c.customerId"
)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    
    private String customerName;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCustomerCreated;

    public Customer() {
        Date date = new Date();
        this.setDateCustomerCreated(date);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getDateCustomerCreated() {
        return dateCustomerCreated;
    }

    public void setDateCustomerCreated(Date dateCustomerCreated) {        
        this.dateCustomerCreated = dateCustomerCreated;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) customerId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if (this.customerId != other.customerId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.diljeet.onetomany.entity.Customer[ id=" + customerId + " ]";
    }
    
}
