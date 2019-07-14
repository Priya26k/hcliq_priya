package com.example.hcliq_priya.activity_admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.adapter_admin.AdapterTreatmentHospitalStay;
import com.example.hcliq_priya.model_admin.ModelTreatmentHospitalStay;
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

public class TreatmentHospitalStay extends AppCompatActivity {
    RecyclerView recyclerViewHospitalStay;
    TextView createdBy;
    Button buttonAddNew;
    String patientNameValue,hospitalPatientId,patientImgPath,ipdSrlNo,userId="32";
    TextView textPatientName,textPatientId;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_stay);

        recyclerViewHospitalStay = (RecyclerView)findViewById(R.id.recycler_hospital_stay);
        createdBy = (TextView)findViewById(R.id.text_created_by) ;
        buttonAddNew = (Button)findViewById(R.id.button_add_new);
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
        //LinearLayout linearLayout=(LinearLayout)findViewById(R.id.layout_header);

       //Animation blink = AnimationUtils.loadAnimation(TreatmentHospitalStay.this,R.anim.blink_anim);
        //Animation slidein = AnimationUtils.loadAnimation(TreatmentHospitalStay.this,R.anim.slide_in);
        //Animation slidein1 = AnimationUtils.loadAnimation(TreatmentHospitalStay.this,R.anim.lefttoright);
        //Animation bounce = AnimationUtils.loadAnimation(TreatmentHospitalStay.this,R.anim.bounce);

        //linearLayout.startAnimation(slidein1);
        // slidein1.setDuration(800);

        buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TreatmentHospitalStay.this,TreatmentAddHospitalStay.class);
                intent.putExtra("patientNameValue",patientNameValue);
                intent.putExtra("hospitalPatientId",hospitalPatientId);
                intent.putExtra("patientImgPath",patientImgPath);
                intent.putExtra("ipdSrlNo",ipdSrlNo);
                startActivity(intent);
            }
        });

        getPatientIPDDailyDetails();

    }

    public void getPatientIPDDailyDetails(){

        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in/").build();
        final ApiService apiService=retrofit.create(ApiService.class);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Data fetching from server");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<ResponseBody> call= apiService.getPatientIPDDailyDetails("kH4J3RXsw5cBMKvpLEwTDUCVi", userId,"1",ipdSrlNo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    final ArrayList<ModelTreatmentHospitalStay> treatmentHospitalStays=new ArrayList();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String ipdDlySrlNo = jsonObject.getString("ipdDlySrlNo");
                        String ipdDlyDateDesc = jsonObject.getString("ipdDlyDateDesc");
                        String ipdDlyPrcdr = jsonObject.getString("ipdDlyPrcdr");
                        String ipdDlyTrtmt = jsonObject.getString("ipdDlyTrtmt");
                        String ipdDlyDoctorNotes = jsonObject.getString("ipdDlyDoctorNotes");
                        String dlyDetailCreatedByUserName = jsonObject.getString("dlyDetailCreatedByUserName");
                        String dlyDetailEditFlag = jsonObject.getString("dlyDetailEditFlag");
                        String ipdOprSrlNo = jsonObject.getString("ipdOprSrlNo");
                        String ipdOperationName = jsonObject.getString("ipdOperationName");
                        String ipdOperationStartTime = jsonObject.getString("ipdOperationStartTime");
                        String ipdOperationEndTime = jsonObject.getString("ipdOperationEndTime");

                        treatmentHospitalStays.add(new ModelTreatmentHospitalStay(ipdDlySrlNo, ipdDlyDateDesc, ipdDlyPrcdr, ipdDlyTrtmt, ipdDlyDoctorNotes, dlyDetailCreatedByUserName, dlyDetailEditFlag,ipdOprSrlNo,ipdOperationName,ipdOperationStartTime,ipdOperationEndTime));

                    }
                    progressDialog.dismiss();
                    recyclerViewHospitalStay.setLayoutManager(new LinearLayoutManager(TreatmentHospitalStay.this));
                    final AdapterTreatmentHospitalStay adapterTreatmentHospitalStay=new AdapterTreatmentHospitalStay(TreatmentHospitalStay.this,treatmentHospitalStays);
                    recyclerViewHospitalStay.setAdapter(adapterTreatmentHospitalStay);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TreatmentHospitalStay.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
