package com.example.hcliq_priya.activity_admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.adapter_admin.AdapterDailyDetailList;
import com.example.hcliq_priya.adapter_admin.AdapterPreOpReport;
import com.example.hcliq_priya.model_admin.ModelDailyDetailList;
import com.example.hcliq_priya.model_admin.ModelPreOperationReport;
import com.example.hcliq_priya.service.ApiService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PatientIPDProfile extends AppCompatActivity {

    ArrayList<ModelPreOperationReport> preOperationReports = new ArrayList<>();
    ArrayList<ModelPreOperationReport> postOperationReports = new ArrayList<>();
    ArrayList<ModelPreOperationReport> generalReports = new ArrayList<>();
    ArrayList<ModelPreOperationReport> operationList = new ArrayList<>();
    ArrayList<ModelDailyDetailList> dateReports = new ArrayList<>();
    LinearLayout linearLayoutSignatureInfo,linearLayout,linerLayoutReport;
    RecyclerView recyclerViewPreOpReport,recyclerViewPostOpReport,recyclerViewGeneralReport,recyclerViewDate,recyclerViewOperation;
    TextView textPatientAdmissionSummary,textUpdatedBy,textGeneral,textPreOpReport,textPostOperationReport,textPatientName,textPatientId,textIPDNo,textAdmitDate,textDischargeType,textDischargeDate;
    CheckBox checkBoxSign;
    CircleImageView circleImageView;
    String ipdSrlNo,userId="32";
    CardView cardViewAdmissionSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_ipdprofile);

        recyclerViewGeneralReport = (RecyclerView)findViewById(R.id.recycler_view_general_report);
        recyclerViewPreOpReport = (RecyclerView)findViewById(R.id.recycler_view_pre_op_report);
        recyclerViewPostOpReport = (RecyclerView)findViewById(R.id.recycler_view_post_op_report);
        recyclerViewDate = (RecyclerView)findViewById(R.id.recycler_view_date);
        recyclerViewOperation = (RecyclerView)findViewById(R.id.recycler_view_operation);
        textPatientAdmissionSummary = (TextView)findViewById(R.id.text_patient_admission_summary);
        textUpdatedBy = (TextView)findViewById(R.id.text_uptaded_by);
        textGeneral = (TextView)findViewById(R.id.text_general);
        textPreOpReport = (TextView)findViewById(R.id.text_pre_op_report);
        textPostOperationReport = (TextView)findViewById(R.id.text_post_operation);
        textPatientName = (TextView)findViewById(R.id.text_patient_name);
        textPatientId = (TextView)findViewById(R.id.text_patient_id);
        textIPDNo = (TextView)findViewById(R.id.text_ipd_no);
        textAdmitDate = (TextView)findViewById(R.id.text_admit_date);
        textDischargeDate = (TextView)findViewById(R.id.text_discharge_date);
        textDischargeType = (TextView)findViewById(R.id.text_discharge_type);
        linearLayoutSignatureInfo = (LinearLayout)findViewById(R.id.linearLayoutSignature);
        linerLayoutReport = (LinearLayout)findViewById(R.id.linearLayout_report);
        cardViewAdmissionSummary = (CardView)findViewById(R.id.card_view_admission_summary);
        checkBoxSign = (CheckBox)findViewById(R.id.checkbox_sign);
        circleImageView = (CircleImageView)findViewById(R.id.image);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);

        String patientImgPath = getIntent().getStringExtra("patientImgPath");
        String patientName = getIntent().getStringExtra("patientName");
        String hospitalPatientId = getIntent().getStringExtra("hospitalPatientId");
        String ipdNo = getIntent().getStringExtra("ipdNo");
        String ipdDischargeType = getIntent().getStringExtra("ipdDischargeType");
        String ipdStDateDesc = getIntent().getStringExtra("ipdStDateDesc");
        String ipdEndDateDesc = getIntent().getStringExtra("ipdEndDateDesc");
        ipdSrlNo = getIntent().getStringExtra("ipdSrlNo");
        textPatientName.setText("Patient Name : "+patientName);
        textPatientId.setText("Patient #"+hospitalPatientId);
        textIPDNo.setText("IPD No : "+ipdNo);
        textDischargeType.setText("Discharge Type : "+ipdDischargeType);
        textAdmitDate.setText("Admit Date : "+ipdStDateDesc);
        textDischargeDate.setText("Discharge Date : "+ipdEndDateDesc);
        if (patientImgPath.equals("")){
            Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/Patients/defualtUserImage.png").fit().into(circleImageView);
        } else {
            Picasso.with(getApplicationContext()).load(patientImgPath).fit().into(circleImageView);
        }

        cardViewAdmissionSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientIPDProfile.this,AdmissionSummery.class);
                startActivity(intent);
            }
        });

        linerLayoutReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientIPDProfile.this,IpdDiagnosisReport.class);
                startActivity(intent);
            }
        });

        getPatientProfile();
        animation();

    }

    public void getPatientProfile(){

        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in/").build();
        final ApiService apiService=retrofit.create(ApiService.class);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Data fetching from server");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<ResponseBody> call= apiService.getPatientIPDProfile("kH4J3RXsw5cBMKvpLEwTDUCVi", userId,"1",ipdSrlNo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject jsonObject1 = jsonObject.getJSONObject("patientIPDSummary");
                    JSONArray jsonArray = jsonObject.getJSONArray("patientIpdDiagnosisList");
                    JSONArray jsonArray1 = jsonObject.getJSONArray("patientIPDDailyDetailList");
                    JSONArray jsonArray2 = jsonObject.getJSONArray("patientOperationList");
                    JSONObject jsonObject5 = jsonObject.getJSONObject("patientConsentLetterStatus");

                    String ipdAdmissionSmry = jsonObject1.getString("ipdAdmissionSmry");
                    String ipdSmryCreatedByUser = jsonObject1.getString("ipdSmryCreatedByUser");
                    textPatientAdmissionSummary.setText(ipdAdmissionSmry);
                    textUpdatedBy.setText("Updated by : "+ipdSmryCreatedByUser);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        String ipdRptType = jsonObject2.getString("ipdRptType");
                        String ipdRptName = jsonObject2.getString("ipdRptName");
                        String ipdRptCreationDate = jsonObject2.getString("ipdRptCreationDate");
                        String ipdRptCreatedByUser = jsonObject2.getString("ipdRptCreatedByUser");
                        if( ipdRptType.equals("1")){
                            generalReports.add(new ModelPreOperationReport(ipdRptName,ipdRptCreationDate,ipdRptCreatedByUser));
                        }
                        if( ipdRptType.equals("2")){
                            preOperationReports.add(new ModelPreOperationReport(ipdRptName,ipdRptCreationDate,ipdRptCreatedByUser));
                        }
                        if( ipdRptType.equals("3")){
                            postOperationReports.add(new ModelPreOperationReport(ipdRptName,ipdRptCreationDate,ipdRptCreatedByUser));
                        }
                    }

                    for (int i=0;i<jsonArray1.length();i++){
                        JSONObject jsonObject3 = jsonArray1.getJSONObject(i);
                        String ipdDlyPrcdr = jsonObject3.getString("ipdDlyPrcdr");
                        String ipdDlySrlNo = jsonObject3.getString("ipdDlySrlNo");
                        String ipdDlyDate = jsonObject3.getString("ipdDlyDate");
                        String ipdSrlNo = jsonObject3.getString("ipdSrlNo");
                        String ipdDlyTrtmt = jsonObject3.getString("ipdDlyTrtmt");
                        String ipdDlyDoctorNotes = jsonObject3.getString("ipdDlyDoctorNotes");
                        String ipdDlyDetailCreationTime = jsonObject3.getString("ipdDlyDetailCreationTime");
                        String ipdDlyDetailCreatedByUser = jsonObject3.getString("ipdDlyDetailCreatedByUser");
                        String ipdDlyDetailEditFlag = jsonObject3.getString("ipdDlyDetailEditFlag");

                        dateReports.add(new ModelDailyDetailList(ipdDlyPrcdr,ipdDlyTrtmt,ipdDlyDetailCreationTime,ipdDlyDetailCreatedByUser));

                    }

                    for (int i=0;i<jsonArray2.length();i++){
                        JSONObject jsonObject4 = jsonArray2.getJSONObject(i);
                        String ipdSrlNo = jsonObject4.getString("ipdSrlNo");
                        String ipdOprSrlNo = jsonObject4.getString("ipdOprSrlNo");
                        String ipdOprName = jsonObject4.getString("ipdOprName");
                        String ipdOprSchedDate = jsonObject4.getString("ipdOprSchedDate");
                        String ipdOprSchedStTime = jsonObject4.getString("ipdOprSchedStTime");
                        String ipdOprSchedEndTime = jsonObject4.getString("ipdOprSchedEndTime");
                        String ipdOprStatus = jsonObject4.getString("ipdOprStatus");

                        operationList.add(new ModelPreOperationReport(ipdOprName,ipdOprSchedDate,ipdOprSchedStTime+"-"+ipdOprSchedEndTime));
                    }
                    progressDialog.dismiss();

                    if (generalReports.isEmpty()){
                        textGeneral.setVisibility(View.GONE);
                    }

                    if (preOperationReports.isEmpty()){
                        textPreOpReport.setVisibility(View.GONE);
                    }

                    if (postOperationReports.isEmpty()){
                        textPostOperationReport.setVisibility(View.GONE);
                    }

                    recyclerViewGeneralReport.setLayoutManager(new LinearLayoutManager(PatientIPDProfile.this));
                    recyclerViewGeneralReport.setAdapter(new AdapterPreOpReport(PatientIPDProfile.this,generalReports));

                    recyclerViewPreOpReport.setLayoutManager(new LinearLayoutManager(PatientIPDProfile.this));
                    recyclerViewPreOpReport.setAdapter(new AdapterPreOpReport(PatientIPDProfile.this,preOperationReports));

                    recyclerViewPostOpReport.setLayoutManager(new LinearLayoutManager(PatientIPDProfile.this));
                    recyclerViewPostOpReport.setAdapter(new AdapterPreOpReport(PatientIPDProfile.this,postOperationReports));

                    recyclerViewDate.setLayoutManager(new LinearLayoutManager(PatientIPDProfile.this));
                    recyclerViewDate.setAdapter(new AdapterDailyDetailList(PatientIPDProfile.this,dateReports));

                    recyclerViewOperation.setLayoutManager(new LinearLayoutManager(PatientIPDProfile.this));
                    recyclerViewOperation.setAdapter(new AdapterPreOpReport(PatientIPDProfile.this,operationList));

                    String consentLetterFlag = jsonObject5.getString("consentLetterFlag");
                    String consentLetterStatus = jsonObject5.getString("consentLetterStatus");

                    if (consentLetterFlag.equals("T")){
                        linearLayoutSignatureInfo.setVisibility(View.VISIBLE);
                        if (consentLetterStatus.equals("Y")){
                            checkBoxSign.setChecked(true);
                            String signed = "<u>Signed</u>";
                            checkBoxSign.setText(Html.fromHtml(signed));
                            checkBoxSign.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(PatientIPDProfile.this,ConsentLetter.class);
                                    startActivity(intent);
                                }
                            });
                        } else if (consentLetterStatus.equals("N")){
                            checkBoxSign.setChecked(false);
                            String signed = "<u>Signed</u>";
                            checkBoxSign.setText(Html.fromHtml(signed));
                            checkBoxSign.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(PatientIPDProfile.this,ConsentLetter.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    } else if (consentLetterFlag.equals("F")){
                        linearLayoutSignatureInfo.setVisibility(View.GONE);
                    }

                }
                catch (JSONException e){
                    Toast.makeText(PatientIPDProfile.this, "Json exception"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });


    }

    public void animation(){
        Animation animation = AnimationUtils.loadAnimation(PatientIPDProfile.this,R.anim.lefttoright);
        linearLayout.startAnimation(animation);
    }

}