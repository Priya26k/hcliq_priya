package com.example.hcliq_priya.activity_admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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

public class TreatmentAddHospitalStay extends AppCompatActivity {

    EditText hospitalStayDate,ipdDlyPrcdr1,ipdDlyTrtmt1,ipdDlyDoctorNotes1;
    TextView hospitalStayEndTime1,textPatientName,textPatientId,hospitalStayStartTime1;
    Button buttonsave;
    String ipdDlySrlNo, ipdDlyDateDesc, ipdDlyPrcdr, ipdDlyTrtmt, ipdDlyDoctorNotes, dlyDetailCreatedByUserName, dlyDetailEditFlag, ipdOprSrlNo, ipdOperationName, ipdOperationStartTime, ipdOperationEndTime,ipdSrlNo,userId="32",patientNameValue,hospitalPatientId,patientImgPath;
    CircleImageView circleImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_add_hospital_stay);

        hospitalStayDate = (EditText) findViewById(R.id.edit_hospital_stay_date);
        hospitalStayStartTime1 = (TextView) findViewById(R.id.edit_hospital_stay_start_time);
        hospitalStayEndTime1 = (TextView) findViewById(R.id.edit_hospital_stay_end_time);
        buttonsave = (Button)findViewById(R.id.button_saveinfo);
        ipdDlyPrcdr1 =(EditText)findViewById(R.id.edit_hospital_stay_procedure);
        ipdDlyTrtmt1=(EditText)findViewById(R.id.edit_hospital_stay_treatment);
        ipdDlyDoctorNotes1=(EditText)findViewById(R.id.edit_doctor_note);
        textPatientName = (TextView)findViewById(R.id.text_patient_name);
        textPatientId = (TextView)findViewById(R.id.text_patient_id);
        circleImageView = (CircleImageView)findViewById(R.id.image);

        Intent intent = getIntent();

        ipdDlyDateDesc = intent.getStringExtra("ipdDlyDateDesc");
        ipdDlySrlNo = intent.getStringExtra("ipdDlySrlNo");
        ipdDlyPrcdr = intent.getStringExtra("ipdDlyPrcdr");
        ipdDlyTrtmt = intent.getStringExtra("ipdDlyTrtmt");
        ipdDlyDoctorNotes = intent.getStringExtra("ipdDlyDoctorNotes");
        dlyDetailCreatedByUserName= intent.getStringExtra("dlyDetailCreatedByUserName");
        dlyDetailEditFlag= intent.getStringExtra("dlyDetailEditFlag");
        ipdOprSrlNo= intent.getStringExtra("ipdOprSrlNo");
        ipdOperationName= intent.getStringExtra("ipdOperationName");
        ipdOperationStartTime= intent.getStringExtra("ipdOperationStartTime");
        ipdOperationEndTime= intent.getStringExtra("ipdOperationEndTime");

        hospitalStayDate.setText(ipdDlyDateDesc);
        hospitalStayStartTime1.setText(ipdOperationStartTime);
        hospitalStayEndTime1.setText(ipdOperationEndTime);
        ipdDlyTrtmt1.setText(ipdDlyTrtmt);
        ipdDlyDoctorNotes1.setText(ipdDlyDoctorNotes);
        ipdDlyPrcdr1.setText(ipdDlyPrcdr);

        patientNameValue = getIntent().getStringExtra("patientName");
        hospitalPatientId = getIntent().getStringExtra("hospitalPatientId");
        patientImgPath = getIntent().getStringExtra("patientImgPath");
        ipdSrlNo = getIntent().getStringExtra("ipdSrlNo");
        textPatientName.setText(patientNameValue);
        textPatientId.setText("Patient #"+hospitalPatientId);
        if (patientImgPath == null || patientImgPath.equals("")){
            Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/Patients/defualtUserImage.png").fit().into(circleImageView);
        } else {
            Picasso.with(getApplicationContext()).load(patientImgPath).fit().into(circleImageView);
        }

        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditIPDDailyProcedureDetails();
            }
        });

        clickListeners();
    }

    private void clickListeners(){

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        String currentdate = simpleDateFormat.format(new Date());

        final Calendar mycalendar = Calendar.getInstance();
        hospitalStayDate.setText(ipdDlyDateDesc);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mycalendar.set(Calendar.YEAR,year);
                mycalendar.set(Calendar.MONTH,month);
                mycalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {

                hospitalStayDate.setText(simpleDateFormat.format(mycalendar.getTime()));

            }
        };
        hospitalStayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TreatmentAddHospitalStay.this,date,mycalendar.get(Calendar.YEAR),mycalendar.get(Calendar.MONTH),mycalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);

        hospitalStayStartTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog mTimePicker = new TimePickerDialog(TreatmentAddHospitalStay.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hospitalStayStartTime1.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
                mTimePicker.show();

                hospitalStayEndTime1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TimePickerDialog mTimePicker = new TimePickerDialog(TreatmentAddHospitalStay.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hospitalStayEndTime1.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, true);
                        mTimePicker.show();
                    }
                });

            }
        });
    }

    public void addEditIPDDailyProcedureDetails(){

        ApiService apiService = new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in").addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.class);

            Call<ResponseBody> call = apiService.addEditIPDDailyProcedureDetails("kH4J3RXsw5cBMKvpLEwTDUCVi",
                userId,
                "1",
                ipdSrlNo,
                    getIntent().getStringExtra("ipdDlySrlNo"),
                hospitalStayDate.getText().toString(),
                ipdDlyPrcdr1.getText().toString(),
                ipdDlyTrtmt1.getText().toString(),
                ipdDlyDoctorNotes1.getText().toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String activitySuccessFlag = jsonObject.getString("activitySuccessFlag");
                    String activityMessage = jsonObject.getString("activityMessage");
                    Toast.makeText(TreatmentAddHospitalStay.this, ""+activityMessage, Toast.LENGTH_SHORT).show();
                if(activitySuccessFlag.equals("S")){
                    Intent intent = new Intent(TreatmentAddHospitalStay.this,TreatmentHospitalStay.class);
                    startActivity(intent);
                }

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

}