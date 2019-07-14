package com.example.hcliq_priya.model_admin;

public class ModelPreOperationReport {
    private String rptName,date,updatedby;

    public ModelPreOperationReport(String rptName, String date,String updatedby) {
        this.rptName = rptName;
        this.date = date;
        this.updatedby = updatedby;
    }

    public String getRptName() {
        return rptName;
    }

    public String getDate() {
        return date;
    }

    public String getUpdatedby() {
        return updatedby;
    }
}
