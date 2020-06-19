/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diljeet.onetomany.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author diljeet
 */
@Entity
@Table(name = "customer")
@NamedQuery(
        name = "getCustomers",
        //query = "Select c.customerId, c.customerName, c.dateCustomerCreated, c.dateCustomerUpdated from Customer c order by c.customerId"
        query = "Select c from Customer c order by c.customerId desc"
)
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement
    private int customerId;

    @XmlElement
    private String customerName;

    @Temporal(TemporalType.TIMESTAMP)
    @XmlElement
    private Date dateCustomerCreated;
    
    @Temporal(TemporalType.TIMESTAMP)
    @XmlElement
    private Date dateCustomerUpdated;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer")
    @JsonbTransient    
    @XmlTransient
    private List<Order> orders = new ArrayList<>();

    public Customer() {
        dateCustomerCreated = new Date();
        //this.setDateCustomerCreated(dateCustomerCreated);
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
//        this.dateCustomerCreated = new Date();
            this.dateCustomerCreated = dateCustomerCreated;
    }

    public Date getDateCustomerUpdated() {
        return dateCustomerUpdated;
    }

    public void setDateCustomerUpdated(Date dateCustomerUpdated) {
        this.dateCustomerUpdated = dateCustomerUpdated;
    }  

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
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
