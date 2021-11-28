package com.example.easycut;

public class HairStylist implements hairStylistInterface {
    final String _userTypeHairStylist = "HairStylist";
    private userInterface user;

    public HairStylist(String _firstName, String _lastName, String _email, String _pass, String _phoneNumber, String _userID) {
        user = new User(_firstName, _lastName, _email, _pass, _phoneNumber, _userID);
    }

    @Override
    public void updateCatlog() {

    }

    @Override
    public void cancelAppoitment() {

    }

    @Override
    public String getType() {
        return _userTypeHairStylist;
    }
}
