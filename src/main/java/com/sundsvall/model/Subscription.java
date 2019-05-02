package com.sundsvall.model;

public class Subscription {

    private long Id;
    private String MSISDN;

    public Subscription(long id, String MSISDN) {
        Id = id;
        this.MSISDN = MSISDN;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getMSISDN() {
        return MSISDN;
    }

    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }


    @Override
    public String toString() {
        return "Subscription{" + "Id=" + Id + ", MSISDN=" + MSISDN + '}';
    }
  
}
