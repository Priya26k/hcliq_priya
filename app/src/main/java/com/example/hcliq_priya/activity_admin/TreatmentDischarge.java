package com.example.hcliq_priya.activity_admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hcliq_priya.R;
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

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TreatmentDischarge extends AppCompatActivity {
    TextView textDateOfDischarge,textTimeOfDischarge,textAdviceOnDischarge,textDoctorNotes,textPatientId,textPatientName;
    EditText dateOfDischarge,timeOfDischarge,doctorNotes,adviceOnDischarge;
    Spinner  spinnerTreatmentType;
    CardView cardviews ;
    Button btnSave;
    String patientNameValue,hospitalPatientId,patientImgPath,dischargeType,ipdSrlNo,userId="32";
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_discharge);

        textDateOfDischarge=(TextView)findViewById(R.id.text_Date_of_discharge);
        textTimeOfDischarge=(TextView)findViewById(R.id.text_time_of_discharge);
        textAdviceOnDischarge =(TextView)findViewById(R.id.text_advice_on_discharge) ;
        textDoctorNotes=(TextView)findViewById(R.id.text_doctor_notes);
        dateOfDischarge = (EditText) findViewById(R.id.edit_text_date_of_discharge);
        timeOfDischarge = (EditText) findViewById(R.id.edit_text_time_of_discharge);
        doctorNotes = (EditText) findViewById(R.id.edit_text_doctor_notes);
        adviceOnDischarge = (EditText) findViewById(R.id.edit_text_advice_on_discharge);
        spinnerTreatmentType = (Spinner) findViewById(R.id.spinner_treatment_type);
        cardviews = (CardView) findViewById(R.id.cardview);
        btnSave=(Button)findViewById(R.id.save_button);
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

        getPatientDischargeSummary();
        Animation animation = AnimationUtils.loadAnimation(TreatmentDischarge.this,R.anim.lefttoright);
        cardviews.startAnimation(animation);

        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String currentDate = sdf.format(new Date());
        dateOfDischarge.setText(currentDate);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {

                dateOfDischarge.setText(sdf.format(myCalendar.getTime()));

            }
        };

        dateOfDischarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TreatmentDischarge.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        Calendar mcurrentTime = Calendar.getInstance();

        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);

        timeOfDischarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog mTimePicker = new TimePickerDialog(TreatmentDischarge.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeOfDischarge.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });
    }

    private void getFilterInfo(final String ipdDischargeType ) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in/").build();
        ApiService apiService = retrofit.create(ApiService.class);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Data fetching from server");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<ResponseBody> call = apiService.getFilterInfo("AE");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    final ArrayList<String> treatmentIdList = new ArrayList<>();
                    final ArrayList<String> treatmentDescriptionLIst = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        final String FilterID = jsonObject.getString("FilterID");
                        final String FilterDescription = jsonObject.getString("FilterDescription");
                        treatmentDescriptionLIst.add(FilterDescription);
                        treatmentIdList.add(FilterID);
                    }

                    progressDialog.dismiss();

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(TreatmentDischarge.this, android.R.layout.simple_dropdown_item_1line, treatmentDescriptionLIst);
                    spinnerTreatmentType.setAdapter(arrayAdapter);
                    spinnerTreatmentType.setSelection(treatmentIdList.indexOf(ipdDischargeType));
                    spinnerTreatmentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (treatmentDescriptionLIst.get(i).equals("Death")){
                                textTimeOfDischarge.setText("Time of Death");
                                adviceOnDischarge.setHint("Cause of Death");
                                textDateOfDischarge.setText("Date of Expire");
                                textAdviceOnDischarge.setText("Cause of Death");
                                textDoctorNotes.setText("Doctor Notes");
                            }
                            else {
                                textTimeOfDischarge.setText("Time of Discharge");
                                adviceOnDischarge.setHint("Advice on Discharge");
                                textDateOfDischarge.setText("Date of Discharge");
                                textAdviceOnDischarge.setText("Advice on Discharge");
                                textDoctorNotes.setText("Doctor Notes");
                            }

                            dischargeType = treatmentIdList.get(i);

                            btnSave.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    managePatientIPDDischargeSmry(dischargeType);
                                    Intent intent = new Intent(TreatmentDischarge.this,PatientIPDHome.class);
                                    startActivity(intent);

                                }
                            });

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

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

    public void managePatientIPDDischargeSmry(String dischargeType){
        ApiService apiService = new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in").addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.class);
        Call<ResponseBody> call = apiService.managePatientIPDDischargeSmry("kH4J3RXsw5cBMKvpLEwTDUCVi", userId, "1", ipdSrlNo, dischargeType, dateOfDischarge.getText().toString(), timeOfDischarge.getText().toString(), adviceOnDischarge.getText().toString(), doctorNotes.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String activitySuccessFlag = jsonObject.getString("activitySuccessFlag");
                    String activityMessage = jsonObject.getString("activityMessage");
                    Toast.makeText(TreatmentDischarge.this, "success"+activityMessage, Toast.LENGTH_SHORT).show();
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

    public void getPatientDischargeSummary(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in/").build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.getPatientDischargeSummary("kH4J3RXsw5cBMKvpLEwTDUCVi", userId, "1",ipdSrlNo);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                JSONObject jsonObject = new JSONObject(response.body().string());
                String ipdSrlNo = jsonObject.getString("ipdSrlNo");
                String ipdDischargeType = jsonObject.getString("ipdDischargeType");
                String ipdDischargeDate = jsonObject.getString("ipdDischargeDate");
                String ipdDischargeTime = jsonObject.getString("ipdDischargeTime");
                String ipdDischargeSmry = jsonObject.getString("ipdDischargeSmry");
                String ipdDischargeSmryCreatedByUser = jsonObject.getString("ipdDischargeSmryCreatedByUser");
                String ipdDischargeSmryEditFlag = jsonObject.getString("ipdDischargeSmryEditFlag");
                String ipdDoctorNotes = jsonObject.getString("ipdDoctorNotes");
                String ipdDoctorNotesCreatedByUser = jsonObject.getString("ipdDoctorNotesCreatedByUser");
                String ipdDoctorNotesEditFlag = jsonObject.getString("ipdDoctorNotesEditFlag");
                dateOfDischarge.setText(ipdDischargeDate);
                timeOfDischarge.setText(ipdDischargeTime);
                doctorNotes.setText(ipdDoctorNotes);
                adviceOnDischarge.setText(ipdDischargeSmry);

                getFilterInfo( ipdDischargeType );
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
}