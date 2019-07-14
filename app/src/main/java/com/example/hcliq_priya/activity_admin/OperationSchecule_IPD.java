package com.example.hcliq_priya.activity_admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.service.ApiService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class OperationSchecule_IPD extends AppCompatActivity {
    EditText operationScheduleDate,operationScheduleStartTime,expectedDuration,operationName;
    Button btn_save;
    String patientNameValue,hospitalPatientId,patientImgPath;
    TextView textPatientName,textPatientId;
    CircleImageView circleImageView;
    String ipdSrlNo,userId="32";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_schedule_ipd);

        operationScheduleDate = (EditText) findViewById(R.id.edit_operation_schedule_date);
        operationScheduleStartTime = (EditText) findViewById(R.id.edit_operation_schedule_start_time);
        expectedDuration = (EditText) findViewById(R.id.edit_expected_duration);
        operationName = (EditText) findViewById(R.id.text_operation_name);
        btn_save=(Button)findViewById(R.id.button_save);
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

        clickListeners();
        getOperationSchedule();
    }

    private void clickListeners(){


        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        String currentdate = simpleDateFormat.format(new Date());
        operationScheduleDate.setText(""+currentdate);

        final Calendar mycalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mycalendar.set(Calendar.YEAR,year);
                mycalendar.set(Calendar.MONTH,month);
                mycalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {

                operationScheduleDate.setText(simpleDateFormat.format(mycalendar.getTime()));

            }
        };
        operationScheduleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(OperationSchecule_IPD.this,date,mycalendar.get(Calendar.YEAR),mycalendar.get(Calendar.MONTH),mycalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);

        operationScheduleStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog mTimePicker = new TimePickerDialog(OperationSchecule_IPD.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        operationScheduleStartTime.setText(hourOfDay + ":" +minute);
                    }
                },hour,minute,true);
                mTimePicker.show();
            }

        });

    }

    public void getOperationSchedule(){

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://testapi.wcss.co.in/")
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Data fetching from server");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<ResponseBody> call = apiService.getOperationSchedule("kH4J3RXsw5cBMKvpLEwTDUCVi", userId, "1",ipdSrlNo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        String ipdSrlNo = jsonObject.getString("ipdSrlNo");
                        String ipdOprSrlNo = jsonObject.getString("ipdOprSrlNo");
                        String ipdOprName = jsonObject.getString("ipdOprName");
                        String ipdOPrSchedDateDesc = jsonObject.getString("ipdOPrSchedDateDesc");
                        String ipdOprSchedStTimeDesc = jsonObject.getString("ipdOprSchedStTimeDesc");
                        String ipdOprExpDuration = jsonObject.getString("ipdOprExpDuration");
                        String ipdOprSchedEditFlag = jsonObject.getString("ipdOprSchedEditFlag");
                        progressDialog.dismiss();

                        operationName.setText(ipdOprName);
                        operationScheduleDate.setText(ipdOPrSchedDateDesc);
                        operationScheduleStartTime.setText(ipdOprSchedStTimeDesc);
                        expectedDuration.setText(ipdOprExpDuration);

                        if (ipdOprSchedEditFlag.equals("F")){
                            operationScheduleStartTime.setEnabled(false);
                            operationScheduleDate.setEnabled(false);
                            expectedDuration.setEnabled(false);

                        } else {
                            operationScheduleStartTime.setEnabled(true);
                            operationScheduleDate.setEnabled(true);
                            expectedDuration.setEnabled(true);
                        }

                    btn_save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            manageOprationSchedule();
                            Intent intent = new Intent(OperationSchecule_IPD.this,PatientIPDHome.class);
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

    public void manageOprationSchedule(){
            ApiService apiService = new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in").addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.class);
            Call<ResponseBody> call = apiService.manageOprationSchedule("kH4J3RXsw5cBMKvpLEwTDUCVi", userId, "1", ipdSrlNo,operationName.getText().toString(),operationScheduleDate.getText().toString(),operationScheduleStartTime.getText().toString(),expectedDuration.getText().toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String activitySuccessFlag = jsonObject.getString("activitySuccessFlag");
                        String activityMessage = jsonObject.getString("activityMessage");
                        Toast.makeText(OperationSchecule_IPD.this, "success"+activityMessage, Toast.LENGTH_SHORT).show();
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
