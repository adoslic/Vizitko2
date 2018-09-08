package com.example.korisnik.vizitko;

public class Account {

    String accountId;
    String accountIme;
    String accountPrezime;
    String accountSpol;
    String accountDatum;
    String accountBrojDiplome;
    String accountTelBroj;

    public Account (){

    }

    public Account(String accountId, String accountIme, String accountPrezime, String accountSpol, String accountDatum, String accountBrojDiplome, String accountTelBroj) {
        this.accountId = accountId;
        this.accountIme = accountIme;
        this.accountPrezime = accountPrezime;
        this.accountSpol = accountSpol;
        this.accountDatum = accountDatum;
        this.accountBrojDiplome = accountBrojDiplome;
        this.accountTelBroj = accountTelBroj;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountIme() {
        return accountIme;
    }

    public String getAccountPrezime() {
        return accountPrezime;
    }

    public String getAccountSpol() {
        return accountSpol;
    }

    public String getAccountDatum() {
        return accountDatum;
    }

    public String getAccountBrojDiplome() {
        return accountBrojDiplome;
    }

    public String getAccountTelBroj() {
        return accountTelBroj;
    }
}
