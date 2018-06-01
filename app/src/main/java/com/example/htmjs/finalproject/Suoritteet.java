package com.example.htmjs.finalproject;

public class Suoritteet {

    private int ID;
    private String suorite;
    private String yksikko;
    private String suoriteryhma;
    private String tyo_ID;


    public Suoritteet(int _id, String _suorite, String _yksikko, String _suoriteryhma, String _tyoID) {
        this.ID = _id;
        this.suorite = _suorite;
        this.yksikko = _yksikko;
        this.suoriteryhma = _suoriteryhma;
        this.tyo_ID = _tyoID;

    }

    public String getSuoriteryhma() {
        return suoriteryhma;
    }

    public void setSuoriteryhma(String suoriteryhma) {
        this.suoriteryhma = suoriteryhma;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSuorite() {
        return suorite;
    }

    public void setSuorite(String suorite) {
        this.suorite = suorite;
    }

    public String getYksikko() {
        return yksikko;
    }

    public void setYksikko(String yksikko) {
        this.yksikko = yksikko;
    }

    public String getTyo_ID() {
        return tyo_ID;
    }

    public void setTyo_ID(String tyo_ID) {
        this.tyo_ID = tyo_ID;
    }
}
