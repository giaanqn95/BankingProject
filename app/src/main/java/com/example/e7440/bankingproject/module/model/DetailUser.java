package com.example.e7440.bankingproject.module.model;

import com.google.gson.annotations.SerializedName;

public class DetailUser {
    @SerializedName("IDCard")
    private String iDCard;

    @SerializedName("DateOfIssue")
    private String dateOfIssue;

    @SerializedName("PlaceOfIssue")
    private String placeOfIssue;

    @SerializedName("Nationality")
    private String nationality;

    @SerializedName("ResidenceStatus")
    private String residenceStatus;

    @SerializedName("MarialStatus")
    private String marialStatus;

    @SerializedName("Occupation")
    private String occupation;

    @SerializedName("Industry")
    private String industry;

    @SerializedName("Position")
    private String position;

    @SerializedName("MonthlyIncome")
    private String monthlyIncome;

    public String getiDCard() {
        return iDCard;
    }

    public DetailUser setiDCard(String iDCard) {
        this.iDCard = iDCard;
        return this;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public DetailUser setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
        return this;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public DetailUser setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
        return this;
    }

    public String getNationality() {
        return nationality;
    }

    public DetailUser setNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public String getResidenceStatus() {
        return residenceStatus;
    }

    public DetailUser setResidenceStatus(String residenceStatus) {
        this.residenceStatus = residenceStatus;
        return this;
    }

    public String getMarialStatus() {
        return marialStatus;
    }

    public DetailUser setMarialStatus(String marialStatus) {
        this.marialStatus = marialStatus;
        return this;
    }

    public String getOccupation() {
        return occupation;
    }

    public DetailUser setOccupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public String getIndustry() {
        return industry;
    }

    public DetailUser setIndustry(String industry) {
        this.industry = industry;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public DetailUser setPosition(String position) {
        this.position = position;
        return this;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public DetailUser setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
        return this;
    }
}
