package com.example.htmjs.finalproject;

public class Works {

    private int ID;
    private String tyo_ID;
    private String kuvaus;
    private String pvm;
    private String tila;
    private String userID;
    private String selitys;
    private String tunnit;



    public Works(int _id, String _tyo_ID, String _kuvaus, String _pvm, String _tila, String _userID, String _selitys, String _tunnit) {

        this.ID = _id;
        this.tyo_ID = _tyo_ID;
        this.kuvaus = _kuvaus;
        this.pvm = _pvm;
        this.tila = _tila;
        this.userID = _userID;
        this.selitys = _selitys;
        this.tunnit = _tunnit;
    }

    public String getSelitys() {
        return selitys;
    }

    public void setSelitys(String selitys) {
        this.selitys = selitys;
    }

    public String getTunnit() {
        return tunnit;
    }

    public void setTunnit(String tunnit) {
        this.tunnit = tunnit;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTyo_ID() {
        return tyo_ID;
    }

    public void setTyo_ID(String tyo_ID) {
        this.tyo_ID = tyo_ID;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public String getPvm() {
        return pvm;
    }

    public void setPvm(String pvm) {
        this.pvm = pvm;
    }

    public String getTila() {
        return tila;
    }

    public void setTila(String tila) {
        this.tila = tila;
    }
}
