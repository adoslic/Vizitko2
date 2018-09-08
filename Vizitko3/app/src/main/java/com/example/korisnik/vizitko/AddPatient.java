package com.example.korisnik.vizitko;

public class AddPatient {

    String pacijentId;
    String pacijentIme;
    String pacijentPrezime;
    String pacijentSpol;
    String pacijentDatum;
    String pacijentVisina;
    String pacijentTezina;
    String pacijentOsiguranje;
    String pacijentTelBroj;

    public AddPatient(){

    }
    public AddPatient(String pacijentId, String pacijentIme, String pacijentPrezime, String pacijentSpol, String pacijentDatum,
                         String pacijentVisina, String pacijentTezina, String pacijentOsiguranje, String pacijentTelBroj) {
        this.pacijentId = pacijentId;
        this.pacijentIme = pacijentIme;
        this.pacijentPrezime = pacijentPrezime;
        this.pacijentSpol = pacijentSpol;
        this.pacijentDatum = pacijentDatum;
        this.pacijentVisina = pacijentVisina;
        this.pacijentTezina = pacijentTezina;
        this.pacijentOsiguranje = pacijentOsiguranje;
        this.pacijentTelBroj = pacijentTelBroj;
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

    public String getpacijentSpol() { return pacijentSpol; }

    public String getPacijentDatum() {
        return pacijentDatum;
    }

    public String getPacijentVisina() {
        return pacijentVisina;
    }

    public String getPacijentTezina() {
        return pacijentTezina;
    }

    public String getPacijentOsiguranje() {
        return pacijentOsiguranje;
    }

    public String getPacijentBroj() {
        return pacijentTelBroj;
    }
}
