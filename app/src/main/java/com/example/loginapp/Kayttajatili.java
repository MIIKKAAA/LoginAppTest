package com.example.loginapp;

public class Kayttajatili {
    public static final String SARAKE_ETUNIMI = "etunimi";
    public static final String SARAKE_SUKUNIMI = "sukunimi";
    public static final String SARAKE_IKA = "ika";
    public static final String SARAKE_TUNNUS = "tunnus";
    public static final String SARAKE_SALASANA = "salasana";
    public static final String SARAKE_SAHKOPOSTI = "sahkoposti";
    public static final String SARAKE_SALT = "salt";
    public static final String TAULUN_NIMI = "Kayttajatilit";

    private String etunimi;
    private String sukunimi;
    private int ika;
    private String tunnus;
    private String salasana;
    private String sahkoposti;
    private String salt;
    public static final String LUO_TAULU = "CREATE TABLE " + TAULUN_NIMI + "("  + SARAKE_ETUNIMI +
            " TEXT, " + SARAKE_SUKUNIMI + " TEXT, " + SARAKE_IKA + " INTEGER, " + SARAKE_SAHKOPOSTI +
            " TEXT, " + SARAKE_TUNNUS + " TEXT PRIMARY KEY, " + SARAKE_SALASANA + " TEXT, " + SARAKE_SALT + ")";

    public Kayttajatili(){
    }

    public Kayttajatili(String enimi, String snimi, String sposti, int ika, String tunnus, String salasana, String salt){
        this.etunimi = enimi;
        this.sukunimi = snimi;
        this.sahkoposti = sposti;
        this.ika = ika;
        this.tunnus = tunnus;
        this.salasana = salasana;
        this.salt = salt;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public String getSahkoposti() {
        return sahkoposti;
    }

    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti = sahkoposti;
    }

    public int getIka() {
        return ika;
    }

    public void setIka(int ika) {
        this.ika = ika;
    }

    public String getTunnus() {
        return tunnus;
    }

    public void setTunnus(String tunnus) {
        this.tunnus = tunnus;
    }

    public String getSalasana() {
        return salasana;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
