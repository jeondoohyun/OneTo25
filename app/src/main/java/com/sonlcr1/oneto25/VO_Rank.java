package com.sonlcr1.oneto25;

public class VO_Rank {
    String email;
    int int_record;
    String st_record;


    public VO_Rank(String email, int record) {
        this.email = email;
        this.int_record = record;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRecord() {
        return int_record;
    }

    public void setRecord(int record) {
        this.int_record = record;
    }
}
