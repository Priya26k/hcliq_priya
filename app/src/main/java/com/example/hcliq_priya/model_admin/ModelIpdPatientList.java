package com.example.hcliq_priya.model_admin;

public class ModelIpdPatientList {
    private String ipdSrlNo,ipdNo,hospitalPatientId,patientName,doctorName,ipdStDateDesc,ipdEndDateDesc,facilityName,facilityUnitName,facilityBedDesc,patientPaymentStatus,ipdClStatus,ipdCompletedStatus,ipdDischargeType,ipdDischargeTypeDesc,patientImgPath,isOprtvTrtmtFlag;

    public ModelIpdPatientList(String ipdSrlNo, String ipdNo, String hospitalPatientId,String patientName, String doctorName, String ipdStDateDesc, String ipdEndDateDesc, String facilityName, String facilityUnitName, String facilityBedDesc, String patientPaymentStatus, String ipdClStatus, String ipdCompletedStatus, String ipdDischargeType, String ipdDischargeTypeDesc,String patientImgPath,String isOprtvTrtmtFlag){
         this.ipdSrlNo = ipdSrlNo;
         this.ipdNo = ipdNo;
         this.hospitalPatientId = hospitalPatientId;
         this.patientName=patientName;
         this.doctorName = doctorName;
         this.ipdStDateDesc = ipdStDateDesc;
         this.ipdEndDateDesc = ipdEndDateDesc;
         this.facilityName = facilityName;
         this.facilityUnitName = facilityUnitName;
         this.facilityBedDesc = facilityBedDesc;
         this.patientPaymentStatus=patientPaymentStatus;
         this.ipdClStatus=ipdClStatus;
         this.ipdCompletedStatus = ipdCompletedStatus;
         this.ipdDischargeType = ipdDischargeType;
         this.ipdDischargeTypeDesc = ipdDischargeTypeDesc;
         this.patientImgPath = patientImgPath;
         this.isOprtvTrtmtFlag = isOprtvTrtmtFlag;
   }

    public String getIpdSrlNo() {
        return ipdSrlNo;
    }

    public String getIpdNo() {
        return ipdNo;
    }

    public String getHospitalPatientId() {
        return hospitalPatientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getIpdStDateDesc() {
        return ipdStDateDesc;
    }

    public String getIpdEndDateDesc() {
        return ipdEndDateDesc;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public String getFacilityUnitName() {
        return facilityUnitName;
    }

    public String getFacilityBedDesc() {
        return facilityBedDesc;
    }

    public String getPatientPaymentStatus() {
        return patientPaymentStatus;
    }

    public String getIpdClStatus() {
        return ipdClStatus;
    }

    public String getIpdCompletedStatus() {
        return ipdCompletedStatus;
    }

    public String getIpdDischargeType() {
        return ipdDischargeType;
    }

    public String getIpdDischargeTypeDesc() {
        return ipdDischargeTypeDesc;
    }

    public String getPatientImgPath() {
        return patientImgPath;
    }

    public String getIsOprtvTrtmtFlag() {
        return isOprtvTrtmtFlag;
    }

}
