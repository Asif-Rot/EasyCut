package com.example.easycut.objects;

import com.example.easycut.Enum.UserType;

public class HairStylist extends User {

    public HairStylist(String _firstName, String _lastName, String _email, String _pass, String _phoneNumber, String _userID) {
        super(_firstName, _lastName, _email, _pass, _phoneNumber, _userID);
        _userType = UserType.HairStylist;
    }

    public void updateCatlog() {

    }

    public void cancelAppoitment() {

    }
}
