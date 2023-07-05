package com.lvu2code.springboot.demo.mycoolapp.entity;

public class StudentData {

    private String firstName;
    private String lastName;

    public StudentData() {
    }

    public StudentData(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
