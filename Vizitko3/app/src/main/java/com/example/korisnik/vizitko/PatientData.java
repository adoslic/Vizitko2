package com.example.korisnik.vizitko;

public class PatientData {
    String dataId;
    String datagTlak;
    String dataDTlak;
    String dataDijabetes;
    String dataPuls;
    String dataTemperatura;
    String dataDate;
    String dataURL;

    public PatientData() {

    }

    public PatientData(String dataId, String datagTlak, String dataDTlak, String dataDijabetes, String dataPuls, String dataTemperatura, String dataDate, String dataURL) {
        this.dataId = dataId;
        this.datagTlak = datagTlak;
        this.dataDTlak = dataDTlak;
        this.dataDijabetes = dataDijabetes;
        this.dataPuls = dataPuls;
        this.dataTemperatura = dataTemperatura;
        this.dataDate = dataDate;
        this.dataURL = dataURL;
    }

    public String getDataId() {
        return dataId;
    }

    public String getDatagTlak() {
        return datagTlak;
    }

    public String getDataDTlak() {
        return dataDTlak;
    }

    public String getDataDijabetes() {
        return dataDijabetes;
    }

    public String getDataPuls() {
        return dataPuls;
    }

    public String getDataTemperatura() {
        return dataTemperatura;
    }

    public String getDataDate() {
        return dataDate;
    }

    public String getDataURL() {
        return dataURL;
    }
}
