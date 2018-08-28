package com.example.e7440.bankingproject.module.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
    private String username;

    @SerializedName("extension")
    private String extension;

    @SerializedName("issupervisor")
    private String issupervisor;

    @SerializedName("isadmin")
    private String isadmin;

    @SerializedName("agentname")
    private String agentname;

    @SerializedName("x_crm")
    private String x_crm;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getIssupervisor() {
        return issupervisor;
    }

    public User setIssupervisor(String issupervisor) {
        this.issupervisor = issupervisor;
        return this;
    }

    public String getIsadmin() {
        return isadmin;
    }

    public User setIsadmin(String isadmin) {
        this.isadmin = isadmin;
        return this;
    }

    public String getAgentname() {
        return agentname;
    }

    public User setAgentname(String agentname) {
        this.agentname = agentname;
        return this;
    }

    public String getX_crm() {
        return x_crm;
    }

    public User setX_crm(String x_crm) {
        this.x_crm = x_crm;
        return this;
    }
}
