package com.example.hcliq_priya.model_admin;

public class ModelTreatmentHospitalStay {

    private String ipdDlySrlNo, ipdDlyDateDesc, ipdDlyPrcdr, ipdDlyTrtmt, ipdDlyDoctorNotes, dlyDetailCreatedByUserName, dlyDetailEditFlag, ipdOprSrlNo, ipdOperationName, ipdOperationStartTime, ipdOperationEndTime;

    public ModelTreatmentHospitalStay(String ipdDlySrlNo, String ipdDlyDateDesc, String ipdDlyPrcdr, String ipdDlyTrtmt, String ipdDlyDoctorNotes, String dlyDetailCreatedByUserName, String dlyDetailEditFlag, String ipdOprSrlNo, String ipdOperationName, String ipdOperationStartTime, String ipdOperationEndTime) {
        this.ipdDlySrlNo = ipdDlySrlNo;
        this.ipdDlyDateDesc = ipdDlyDateDesc;
        this.ipdDlyPrcdr = ipdDlyPrcdr;
        this.ipdDlyTrtmt = ipdDlyTrtmt;
        this.ipdDlyDoctorNotes = ipdDlyDoctorNotes;
        this.dlyDetailCreatedByUserName = dlyDetailCreatedByUserName;
        this.dlyDetailEditFlag = dlyDetailEditFlag;
        this.ipdOprSrlNo = ipdOprSrlNo;
        this.ipdOperationName = ipdOperationName;
        this.ipdOperationStartTime = ipdOperationStartTime;
        this.ipdOperationEndTime = ipdOperationEndTime;
    }

    public String getIpdDlySrlNo() {
        return ipdDlySrlNo;
    }

    public String getIpdDlyDateDesc() {
        return ipdDlyDateDesc;
    }

    public String getIpdDlyPrcdr() {
        return ipdDlyPrcdr;
    }

    public String getIpdDlyTrtmt() {
        return ipdDlyTrtmt;
    }

    public String getIpdDlyDoctorNotes() {
        return ipdDlyDoctorNotes;
    }

    public String getDlyDetailCreatedByUserName() {
        return dlyDetailCreatedByUserName;
    }

    public String getDlyDetailEditFlag() {
        return dlyDetailEditFlag;
    }

    public String getIpdOprSrlNo() {
        return ipdOprSrlNo;
    }

    public String getIpdOperationName() {
        return ipdOperationName;
    }

    public String getIpdOperationStartTime() {
        return ipdOperationStartTime;
    }

    public String getIpdOperationEndTime() {
        return ipdOperationEndTime;
    }
}
