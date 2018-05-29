package com.example.htmjs.finalproject;

public class Users {

    private int ID;
    private String kayttajatunnus;
    private String nimi;
    private String lisatieto;

    public Users(int _id, String _kayttajatunnus, String _nimi, String _lisatieto) {

        this.ID = _id;
        this.kayttajatunnus = _kayttajatunnus;
        this.nimi = _nimi;
        this.lisatieto = _lisatieto;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getKayttajatunnus() {
        return kayttajatunnus;
    }

    public void setKayttajatunnus(String kayttajatunnus) {
        this.kayttajatunnus = kayttajatunnus;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getLisatieto() {
        return lisatieto;
    }

    public void setLisatieto(String lisatieto) {
        this.lisatieto = lisatieto;
    }
}
