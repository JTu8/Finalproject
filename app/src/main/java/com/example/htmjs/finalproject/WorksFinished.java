package com.example.htmjs.finalproject;

public class WorksFinished {

    private int ID;
    private String tyo_ID;
    private String kuvaus;
    private String pvm;
    private String tila;
    private String selitys;
    private String tunnit;
    private String maara;
    private String suorite;
    private String yksikko;



    public WorksFinished(int _id, String _tyo_ID, String _kuvaus, String _pvm, String _tila, String _selitys, String _tunnit, String _maara, String _suorite, String _yksikko) {

        this.ID = _id;
        this.tyo_ID = _tyo_ID;
        this.kuvaus = _kuvaus;
        this.pvm = _pvm;
        this.tila = _tila;
        this.selitys = _selitys;
        this.tunnit = _tunnit;
        this.maara = _maara;
        this.suorite = _suorite;
        this.yksikko = _yksikko;
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

    public String getMaara() {
        return maara;
    }

    public void setMaara(String maara) {
        this.maara = maara;
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
}
