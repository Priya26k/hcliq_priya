package com.example.hcliq_priya.service;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("api/filterinfo/getFilterInfo/NULL/NUll/NULL/{opr}")
    Call<ResponseBody> getFilterInfo(@Path("opr")String opr);

    //Facility Spinner
   // http://testapi.wcss.co.in/api/filterinfo/getIPDFacilityFilter/kH4J3RXsw5cBMKvpLEwTDUCVi/25/6
    @GET("api/filterinfo/getIPDFacilityFilter/{authKey}/{userId}/{hospitalCode}")
    Call<ResponseBody> getIPDFacilityFilter(@Path("authKey")String authKey,
                                            @Path("userId")String userId,
                                            @Path("hospitalCode")String hospitalCode);

    //Unit Spinner
   //http://testapi.wcss.co.in/api/filterinfo/getIPDFacilityUnitFIlter/kH4J3RXsw5cBMKvpLEwTDUCVi/25/6/5
    @GET ("api/filterinfo/getIPDFacilityUnitFIlter/{authKey}/{userId}/{hospitalCode}/{facilityId}")
    Call<ResponseBody> getIPDFacilityUnitFIlter(@Path("authKey")String authKey,
                                                @Path("userId")String userId,
                                                @Path("hospitalCode")String hospitalCode,
                                                @Path("facilityId")String facilityId);

    //Patient IPD Profile
    //http://testapi.wcss.co.in/api/hospitalTreatment/getPatientIPDProfile/kH4J3RXsw5cBMKvpLEwTDUCVi/25/6/41
    @GET("http://testapi.wcss.co.in/api/hospitalTreatment/getPatientIPDProfile/{authKey}/{userId}/{hospitalCode}/{ipdSrlNo}")
    Call<ResponseBody>getPatientIPDProfile(@Path("authKey")String authKey,
                                           @Path("userId")String userId,
                                           @Path("hospitalCode")String hospitalCode,
                                           @Path("ipdSrlNo")String ipdSrlNo);
    //Patient IPD List
    //http://testapi.wcss.co.in/api/ePatientMaster/getPatientList
    @FormUrlEncoded
    @POST("/api/hospitalTreatment/getHospitalPatientListIPD")
    Call<ResponseBody> getHospitalPatientListIPD(@Field("authKey")String authKey,
                                         @Field("userId")String userId,
                                         @Field("hospitalCode")String hospitalCode,
                                         @Field("doctorId")String doctorId,
                                         @Field("showAllFlag")String showAllFlag,
                                         @Field("fromDate")String fromDate,
                                         @Field("toDate")String toDate);

    //doctor selection spinner
    //http://testapi.wcss.co.in/api/eDoctorListAndSchedule/eGetDoctorList/{authKey}/{hospitalCode}
    @GET("api/eDoctorListAndSchedule/eGetDoctorList/{authKey}/{hospitalCode}")
    Call<ResponseBody> eGetDoctorList(@Path("authKey")String authKey,
                                      @Path("hospitalCode")String hospitalCode);

    //Consent Letter Master
    //http://testapi.wcss.co.in/api/eIPDService/getConsentLetterTemplate/kH4J3RXsw5cBMKvpLEwTDUCVi/25/6
    @GET("api/eIPDService/getConsentLetterTemplate/{authKey}/{userId}/{hospitalCode}")
    Call<ResponseBody>getConsentLetterTemplate(@Path("authKey")String authKey,
                                               @Path("userId")String userId,
                                               @Path("hospitalCode")String hospitalCode);

    //http://testapi.wcss.co.in/api/eIPDService/createEditConsentLetterTemplate
    @FormUrlEncoded
    @POST("api/eIPDService/createEditConsentLetterTemplate")
    Call<ResponseBody> createEditConsentLetterTemplate( @Field("authkey") String authkey,
                                                        @Field("userId") String userId,
                                                        @Field("hospitalCode") String hospitalCode,
                                                        @Field("heading1") String heading1,
                                                        @Field("heading2") String heading2,
                                                        @Field("para1") String para1,
                                                        @Field("para2") String para2,
                                                        @Field("para3") String para3,
                                                        @Field("para4") String para4,
                                                        @Field("witnessReq") String witnessReq,
                                                        @Field("witnessDclrn") String witnessDclrn,
                                                        @Field("witness2Req") String witness2Req,
                                                        @Field("docReq") String docReq,
                                                        @Field("docDclrn") String docDclrn,
                                                        @Field("validFrom") String validFrom);

    //manage IPD Diagnosis
    // http://testapi.wcss.co.in/api/hospitalTreatment/managePatientIPDDiagnosisReport
    @POST("api/hospitalTreatment/managePatientIPDDiagnosisReport")
    Call<JsonObject> managePatientIPDDiagnosisReport(@Body JsonObject jsonObject);

    // http://testapi.wcss.co.in/api/hospitalTreatment/getPatientIPDDiagnosisReport/kH4J3RXsw5cBMKvpLEwTDUCVi/25/6/41

    @GET("api/hospitalTreatment/getPatientIPDDiagnosisReport/{authKey}/{userId}/{hospitalCode}/{ipdSrlNo}")
    Call<ResponseBody>getPatientIPDDiagnosisReport(  @Path("authKey")String authKey,
                                                     @Path("userId")String userId,
                                                     @Path("hospitalCode")String hospitalCode,
                                                     @Path("ipdSrlNo")String ipdSrlNo);
    //operation Schedule
    //http://testapi.wcss.co.in/api/hospitalTreatment/getOperationSchedule/{authKey}/{userId}/{hospitalCode}/{ipdSrlNo}

    @GET("api/hospitalTreatment/getOperationSchedule/{authKey}/{userId}/{hospitalCode}/{ipdSrlNo}")
    Call<ResponseBody>getOperationSchedule( @Path("authKey")String authKey,
                                            @Path("userId")String userId,
                                            @Path("hospitalCode")String hospitalCode,
                                            @Path("ipdSrlNo")String ipdSrlNo);


    //http://testapi.wcss.co.in/api/hospitalTreatment/ManageOprSchedule
    @FormUrlEncoded
    @POST("api/hospitalTreatment/manageOprationSchedule")
    Call<ResponseBody> manageOprationSchedule( @Field("authkey") String authkey,
                                               @Field("userId") String userId,
                                               @Field("hospitalCode") String hospitalCode,
                                               @Field("ipdSrlNo") String ipdSrlNo,
                                               @Field("ipdOprName") String ipdOprName,
                                               @Field("ipdOprSchedDate") String ipdOprSchedDate,
                                               @Field("ipdOprSchedTime") String ipdOprSchedTime,
                                               @Field("ipdOprSchedExpDuration") String ipdOprSchedExpDuration);

    //Discharge Summary
    //http://testapi.wcss.co.in/api/hospitalTreatment/managePatientIPDDischargeSmry
    @FormUrlEncoded
    @POST("api/hospitalTreatment/managePatientIPDDischargeSmry")
    Call<ResponseBody>managePatientIPDDischargeSmry(@Field("authkey")String authkey,
                                                    @Field("userId")String userId,
                                                    @Field("hospitalCode")String hospitalCode,
                                                    @Field("ipdSrlNo")String ipdSrlNo,
                                                    @Field("dischargeType") String dischargeType,
                                                    @Field("dischargeDate")String dischargeDate,
                                                    @Field("dischargeTime")String dischargeTime,
                                                    @Field("dischargeSummary")String dischargeSummary,
                                                    @Field("doctorNotes")String doctorNotes);


    //http://testapi.wcss.co.in/api/hospitalTreatment/getPatientDischargeSummary
    @GET ("api/hospitalTreatment/getPatientDischargeSummary/{authKey}/{userId}/{hospitalCode}/{ipdSrlNo}")
    Call<ResponseBody> getPatientDischargeSummary(@Path("authKey")String authKey,
                                                  @Path("userId")String userId,
                                                  @Path("hospitalCode")String hospitalCode,
                                                  @Path("ipdSrlNo")String ipdSrlNo);

    //Admission Summery
    //http://testapi.wcss.co.in/api/hospitalTreatment/managePatientIPDAdmissionSmry
    @FormUrlEncoded
    @POST("api/hospitalTreatment/managePatientIPDAdmissionSmry")
    Call<ResponseBody> managePatientIPDAdmissionSmry(@Field("authkey") String authkey,
                                                     @Field("userId") String userId,
                                                     @Field("hospitalCode") String hospitalCode,
                                                     @Field("ipdSrlNo") String ipdSrlNo,
                                                     @Field("admissionSummary") String admissionSummary);

    //Consent Letter
    //http://testapi.wcss.co.in/api/hospitalTreatment/getPatientConsentLetterDetails/kH4J3RXsw5cBMKvpLEwTDUCVi/25/6/41
    @GET("api/hospitalTreatment/getPatientConsentLetterDetails/{authKey}/{userId}/{hospitalCode}/{ipdSrlNo}")
    Call<ResponseBody> getPatientConsentLetterDetails(@Path("authKey")String authKey,
                                                      @Path("userId")String userId,
                                                      @Path("hospitalCode")String hospitalCode,
                                                      @Path("ipdSrlNo")String ipdSrlNo);

    //treatment operation submit
    //testapi.wcss.co.in/api/hospitalTreatment/getPatientOperationDetails/kH4J3RXsw5cBMKvpLEwTDUCVi/25/6/41
    @GET("api/hospitalTreatment/getPatientOperationDetails/{authKey}/{userId}/{hospitalCode}/{ipdSrlNo}")
    Call<ResponseBody>getPatientOperationDetails(@Path("authKey") String authKey,
                                                 @Path("userId") String userId,
                                                 @Path("hospitalCode") String hospitalCode,
                                                 @Path("ipdSrlNo")String ipdSrlNo);

    //http://testapi.wcss.co.in/api/hospitalTreatment/managePatientOperationDetails
    @FormUrlEncoded
    @POST("api/hospitalTreatment/managePatientOperationDetails")
    Call<ResponseBody> managePatientOperationDetails( @Field("authkey") String authkey,
                                                      @Field("userId") String userId,
                                                      @Field("hospitalCode") String hospitalCode,
                                                      @Field("ipdSrlNo") String ipdSrlNo,
                                                      @Field("ipdOprName") String ipdOprName,
                                                      @Field("ipdOprDate") String ipdOprDate,
                                                      @Field("ipdOprStTime") String ipdOprStTime,
                                                      @Field("ipdOprEndTime") String ipdOprEndTime,
                                                      @Field("ipdOprSurgonName") String ipdOprSurgonName,
                                                      @Field("ipdOprAssistedBy") String ipdOprAssistedBy,
                                                      @Field("ipdOprAnaesthetist") String ipdOprAnaesthetist,
                                                      @Field("ipdPreOprDiagnosis") String ipdPreOprDiagnosis,
                                                      @Field("ipdPostOprDiagnosis") String ipdPostOprDiagnosis,
                                                      @Field("ipdOprProcedure") String ipdOprProcedure,
                                                      @Field("ipdOprFindings") String ipdOprFindings);

    //http://testapi.wcss.co.in/api/hospitalTreatment/getPatientIPDAdmissionSummary/kH4J3RXsw5cBMKvpLEwTDUCVi/25/1/6
    @GET("api/hospitalTreatment/getPatientIPDAdmissionSummary/{authKey}/{userId}/{hospitalCode}/{ipdSrlNo}")
    Call<ResponseBody> getPatientIPDAdmissionSummary(@Path("authKey") String authKey,
                                                     @Path("userId") String userId,
                                                     @Path("hospitalCode") String hospitalCode,
                                                     @Path("ipdSrlNo")String ipdSrlNo);

    //http://testapi.wcss.co.in/api/hospitalTreatment/getPatientIPDDailyDetails/kH4J3RXsw5cBMKvpLEwTDUCVi/25/1/1\
    @GET("api/hospitalTreatment/getPatientIPDDailyDetails/{authKey}/{userId}/{hospitalCode}/{ipdSrlNo}")
    Call<ResponseBody>getPatientIPDDailyDetails(@Path("authKey") String authKey,
                                                @Path("userId") String userId,
                                                @Path("hospitalCode") String hospitalCode,
                                                @Path("ipdSrlNo") String ipdSrlNo);

    //http://testapi.wcss.co.in/api/hospitalTreatment/addEditIPDDailyProcedureDetails
    @FormUrlEncoded
    @POST("api/hospitalTreatment/addEditIPDDailyProcedureDetails")
    Call<ResponseBody> addEditIPDDailyProcedureDetails( @Field("authkey") String authkey,
                                                        @Field("userId") String userId,
                                                        @Field("hospitalCode") String hospitalCode,
                                                        @Field("ipdSrlNo") String ipdSrlNo,
                                                        @Field("ipdDlySrlNo") String ipdDlySrlNo ,
                                                        @Field("ipdDlyDate") String ipdDlyDate,
                                                        @Field("ipdDlyProcedure") String ipdDlyProcedure,
                                                        @Field("ipdDlyTrtmt") String ipdDlyTrtmt,
                                                        @Field("ipdDlyDoctorNotes") String ipdDlyDoctorNotes);

    //http://testapi.wcss.co.in/api/hospitalTreatment/getPatientBedList/kH4J3RXsw5cBMKvpLEwTDUCVi/25/1/1

    @GET("api/hospitalTreatment/getPatientBedList/{authKey}/{userId}/{hospitalCode}/{ipdSrlNo}")
    Call<ResponseBody>getPatientBedList(@Path("authKey") String authKey,
                                        @Path("userId") String userId,
                                        @Path("hospitalCode") String hospitalCode,
                                        @Path("ipdSrlNo")String ipdSrlNo);


}
