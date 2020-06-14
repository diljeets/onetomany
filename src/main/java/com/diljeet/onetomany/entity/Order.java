/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diljeet.onetomany.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author diljeet
 */
@Entity
@Table(name = "customer_order")
@NamedQueries({
    @NamedQuery(
        name = "fetchAllOrders",
        query = "SELECT o FROM Order o"
    ),
    @NamedQuery(
        name = "fetchOrdersById",
        query = "SELECT DISTINCT o FROM Order o, IN (o.customer.orders) c where c.customer.customerId = :customerId"
    )
})
//@NamedQuery(
//        name = "getOrders",
//        query = "SELECT o FROM Order o ORDER BY o.id"
//)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    
    //private int customerId;
    
    private String item;
    
    private String quantity;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderCreated;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderUpdated;
    
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_CUSTOMERID")
    private Customer customer;

    public Order() {
        orderCreated = new Date();        
    }    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }    
    
//    public int getCustomerId() {
//        return customerId;
//    }
//
//    public void setCustomerId(int customerId) {
//        this.customerId = customerId;
//    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Date getOrderCreated() {
        return orderCreated;
    }

    public void setOrderCreated(Date orderCreated) {
        //this.orderCreated = new Date();
        this.orderCreated = orderCreated;
    }

    public Date getOrderUpdated() {
        return orderUpdated;
    }

    public void setOrderUpdated(Date orderUpdated) {
        this.orderUpdated = orderUpdated;
    }    

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
   
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.diljeet.onetomany.entity.Order[ id=" + id + " ]";
    }
    
}
