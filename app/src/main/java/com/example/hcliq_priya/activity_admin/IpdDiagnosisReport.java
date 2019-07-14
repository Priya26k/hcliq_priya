package com.example.hcliq_priya.activity_admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.hcliq_priya.R;
import com.example.hcliq_priya.adapter_admin.AdapterIpdDiagnosisReport;
import com.example.hcliq_priya.model_admin.ModelIpdDiagnosisReport;
import com.example.hcliq_priya.service.ApiService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IpdDiagnosisReport extends AppCompatActivity {
    Button buttonAddNew,buttonSave;
    RecyclerView recyclerView;
    String ipdSrlNo,userId="32";
    ArrayList<ModelIpdDiagnosisReport> ipdDiagnosisReportArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipd_diagnosis_report);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        buttonAddNew=(Button)findViewById(R.id.button_add_new);
        buttonSave=(Button)findViewById(R.id.button_save) ;
        ipdSrlNo = getIntent().getStringExtra("ipdSrlNo");

        getPatientIPDDiagnosisReport();

    }

    public void getPatientIPDDiagnosisReport(){
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://testapi.wcss.co.in/")
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Data fetching from server");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<ResponseBody> call1 = apiService.getPatientIPDDiagnosisReport("kH4J3RXsw5cBMKvpLEwTDUCVi", userId, "1",ipdSrlNo);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String ipdSrlNo = jsonObject.getString("ipdSrlNo");
                        String ipdRptSrlNo = jsonObject.getString("ipdRptSrlNo");
                        String ipdRptName = jsonObject.getString("ipdRptName");
                        String ipdRptFilePath = jsonObject.getString("ipdRptFilePath");
                        String ipdRptType = jsonObject.getString("ipdRptType");
                        String ipdRptCreationDate = jsonObject.getString("ipdRptCreationDate");
                        String ipdRptCreatedByUser = jsonObject.getString("ipdRptCreatedByUser");
                        String ipdRptEditFlag = jsonObject.getString("ipdRptEditFlag");
                        ipdDiagnosisReportArrayList.add(new ModelIpdDiagnosisReport(ipdSrlNo,ipdRptSrlNo,ipdRptName,ipdRptFilePath,ipdRptType,ipdRptCreationDate,ipdRptCreatedByUser,ipdRptEditFlag));
                    }
                    progressDialog.dismiss();
                    recyclerView.setLayoutManager(new LinearLayoutManager(IpdDiagnosisReport.this));
                    final AdapterIpdDiagnosisReport adapterIpdDiagnosisReport=new AdapterIpdDiagnosisReport(IpdDiagnosisReport.this, ipdDiagnosisReportArrayList);
                    recyclerView.setAdapter(adapterIpdDiagnosisReport);

                    buttonAddNew.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ipdDiagnosisReportArrayList.add(new ModelIpdDiagnosisReport("", "", "", "", "", "", "", ""));
                            AdapterIpdDiagnosisReport adapterIpdDiagnosisReport1=new AdapterIpdDiagnosisReport(IpdDiagnosisReport.this,ipdDiagnosisReportArrayList);
                            recyclerView.setAdapter(adapterIpdDiagnosisReport1);
                        }
                    });

                    buttonSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ipdDiagnosisList();
                            Intent intent = new Intent(IpdDiagnosisReport.this,PatientIPDHome.class);
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void ipdDiagnosisList(){
        ApiService apiService = new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in").addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.class);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("authkey", "kH4J3RXsw5cBMKvpLEwTDUCVi");
        jsonObject.addProperty("userId", userId);
        jsonObject.addProperty("hospitalCode", "1");
        jsonObject.addProperty("ipdSrlNo", ipdSrlNo);

        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < ipdDiagnosisReportArrayList.size(); i++) {
            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("ipdSrlNo",ipdDiagnosisReportArrayList.get(i).getIpdSrlNo());
            jsonObject1.addProperty("ipdRptSrlNo", ipdDiagnosisReportArrayList.get(i).getIpdRptSrlNo());
            jsonObject1.addProperty("ipdRptName", ipdDiagnosisReportArrayList.get(i).getIpdRptName());
            jsonObject1.addProperty("ipdRptFilePath", ipdDiagnosisReportArrayList.get(i).getIpdRptFilePath());
            jsonObject1.addProperty("ipdRptType", ipdDiagnosisReportArrayList.get(i).getIpdRptType());
            jsonObject1.addProperty("ipdRptCreationDate", ipdDiagnosisReportArrayList.get(i).getIpdRptCreationDate());
            jsonObject1.addProperty("ipdRptCreatedByUser", ipdDiagnosisReportArrayList.get(i).getIpdRptCreatedByUser());
            jsonObject1.addProperty("ipdRptEditFlag", ipdDiagnosisReportArrayList.get(i).getIpdRptEditFlag());

            jsonArray.add(jsonObject1);
        }
        jsonObject.add("ipdDiagnosisList",jsonArray);

        Toast.makeText(this, ""+jsonObject, Toast.LENGTH_SHORT).show();

        Call<JsonObject> call = apiService.managePatientIPDDiagnosisReport(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try{
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String activitySuccessFlag = jsonObject.getString("activitySuccessFlag");
                    String activityMessage = jsonObject.getString("activityMessage");
                    Toast.makeText(IpdDiagnosisReport.this, "success"+activityMessage, Toast.LENGTH_SHORT).show();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
}
