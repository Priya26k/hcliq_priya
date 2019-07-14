package com.example.hcliq_priya.model_admin;

public class ModelIpdDiagnosisReport {
    public String ipdSrlNo,ipdRptSrlNo,ipdRptName,ipdRptFilePath,ipdRptCreationDate,ipdRptCreatedByUser,ipdRptType,ipdRptEditFlag;


    public ModelIpdDiagnosisReport(String ipdSrlNo, String ipdRptSrlNo, String ipdRptName, String ipdRptFilePath, String ipdRptType, String ipdRptCreationDate, String ipdRptCreatedByUser, String ipdRptEditFlag) {
        this.ipdSrlNo = ipdSrlNo;
        this.ipdRptSrlNo = ipdRptSrlNo;
        this.ipdRptName = ipdRptName;
        this.ipdRptFilePath = ipdRptFilePath;
        this.ipdRptCreationDate = ipdRptCreationDate;
        this.ipdRptCreatedByUser = ipdRptCreatedByUser;
        this.ipdRptType = ipdRptType;
        this.ipdRptEditFlag = ipdRptEditFlag;
    }

    public String getPost_operation() {
        return ipdRptName;
    }

    public String getPre_operation() {
        return ipdRptFilePath;
    }

    public String getIpdSrlNo() {
        return ipdSrlNo;
    }

    public String getIpdRptSrlNo() {
        return ipdRptSrlNo;
    }

    public String getIpdRptName() {
        return ipdRptName;
    }

    public String getIpdRptFilePath() {
        return ipdRptFilePath;
    }

    public String getIpdRptCreationDate() {
        return ipdRptCreationDate;
    }

    public String getIpdRptCreatedByUser() {
        return ipdRptCreatedByUser;
    }

    public String getIpdRptType() {
        return ipdRptType;
    }

    public String getIpdRptEditFlag() {
        return ipdRptEditFlag;
    }

    public void setIpdSrlNo(String ipdSrlNo) {
        this.ipdSrlNo = ipdSrlNo;
    }

    public void setIpdRptSrlNo(String ipdRptSrlNo) {
        this.ipdRptSrlNo = ipdRptSrlNo;
    }

    public void setIpdRptName(String ipdRptName) {
        this.ipdRptName = ipdRptName;
    }

    public void setIpdRptFilePath(String ipdRptFilePath) {
        this.ipdRptFilePath = ipdRptFilePath;
    }

    public void setIpdRptCreationDate(String ipdRptCreationDate) {
        this.ipdRptCreationDate = ipdRptCreationDate;
    }

    public void setIpdRptCreatedByUser(String ipdRptCreatedByUser) {
        this.ipdRptCreatedByUser = ipdRptCreatedByUser;
    }

    public void setIpdRptType(String ipdRptType) {
        this.ipdRptType = ipdRptType;
    }

    public void setIpdRptEditFlag(String ipdRptEditFlag) {
        this.ipdRptEditFlag = ipdRptEditFlag;
    }

}
