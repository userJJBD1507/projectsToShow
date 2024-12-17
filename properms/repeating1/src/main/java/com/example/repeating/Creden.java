package com.example.repeating;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
@Entity
@Table(name = "creden_table")
public class Creden {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "messageId")
    private String messageId;
    @Column(name = "productId")
    private String productId;

    public Creden() {
    }

    public Creden(String messageId, String productId) {
        this.messageId = messageId;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Creden{" +
                "id=" + id +
                ", messageId='" + messageId + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}