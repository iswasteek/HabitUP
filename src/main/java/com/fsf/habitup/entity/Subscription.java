package com.fsf.habitup.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriptionId", nullable = false, unique = true)
    private Long subscrip_Id;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true)
    private User user;

    @Column(name = "planName", nullable = false, unique = false)
    private String planName;

    @Column(name = "price", nullable = false, unique = false)
    private int price;

    @Column(name = "startDate", nullable = false, unique = false)
    private Date startDate;

    @Column(name = "endDate", nullable = false, unique = false)
    private Date endDate;

    @Column(name = "paymentMethod", nullable = false, unique = false)
    private String paymentMethod;

    @Column(name = "paymentStatus", nullable = false, unique = false)
    private String paymentStatus;

    @Column(name = "renewalStatus", nullable = false, unique = false)
    private String renewalStatus;

    @Column(name = "createdAt", nullable = false, unique = false)
    private Date createdAt;

    @Column(name = "updatedAt", nullable = false, unique = false)
    private Date updatedAt;

    @Column(name = "status", nullable = false, unique = false)
    private String status;

    /**
     * @return Long return the subscrip_Id
     */
    public Long getSubscrip_Id() {
        return subscrip_Id;
    }

    /**
     * @param subscrip_Id the subscrip_Id to set
     */
    public void setSubscrip_Id(Long subscrip_Id) {
        this.subscrip_Id = subscrip_Id;
    }

    /**
     * @return User return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return String return the planName
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * @param planName the planName to set
     */
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    /**
     * @return int return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return Date return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return Date return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return String return the paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * @return String return the paymentStatus
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * @param paymentStatus the paymentStatus to set
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * @return String return the renewalStatus
     */
    public String getRenewalStatus() {
        return renewalStatus;
    }

    /**
     * @param renewalStatus the renewalStatus to set
     */
    public void setRenewalStatus(String renewalStatus) {
        this.renewalStatus = renewalStatus;
    }

    /**
     * @return Date return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return Date return the updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return String return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
