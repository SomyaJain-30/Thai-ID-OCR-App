package com.example.thaiidocr;

public class IDModel {
    private String firstName, lastName, dob,doi,doe,identification;
    public void IDModel(){

    }

    public void IDModel(String firstName, String lastName, String dob, String doi, String doe, String identification){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.doi = doi;
        this.doe = doe;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getDoe() {
        return doe;
    }

    public void setDoe(String doe) {
        this.doe = doe;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }
}
