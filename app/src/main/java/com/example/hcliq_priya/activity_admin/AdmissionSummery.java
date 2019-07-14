package com.example.hcliq_priya.activity_admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.model_admin.ModelIpdPatientList;
import com.example.hcliq_priya.service.ApiService;
import com.squareup.picasso.Picasso;

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
import retrofit2.converter.gson.GsonConverterFactory;

public class AdmissionSummery extends AppCompatActivity {
    EditText editAdmissinSummery;
    Button buttonBack,buttonSave;
    LinearLayout linearLayout;
    String patientNameValue,hospitalPatientId,patientImgPath,ipdSrlNo,userId="32";
    TextView textPatientName,textPatientId;
    CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_summery);
        editAdmissinSummery= (EditText)findViewById(R.id.edit_text_summery);
        buttonBack = (Button)findViewById(R.id.button_back);
        buttonSave = (Button)findViewById(R.id.button_save);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        textPatientName = (TextView)findViewById(R.id.text_patient_name);
        textPatientId = (TextView)findViewById(R.id.text_patient_id);
        circleImageView = (CircleImageView)findViewById(R.id.image);
        patientNameValue = getIntent().getStringExtra("patientName");
        hospitalPatientId = getIntent().getStringExtra("hospitalPatientId");
        patientImgPath = getIntent().getStringExtra("patientImgPath");
        ipdSrlNo = getIntent().getStringExtra("ipdSrlNo");
        textPatientName.setText("Patient Name : "+patientNameValue);
        textPatientId.setText("Patient #"+hospitalPatientId);
        if (patientImgPath.equals("")){
            Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/Patients/defualtUserImage.png").fit().into(circleImageView);
        } else {
            Picasso.with(getApplicationContext()).load(patientImgPath).fit().into(circleImageView);
        }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdmissionSummery.this,PatientIPDHome.class);
                startActivity(intent);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managePatientIPDAdmissionSmry();
                Intent intent = new Intent(AdmissionSummery.this,PatientIPDHome.class);
                startActivity(intent);
            }
        });

        animation();
        getPatientIPDAdmissionSummary();

    }

    public void managePatientIPDAdmissionSmry(){
        ApiService apiService = new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in").addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.class);

            Call<ResponseBody> call = apiService.managePatientIPDAdmissionSmry("kH4J3RXsw5cBMKvpLEwTDUCVi",
                    userId,
                    "1",
                    ipdSrlNo,
                    editAdmissinSummery.getText().toString()
            );

        call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try{

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String activitySuccessFlag = jsonObject.getString("activitySuccessFlag");
                        String activityMessage = jsonObject.getString("activityMessage");
                        Toast.makeText(AdmissionSummery.this, "success"+activityMessage, Toast.LENGTH_SHORT).show();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getPatientIPDAdmissionSummary(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in/").build();
        ApiService apiService = retrofit.create(ApiService.class);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Data fetching from server");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<ResponseBody> call = apiService.getPatientIPDAdmissionSummary("kH4J3RXsw5cBMKvpLEwTDUCVi", userId, "1",ipdSrlNo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String ipdSrlNo = jsonObject.getString("ipdSrlNo");
                    String ipdAdmissionSmry = jsonObject.getString("ipdAdmissionSmry");
                    String ipdSmryCreatedByUser = jsonObject.getString("ipdSmryCreatedByUser");
                    String ipdAdmissionSmryEditFlag = jsonObject.getString("ipdAdmissionSmryEditFlag");
                    String ipdDoctorNotes = jsonObject.getString("ipdDoctorNotes");
                    String ipdDoctorNotesCreatedByUser = jsonObject.getString("ipdDoctorNotesCreatedByUser");
                    String ipdDoctorNotesEditFlag = jsonObject.getString("ipdDoctorNotesEditFlag");
                    String ipdDischargeSmry = jsonObject.getString("ipdDischargeSmry");
                    String ipdDischargeSmryCreatedByUser = jsonObject.getString("ipdDischargeSmryCreatedByUser");
                    String ipdDischargeSmryEditFlag = jsonObject.getString("ipdDischargeSmryEditFlag");
                    String ipdSmryStatus = jsonObject.getString("ipdSmryStatus");
                    progressDialog.dismiss();
                    editAdmissinSummery.setText(ipdAdmissionSmry);
                    if(ipdAdmissionSmryEditFlag.equals("T")){
                        editAdmissinSummery.setFocusable(true);
                        editAdmissinSummery.setFocusableInTouchMode(true);
                        editAdmissinSummery.setClickable(true);
                    } else {
                        editAdmissinSummery.setFocusable(false);
                        editAdmissinSummery.setFocusableInTouchMode(false);
                        editAdmissinSummery.setClickable(false);
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void animation(){
        Animation animation = AnimationUtils.loadAnimation(AdmissionSummery.this,R.anim.lefttoright);
        linearLayout.startAnimation(animation);
    }
 }

