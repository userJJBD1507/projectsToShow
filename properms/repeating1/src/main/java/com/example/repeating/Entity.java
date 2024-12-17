package com.example.repeating;


import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name = "posdb")
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "saved")
    private String saved;

    public Entity() {
    }

    public Entity(String email, String saved) {
        this.email = email;
        this.saved = saved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSaved() {
        return saved;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSaved(String saved) {
        this.saved = saved;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", saved='" + saved + '\'' +
                '}';
    }
}
