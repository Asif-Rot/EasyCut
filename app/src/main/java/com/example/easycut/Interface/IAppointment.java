package com.example.easycut.Interface;

public interface IAppointment {

    public String getKey();
    public void setKey(String i);
    public String getType();
    public void setType(String i);
    public String getClientID();
    public void setClientID(String s);
    public String getDate();
    public void setDate(String s);
    public String getStartTime();
    public void setStartTime(String i);
    public String getEndTime();
    public void setEndTime(String i);
    public int getDuration();
}
