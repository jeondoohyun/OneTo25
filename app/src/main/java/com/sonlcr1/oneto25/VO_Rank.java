package com.sonlcr1.oneto25;

public class VO_Rank {
    String email;
    int record;

    public VO_Rank(String email, int record) {
        this.email = email;
        this.record = record;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }
}
