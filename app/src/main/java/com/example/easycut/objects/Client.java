package com.example.easycut.objects;

import com.example.easycut.Enum.UserType;

public class Client extends User {
    public Client(String _firstName, String _lastName, String _email, String _pass, String _phoneNumber, String _userID ) {
        super(_firstName, _lastName,_phoneNumber, _email, _pass, _userID);
        _userType = UserType.Client;
    }

    public void makeAppoitment() {
    }


    public void cancelAppoitment() {
    }



}
