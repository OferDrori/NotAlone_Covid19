package com.example.notalone_covid19;



public class User {
    private String fullName;
    private String email;
    private String id;


    public User() {
    }

    public User(String fullName, String email, String id) {
        this.fullName = fullName;
        this.email = email;
        this.id = id;

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    }

