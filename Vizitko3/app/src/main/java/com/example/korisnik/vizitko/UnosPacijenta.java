package com.example.korisnik.vizitko;

public class UnosPacijenta {

    String pacijentId;
    String pacijentIme;
    String pacijentPrezime;
    String pacijentDatum;
    String pacijentVsina;
    String pacijentTezina;
    String pacijentOsiguranje;
    String pacijentBroj;

    public UnosPacijenta(){

    }
    public UnosPacijenta(String pacijentId, String pacijentIme, String pacijentPrezime, String pacijentDatum,
                         String pacijentVsina, String pacijentTezina, String pacijentOsiguranje, String pacijentBroj) {
        this.pacijentId = pacijentId;
        this.pacijentIme = pacijentIme;
        this.pacijentPrezime = pacijentPrezime;
        this.pacijentDatum = pacijentDatum;
        this.pacijentVsina = pacijentVsina;
        this.pacijentTezina = pacijentTezina;
        this.pacijentOsiguranje = pacijentOsiguranje;
        this.pacijentBroj = pacijentBroj;
    }

    public String getPacijentId() {
        return pacijentId;
    }

    public String getPacijentIme() {
        return pacijentIme;
    }

    public String getPacijentPrezime() {
        return pacijentPrezime;
    }

    public String getPacijentDatum() {
        return pacijentDatum;
    }

    public String getPacijentVsina() {
        return pacijentVsina;
    }

    public String getPacijentTezina() {
        return pacijentTezina;
    }

    public String getPacijentOsiguranje() {
        return pacijentOsiguranje;
    }

    public String getPacijentBroj() {
        return pacijentBroj;
    }
}
