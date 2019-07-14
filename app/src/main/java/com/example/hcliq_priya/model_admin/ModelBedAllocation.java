package com.example.hcliq_priya.model_admin;

public class ModelBedAllocation {

    private  String ipdNo,ipdFacilityId,facilityName,ipdFacilityUnitId,facilityUnitName,ipdFacilityUnitBedId,facilityBedDesc,ipdDetailStDateDesc,ipdDetailEndDateDesc,ipdBedDeleteFlag;

    public ModelBedAllocation(String ipdNo, String ipdFacilityId, String facilityName, String ipdFacilityUnitId, String facilityUnitName, String ipdFacilityUnitBedId, String facilityBedDesc, String ipdDetailStDateDesc, String ipdDetailEndDateDesc,String ipdBedDeleteFlag) {
        this.ipdNo = ipdNo;
        this.ipdFacilityId = ipdFacilityId;
        this.facilityName = facilityName;
        this.ipdFacilityUnitId = ipdFacilityUnitId;
        this.facilityUnitName = facilityUnitName;
        this.ipdFacilityUnitBedId = ipdFacilityUnitBedId;
        this.facilityBedDesc = facilityBedDesc;
        this.ipdDetailStDateDesc = ipdDetailStDateDesc;
        this.ipdDetailEndDateDesc = ipdDetailEndDateDesc;
        this.ipdBedDeleteFlag = ipdBedDeleteFlag;
    }

    public String getIpdBedDeleteFlag() {
        return ipdBedDeleteFlag;
    }

    public String getIpdNo() {
        return ipdNo;
    }

    public String getIpdFacilityId() {
        return ipdFacilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public String getIpdFacilityUnitId() {
        return ipdFacilityUnitId;
    }

    public String getFacilityUnitName() {
        return facilityUnitName;
    }

    public String getIpdFacilityUnitBedId() {
        return ipdFacilityUnitBedId;
    }

    public String getFacilityBedDesc() {
        return facilityBedDesc;
    }

    public String getIpdDetailStDateDesc() {
        return ipdDetailStDateDesc;
    }

    public String getIpdDetailEndDateDesc() {
        return ipdDetailEndDateDesc;
    }
}
