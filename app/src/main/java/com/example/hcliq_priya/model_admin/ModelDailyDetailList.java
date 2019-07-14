package com.example.hcliq_priya.model_admin;

public class ModelDailyDetailList {

    private String treatment,date,updatedby,procedure;

    public ModelDailyDetailList(String procedure, String treatment,String date,String updatedby) {

        this.procedure = procedure;
        this.treatment = treatment;
        this.date = date;
        this.updatedby = updatedby;
        }

    public String getProcedure() {
        return procedure;
    }

    public String getTreatment() {
        return treatment;
    }
    public String getDate() {
            return date;
        }

     public String getUpdatedby() {
            return updatedby;
        }

}
