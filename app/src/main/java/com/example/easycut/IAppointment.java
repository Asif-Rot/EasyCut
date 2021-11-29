package com.example.easycut;

public interface IAppointment {

    public int getKey();
    public void setKey(String s);
    public int getType();
    public void setType(int i);
    public String getClientID();
    public void getClientName(String s);
    public String getDate();
    public void setDate(String s);
    public String getStartTime();
    public void setStartTime(int i);
    public String getEndTime();
    public void setEndTime(int i);
    public int getDuration();
}
