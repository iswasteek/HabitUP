package com.fsf.habitup.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "trans_id", nullable = false, unique = true)
    private String transId;

    @Column(name = "amount", nullable = true, unique = true)
    private int amount;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false, unique = true)
    private User userId;

    @OneToOne
    @JoinColumn(name = "doctorId", nullable = false, unique = true)
    private Doctor doctor;

    @Column(name = "transactionType ", nullable = false, unique = false)
    private String transactionType;

    @Column(name = "paymentMethod", nullable = false, unique = false)
    private String paymentMethod;

    @Column(name = "transactionStatus", nullable = false, unique = false)
    private String transactionStatus;

    @Column(name = "createdAt", nullable = false, unique = false)
    private Date createdAt;

    @Column(name = "updatedAt", nullable = false, unique = false)
    private Date updatedAt;

    @Column(name = "paymentReferenceId", nullable = false, unique = true)
    private String paymentReferenceId;

    @Column(name = "receiptUrl", nullable = false, unique = true)
    private String receiptUrl;

    /**
     * @return String return the transId
     */
    public String getTransId() {
        return transId;
    }

    /**
     * @param transId the transId to set
     */
    public void setTransId(String transId) {
        this.transId = transId;
    }

    /**
     * @return int return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return User return the userId
     */
    public User getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(User userId) {
        this.userId = userId;
    }

    /**
     * @return Doctor return the doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * @param doctor the doctor to set
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * @return String return the transactionType
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
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
     * @return String return the transactionStatus
     */
    public String getTransactionStatus() {
        return transactionStatus;
    }

    /**
     * @param transactionStatus the transactionStatus to set
     */
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
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
     * @return String return the paymentReferenceId
     */
    public String getPaymentReferenceId() {
        return paymentReferenceId;
    }

    /**
     * @param paymentReferenceId the paymentReferenceId to set
     */
    public void setPaymentReferenceId(String paymentReferenceId) {
        this.paymentReferenceId = paymentReferenceId;
    }

    /**
     * @return String return the receiptUrl
     */
    public String getReceiptUrl() {
        return receiptUrl;
    }

    /**
     * @param receiptUrl the receiptUrl to set
     */
    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

}
