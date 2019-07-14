package com.example.hcliq_priya.model_admin;

public class ModelDoctorSelection {

    private String doctorId,doctorName,doctorImagePath,specialistName,doctorConsultationFee, doctorSelectionFlag;

    public ModelDoctorSelection(String doctorId, String doctorName, String doctorImagePath, String specialistName,  String doctorConsultationFee, String doctorSelectionFlag) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorImagePath = doctorImagePath;
        this.specialistName = specialistName;
        this.doctorConsultationFee = doctorConsultationFee;
        this.doctorSelectionFlag = doctorSelectionFlag;
    }

    public String getDoctorSelectionFlag() {
        return doctorSelectionFlag;
    }

    public void setDoctorSelectionFlag(String doctorSelectionFlag) {
        this.doctorSelectionFlag = doctorSelectionFlag;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorImagePath() {
        return doctorImagePath;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public String getDoctorConsultationFee() {
        return doctorConsultationFee;
    }
}
