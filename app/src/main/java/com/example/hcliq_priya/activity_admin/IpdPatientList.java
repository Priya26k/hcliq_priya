package com.example.hcliq_priya.activity_admin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.adapter_admin.AdapterDoctorSelection;
import com.example.hcliq_priya.adapter_admin.AdapterIpdPatientList;
import com.example.hcliq_priya.model_admin.ModelDoctorSelection;
import com.example.hcliq_priya.model_admin.ModelIpdPatientList;
import com.example.hcliq_priya.service.ApiService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IpdPatientList extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editSearchPatientName;
    String eUserTypeCode = "02",doctorId,startDate="",endDate="",showAllFlag="F",selectDoctorId,userId="32";
    TextView textStartDate,textEndDate,textSelectedDoctor;
    ArrayList<ModelDoctorSelection> doctorSelections = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipd_patient_list);
        textStartDate=(TextView)findViewById(R.id.text_start_date);
        textEndDate=(TextView)findViewById(R.id.text_end_date);
        textSelectedDoctor = (TextView)findViewById(R.id.text_selected_doctor);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        editSearchPatientName = (EditText)findViewById(R.id.edit_search_patient_name);

        if(eUserTypeCode.equals("03")){
            textSelectedDoctor.setVisibility(View.GONE);
            //TODO intent doctorId
            getPatientList(doctorId);
        } else{
            textSelectedDoctor.setVisibility(View.VISIBLE);
            getDoctorList("F");
            textSelectedDoctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDoctorList("T");
                }
            });
        }
        onClick();

    }

    private void getDoctorList(final String showFlag) {
        doctorId = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(IpdPatientList.this);
        TextView title = new TextView(IpdPatientList.this);
        title.setText("Select Doctor");
        title.setBackgroundColor(Color.parseColor("#EA3E52"));
        title.setPadding(15, 15, 15, 15);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(18);
        builder.setCustomTitle(title);
        builder.setCancelable(false);
        View view = LayoutInflater.from(IpdPatientList.this).inflate(R.layout.layout_doctor_list, null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        final Button buttonOK = (Button)view.findViewById(R.id.button_ok);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in/").build();
        ApiService apiService = retrofit.create(ApiService.class);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Data fetching from server");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<ResponseBody> call = apiService.eGetDoctorList("kH4J3RXsw5cBMKvpLEwTDUCVi","1");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject =  jsonArray.getJSONObject(i);
                        String doctorId = jsonObject.getString("doctorId");
                        String doctorName = jsonObject.getString("doctorName");
                        String doctorImagePath = jsonObject.getString("doctorImagePath");
                        String specialistName = jsonObject.getString("specialistName");
                        String doctorConsultationFee = jsonObject.getString("doctorConsultationFee");
                        if (!contains(doctorSelections, doctorId)){
                            if(selectDoctorId == null){
                                selectDoctorId = doctorId;
                            }
                            else {
                                selectDoctorId = selectDoctorId + "," + doctorId;
                            }
                            doctorSelections.add(new ModelDoctorSelection(doctorId,doctorName,doctorImagePath,specialistName,doctorConsultationFee, "T"));
                        }
                    }
                    progressDialog.dismiss();

                    recyclerView.setLayoutManager(new LinearLayoutManager(IpdPatientList.this));
                    recyclerView.setAdapter(new AdapterDoctorSelection(IpdPatientList.this, doctorSelections));

                    buttonOK.setOnClickListener(new View.OnClickListener() {
                        int count = 0;
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < doctorSelections.size(); i++) {

                                if (doctorSelections.get(i).getDoctorSelectionFlag().equals("T")){
                                    if (doctorId == null){
                                        doctorId = doctorSelections.get(i).getDoctorId();
                                    } else {
                                        doctorId = doctorId + ","+ doctorSelections.get(i).getDoctorId();
                                    }
                                    count++;
                                }
                            }
                            if (count == 0){
                                textSelectedDoctor.setText("Select Doctor");
                                Toast.makeText(IpdPatientList.this, "Please Select a doctor...", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                textSelectedDoctor.setText(count+ " Doctors Selected");
                                alertDialog.dismiss();
                            }
                            getPatientList(doctorId);
                        }

                    });
                    if (showFlag.equals("T")){
                        alertDialog.show();
                    } else {
                        getPatientList(selectDoctorId);
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(IpdPatientList.this, "JSONException"+e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(IpdPatientList.this, "IOException"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void onClick(){
        final SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        final Calendar myCalendar=Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
            private void updateLabel(){
                startDate = sdf.format(myCalendar.getTime());
                textStartDate.setText(sdf.format(myCalendar.getTime()));
                getPatientList(doctorId);
            }
        };

        final Calendar myCalendar1=Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR,year);
                myCalendar1.set(Calendar.MONTH,month);
                myCalendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel1();
            }
            private void updateLabel1(){
                endDate = sdf.format(myCalendar1.getTime());
                textEndDate.setText(sdf.format(myCalendar1.getTime()));
                getPatientList(doctorId);
            }
        };
        textStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(IpdPatientList.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        textEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(IpdPatientList.this,date1,myCalendar1.get(Calendar.YEAR),myCalendar1.get(Calendar.MONTH),myCalendar1.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }

    public void filter(AdapterIpdPatientList adapterIpdPatientList, ArrayList<ModelIpdPatientList> appointmentPatientLists, String text){
        ArrayList<ModelIpdPatientList> filteredList=new ArrayList<>();
        for (ModelIpdPatientList item : appointmentPatientLists){
            if(item.getPatientName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapterIpdPatientList.filterList(filteredList);
    }

    public boolean contains(ArrayList<ModelDoctorSelection> list, String doctorId) {
        for (ModelDoctorSelection item : list) {
            if (item.getDoctorId().equals(doctorId)) {
                return true;
            }
        }
        return false;
    }

    public void getPatientList(String doctorId){

        if(startDate.equals("") && endDate.equals("")){
            showAllFlag = "F";
        }
        else {
            showAllFlag = "T";
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in/").build();
        ApiService apiService = retrofit.create(ApiService.class);

        final ArrayList<ModelIpdPatientList> appointmentPatientLists=new ArrayList<>();
        Call<ResponseBody> call = apiService.getHospitalPatientListIPD(
                "kH4J3RXsw5cBMKvpLEwTDUCVi",
                userId,
                "1",
                doctorId,showAllFlag,startDate,endDate);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String ipdSrlNo = jsonObject.getString("ipdSrlNo");
                        String ipdNo = jsonObject.getString("ipdNo");
                        String hospitalPatientId = jsonObject.getString("hospitalPatientId");
                        String patientName = jsonObject.getString("patientName");
                        String doctorName = jsonObject.getString("doctorName");
                        String ipdStDateDesc = jsonObject.getString("ipdStDateDesc");
                        String ipdEndDateDesc = jsonObject.getString("ipdEndDateDesc");
                        String facilityName = jsonObject.getString("facilityName");
                        String facilityUnitName = jsonObject.getString("facilityUnitName");
                        String facilityBedDesc = jsonObject.getString("facilityBedDesc");
                        String patientPaymentStatus = jsonObject.getString("patientPaymentStatus");
                        String ipdClStatus = jsonObject.getString("ipdClStatus");
                        String ipdCompletedStatus = jsonObject.getString("ipdCompletedStatus");
                        String ipdDischargeType = jsonObject.getString("ipdDischargeType");
                        String ipdDischargeTypeDesc = jsonObject.getString("ipdDischargeTypeDesc");
                        String patientImgPath = jsonObject.getString("patientImgPath");
                        String isOprtvTrtmtFlag = jsonObject.getString("isOprtvTrtmtFlag");
                        appointmentPatientLists.add(new ModelIpdPatientList(ipdSrlNo,ipdNo,hospitalPatientId,patientName,doctorName,ipdStDateDesc,ipdEndDateDesc,facilityName,facilityUnitName,facilityBedDesc,patientPaymentStatus,ipdClStatus,ipdCompletedStatus,ipdDischargeType,ipdDischargeTypeDesc,patientImgPath,isOprtvTrtmtFlag));

                    }
                    final AdapterIpdPatientList adapterIpdPatientList =new AdapterIpdPatientList(getApplicationContext(),appointmentPatientLists);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapterIpdPatientList);

                    editSearchPatientName.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            filter(adapterIpdPatientList,appointmentPatientLists,s.toString());
                        }
                    });

                }
                catch (JSONException e){
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

}