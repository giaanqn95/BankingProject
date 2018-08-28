package com.example.e7440.bankingproject.module.model;

import com.google.gson.annotations.SerializedName;

public class Schedules {

    @SerializedName("id_customer")
    public String id_customer;

    @SerializedName("Name")
    public String name;

    @SerializedName("PhoneNumber")
    public String phoneNumber;

    @SerializedName("DateOfBirth")
    public String dateOfBirth;

    @SerializedName("Address")
    public String address;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("status")
    public String status;

    public String getName() {
        return name;
    }

    public Schedules(String name, String address, String avatar, String status) {
        this.name = name;
        this.address = address;
        this.avatar = avatar;
        this.status = status;
    }

    public Schedules setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Schedules setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public Schedules setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Schedules setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getId_customer() {
        return id_customer;
    }

    public Schedules setId_customer(String id_customer) {
        this.id_customer = id_customer;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Schedules setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Schedules setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }
}
