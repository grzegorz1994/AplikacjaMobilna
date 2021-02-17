package com.example.organizerrolnika;

public class Pole {

    private String nazwaPola;
    private String rodzajUprawy;
    private String powierzchniaPola;
    private String dataDodania;
    private String nawozOrganiczny;
    private String nawozAzotowy;
    private String nawozFosforowy;
    private String nawozPotasowy;
    private String nawozSiarkowyMagnezowy;
    private String opryskFungicydy;
    private String opryskInsektycydy;
    private String opryskHerbicydy;
    private String opryskRegulatoryWzrostu;
    private String opryskAtraktanty;
    private String opryskRepelenty;
    //private String nawozWapniowy;
    private String poleId;
    private String tytulNotatki;
    private String trescNotatki;

    public Pole(){}

    public Pole(String nazwaPola, String rodzajUprawy, String powierzchniaPola, String dataDodania, String poleId){
        this.nazwaPola = nazwaPola;
        this.rodzajUprawy = rodzajUprawy;
        this.powierzchniaPola = powierzchniaPola;
        this.dataDodania = dataDodania;
        this.poleId = poleId;
    }

    public Pole(String nawozAzotowy, String nawozFosforowy, String nawozPotasowy, String nawozSiarkowyMagnezowy){
        this.nawozAzotowy = nawozAzotowy;
        this.nawozFosforowy = nawozFosforowy;
        this.nawozPotasowy = nawozPotasowy;
        this.nawozSiarkowyMagnezowy = nawozSiarkowyMagnezowy;
        //this.nawozWapniowy = nawozWapniowy;
    }

    public Pole(String opryskFungicydy, String opryskInsektycydy, String opryskHerbicydy, String opryskRegulatoryWzrostu, String opryskAtraktanty, String opryskRepelenty) {
        this.opryskFungicydy = opryskFungicydy;
        this.opryskInsektycydy = opryskInsektycydy;
        this.opryskHerbicydy = opryskHerbicydy;
        this.opryskRegulatoryWzrostu = opryskRegulatoryWzrostu;
        this.opryskAtraktanty = opryskAtraktanty;
        this.opryskRepelenty = opryskRepelenty;
    }

    public Pole(String nawozOrganiczny){
        this.nawozOrganiczny = nawozOrganiczny;
    }

    public Pole(String tytulNotatki, String trescNotatki) {
        this.tytulNotatki = tytulNotatki;
        this.trescNotatki = trescNotatki;
    }

    public String getNazwaPola(){
        return nazwaPola;
    }

    public String getRodzajUprawy(){
        return rodzajUprawy;
    }

    public String getPowierzchniaPola(){
        return powierzchniaPola;
    }

    public String getDataDodania() {
        return dataDodania;
    }

    public String getNawozAzotowy() {
        return nawozAzotowy;
    }

    public String getNawozFosforowy() {
        return nawozFosforowy;
    }

    public String getNawozPotasowy() {
        return nawozPotasowy;
    }

    public String getNawozSiarkowyMagnezowy() {
        return nawozSiarkowyMagnezowy;
    }

    /*public String getNawozWapniowy() {
        return nawozWapniowy;
    }*/

    public String getPoleId() {
        return poleId;
    }

    public String getNawozOrganiczny() {
        return nawozOrganiczny;
    }

    public String getOpryskFungicydy() {
        return opryskFungicydy;
    }

    public String getOpryskInsektycydy() {
        return opryskInsektycydy;
    }

    public String getOpryskHerbicydy() {
        return opryskHerbicydy;
    }

    public String getOpryskRegulatoryWzrostu() {
        return opryskRegulatoryWzrostu;
    }

    public String getOpryskAtraktanty() {
        return opryskAtraktanty;
    }

    public String getOpryskRepelenty() {
        return opryskRepelenty;
    }

    public String getTytulNotatki() {
        return tytulNotatki;
    }

    public String getTrescNotatki() {
        return trescNotatki;
    }
}
