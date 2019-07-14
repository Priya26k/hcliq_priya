package com.example.hcliq_priya.activity_admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.adapter_admin.AdapterBedAllocation;
import com.example.hcliq_priya.adapter_admin.AdapterIpdBedMaster;
import com.example.hcliq_priya.model_admin.ModelBedAllocation;
import com.example.hcliq_priya.model_admin.ModelIpdBedMaster;
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

public class BedAllocation extends AppCompatActivity {
    RecyclerView recyclerView;
    String patientNameValue,hospitalPatientId,patientImgPath;
    TextView textPatientName,textPatientId;
    CircleImageView circleImageView;
    String ipdSrlNo,userId="32";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_allocation);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_bed_allocation);
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

        getPatientBedList();

    }

    public void getPatientBedList(){

            Retrofit retrofit=new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in/").build();
            ApiService apiService=retrofit.create(ApiService.class);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Data fetching from server");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<ResponseBody> call1 = apiService.getPatientBedList("kH4J3RXsw5cBMKvpLEwTDUCVi",userId,"1",ipdSrlNo);
            call1.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        final ArrayList<ModelBedAllocation> bedAllocationArrayList = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String ipdNo = jsonObject.getString("ipdNo");
                            String ipdFacilityId = jsonObject.getString("ipdFacilityId");
                            String facilityName = jsonObject.getString("facilityName");
                            String ipdFacilityUnitId = jsonObject.getString("ipdFacilityUnitId");
                            String facilityUnitName = jsonObject.getString("facilityUnitName");
                            String ipdFacilityUnitBedId = jsonObject.getString("ipdFacilityUnitBedId");
                            String facilityBedDesc = jsonObject.getString("facilityBedDesc");
                            String ipdDetailStDateDesc = jsonObject.getString("ipdDetailStDateDesc");
                            String ipdDetailEndDateDesc = jsonObject.getString("ipdDetailEndDateDesc");
                            String ipdBedDeleteFlag = jsonObject.getString("ipdBedDeleteFlag");

                            bedAllocationArrayList.add(new ModelBedAllocation (ipdNo,ipdFacilityId,facilityName,ipdFacilityUnitId,facilityUnitName,ipdFacilityUnitBedId,facilityBedDesc,ipdDetailStDateDesc,ipdDetailEndDateDesc,ipdBedDeleteFlag));

                        }
                        progressDialog.dismiss();
                        recyclerView.setLayoutManager(new LinearLayoutManager(BedAllocation.this));
                        final AdapterBedAllocation adapterBedAllocation = new AdapterBedAllocation(BedAllocation.this, bedAllocationArrayList);
                        recyclerView.setAdapter(adapterBedAllocation);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    Toast.makeText(BedAllocation.this, "error", Toast.LENGTH_SHORT).show();

                }

            });
    }
}
