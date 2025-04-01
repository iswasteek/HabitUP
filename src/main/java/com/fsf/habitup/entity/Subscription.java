package com.fsf.habitup.entity;

import java.util.Date;

import com.fsf.habitup.Enums.SubscriptionType;
import jakarta.persistence.*;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriptionId", nullable = false, unique = true)
    private Long subscription_Id;

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

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "subscriptionType", nullable = false)
    private SubscriptionType subscriptionType;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRenewalStatus() {
        return renewalStatus;
    }

    public void setRenewalStatus(String renewalStatus) {
        this.renewalStatus = renewalStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSubscription_Id() {
        return subscription_Id;
    }

    public void setSubscription_Id(Long subscription_Id) {
        this.subscription_Id = subscription_Id;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
