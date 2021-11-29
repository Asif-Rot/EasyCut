package com.example.easycut;

public interface IAppointment {

    public int getKey();
    public void setKey(int i);
    public int getType();
    public void setType(int i);
    public String getClientID();
    public void getClientName(String s);
    public String getDate();
    public void setDate(String s);
    public String getStartTime();
    public void setStartTime(String i);
    public String getEndTime();
    public void setEndTime(String i);
    public int getDuration();
}
