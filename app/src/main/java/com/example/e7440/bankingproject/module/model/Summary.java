package com.example.e7440.bankingproject.module.model;

import com.google.gson.annotations.SerializedName;

public class Summary {

    @SerializedName("waiting")
    public int waiting;
    @SerializedName("in_progress")
    public int in_progress;
    @SerializedName("done")
    public int done;

    public int getWaiting() {
        return waiting;
    }

    public Summary setWaiting(int waiting) {
        this.waiting = waiting;
        return this;
    }

    public int getIn_progress() {
        return in_progress;
    }

    public Summary setIn_progress(int in_progress) {
        this.in_progress = in_progress;
        return this;
    }

    public int getDone() {
        return done;
    }

    public Summary setDone(int done) {
        this.done = done;
        return this;
    }
}
