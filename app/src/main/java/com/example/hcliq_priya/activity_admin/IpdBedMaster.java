package com.example.hcliq_priya.activity_admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.adapter_admin.AdapterIpdBedMaster;
import com.example.hcliq_priya.model_admin.ModelIpdBedMaster;
import com.example.hcliq_priya.service.ApiService;

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

public class IpdBedMaster extends AppCompatActivity {

    Spinner spinner_unit_time,spinner_ipd_facility_name;
    ArrayList<ModelIpdBedMaster> arrayList=new ArrayList<>();
    String userId="32";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_allocation);
        spinner_ipd_facility_name=(Spinner)findViewById(R.id.spinner_IPD_facility_name);
        spinner_unit_time=(Spinner)findViewById(R.id.spinner_unit_name);
        Button button=(Button)findViewById(R.id.button_add_bed);

        final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(IpdBedMaster.this));
        arrayList.add(new ModelIpdBedMaster("Bed1"));
        arrayList.add(new ModelIpdBedMaster("Bed1"));
        arrayList.add(new ModelIpdBedMaster("Bed1"));
        arrayList.add(new ModelIpdBedMaster("Bed1"));
        arrayList.add(new ModelIpdBedMaster("Bed1"));
        AdapterIpdBedMaster adapterBedFacility=new AdapterIpdBedMaster(IpdBedMaster.this,arrayList);
        recyclerView.setAdapter(adapterBedFacility);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.add(new ModelIpdBedMaster("Bed1"));
                AdapterIpdBedMaster adapterBedFacility=new AdapterIpdBedMaster(IpdBedMaster.this,arrayList);
                recyclerView.setAdapter(adapterBedFacility);
            }
        });
        getIPDFacilityFilter();
        getIPDFacilityUnitFIlter();

    }

    public void getIPDFacilityFilter(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in/").build();
        ApiService apiService=retrofit.create(ApiService.class);
        final ArrayList<String> ipd_facility_name=new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(IpdBedMaster.this,android.R.layout.simple_list_item_1,ipd_facility_name);
        Call<ResponseBody> call=apiService.getIPDFacilityFilter("kH4J3RXsw5cBMKvpLEwTDUCVi",userId,"1");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String FilterDescription = jsonObject.getString("FilterDescription");
                        ipd_facility_name.add(FilterDescription);
                    }
                    spinner_ipd_facility_name.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(IpdBedMaster.this, "on Failure :"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public  void getIPDFacilityUnitFIlter() {
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in/").build();
        ApiService apiService=retrofit.create(ApiService.class);
        final ArrayList<String> unit_name = new ArrayList<>();

        final ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(IpdBedMaster.this, android.R.layout.simple_list_item_1, unit_name);
        Call<ResponseBody> call1 = apiService.getIPDFacilityUnitFIlter("kH4J3RXsw5cBMKvpLEwTDUCVi", userId, "1", "5");

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String FilterDescription = jsonObject.getString("FilterDescription");
                        unit_name.add(FilterDescription);
                    }
                    spinner_unit_time.setAdapter(arrayAdapter1);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(IpdBedMaster.this, "on Failure :" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
