package com.sonlcr1.oneto25;

public class VO_Rank {
    String email;
    String record;

    public VO_Rank(String email, String record) {
        this.email = email;
        this.record = record;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
