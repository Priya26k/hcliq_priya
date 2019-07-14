package com.example.hcliq_priya.activity_admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import java.sql.Time;
import java.text.ParseException;
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

public class TreatmentOperationSubmit extends AppCompatActivity {
    EditText editActDate,editActStartTime,editActEndTime,editSurgeonName,editAssistedBy,editSurgeryName,editAnaethetist,editPreOperationDiagnosis,editPostOperationDiagnosis,editProcedureInDetails,editOperativeFinding;
    TextView editSchDate,editSchStartTime,editSchEndTime,updatedBy,updatedOn,editTreatmentName,error;
    Button btn_Save;
    String patientNameValue,hospitalPatientId,patientImgPath,ipdSrlNo,userId="32";
    TextView textPatientName,textPatientId;
    CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_operation_submit);

        editSchDate=(TextView) findViewById(R.id.edit_sch_date);
        editSchStartTime=(TextView) findViewById(R.id.edit_sch_start_time);
        editSchEndTime=(TextView) findViewById(R.id.edit_sch_end_time);
        editActDate=(EditText)findViewById(R.id.edit_act_date);
        editActStartTime=(EditText)findViewById(R.id.edit_act_start_date);
        editActEndTime=(EditText)findViewById(R.id.edit_act_end_time);
        editTreatmentName=(TextView)findViewById(R.id.treatment_name);
        editSurgeonName=(EditText)findViewById(R.id.surgen_name);
        editAssistedBy=(EditText)findViewById(R.id.assisted_by);
        editAnaethetist=(EditText)findViewById(R.id.anaesthetist);
        editSurgeryName=(EditText)findViewById(R.id.surgery_name) ;
        editPreOperationDiagnosis=(EditText)findViewById(R.id.pre_operation_diagnosis);
        editPostOperationDiagnosis=(EditText)findViewById(R.id.post_operation_diagnosis);
        editProcedureInDetails=(EditText)findViewById(R.id.procedure_in_details);
        editOperativeFinding=(EditText)findViewById(R.id.operative_finding);
        textPatientName = (TextView)findViewById(R.id.text_patient_name);
        textPatientId = (TextView)findViewById(R.id.text_patient_id);
        circleImageView = (CircleImageView)findViewById(R.id.image);


        btn_Save=(Button)findViewById(R.id.button_Save);

        updatedBy=(TextView)findViewById(R.id.updated_by);
        updatedOn=(TextView)findViewById(R.id.updated_date);
        error=(TextView)findViewById(R.id.error);


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

        datepicker();
        getPatientOperationDetails();

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editSurgeryName.getText().toString().isEmpty()){
                    editSurgeryName.setError("can't be empty");
                } else if(editSurgeonName.getText().toString().isEmpty()) {
                    editSurgeonName.setError("can't be empty");
                } else if(editAnaethetist.getText().toString().isEmpty()){
                    editAnaethetist.setError("can't be empty");
                }else if(!checktimings(editActStartTime.getText().toString(),editActEndTime.getText().toString())){
                    error.setVisibility(View.VISIBLE);
                    error.setText("End time should be greater then start time");
                }else{
                    error.setVisibility(View.GONE);
                    managePatientOperationDetails();
                    Intent intent = new Intent(TreatmentOperationSubmit.this,PatientIPDHome.class);
                    startActivity(intent);
                }
            }


        });
    }

    public void datepicker() {

        final SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String currentDate=sdf.format(new Date());
        final Calendar myCalendar=Calendar.getInstance();
        final Calendar myCalendar1=Calendar.getInstance();
        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                editSchDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, month);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

            private void updateLabel1() {
                editActDate.setText(sdf.format(myCalendar1.getTime()));
            }
        };

        editActDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TreatmentOperationSubmit.this, date1, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        editActStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog mTimePicker = new TimePickerDialog(TreatmentOperationSubmit.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editActStartTime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
                mTimePicker.show();

                editActEndTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TimePickerDialog mTimePicker = new TimePickerDialog(TreatmentOperationSubmit.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                editActEndTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, true);
                        mTimePicker.show();




                    }
                });

            }
        });
    }

    private void getPatientOperationDetails(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in/").build();
        final ApiService apiService=retrofit.create(ApiService.class);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Data fetching from server");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<ResponseBody> call= apiService.getPatientOperationDetails("kH4J3RXsw5cBMKvpLEwTDUCVi", userId,"1",ipdSrlNo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String ipdSrlNo = jsonObject.getString("ipdSrlNo");
                    String ipdNo = jsonObject.getString("ipdNo");
                    String ipdOprSrlNo = jsonObject.getString("ipdOprSrlNo");
                    String treatmentName = jsonObject.getString("treatmentName");
                    String ipdOprName = jsonObject.getString("ipdOprName");
                    String ipdOprSchedDate = jsonObject.getString("ipdOprSchedDate");
                    String ipdOprSchedStTime = jsonObject.getString("ipdOprSchedStTime");
                    String ipdOprSchedEndTime= jsonObject.getString("ipdOprSchedEndTime");
                    String ipdOprActualDate = jsonObject.getString("ipdOprActualDate");
                    String ipdOprActualStTime = jsonObject.getString("ipdOprActualStTime");
                    String ipdOprActualEndTime = jsonObject.getString("ipdOprActualEndTime");
                    String ipdOprSurgonName = jsonObject.getString("ipdOprSurgonName");
                    String ipdOprAssistedBy = jsonObject.getString("ipdOprAssistedBy");
                    String ipdOprAnaesthetist = jsonObject.getString("ipdOprAnaesthetist");
                    String ipdPreOprDiagnosis = jsonObject.getString("ipdPreOprDiagnosis");
                    String ipdPostOprDiagnosis = jsonObject.getString("ipdPostOprDiagnosis");
                    String ipdOprProcedure = jsonObject.getString("ipdOprProcedure");
                    String ipdOprFindings = jsonObject.getString("ipdOprFindings");
                    String ipdOprCreationTime = jsonObject.getString("ipdOprCreationTime");
                    String ipdOprCreatedByUser= jsonObject.getString("ipdOprCreatedByUser");
                    String ipdOprDetailEditFlag = jsonObject.getString("ipdOprDetailEditFlag");
                    progressDialog.dismiss();

                    editTreatmentName.setText(treatmentName);
                    editSchDate.setText(ipdOprSchedDate);
                    editSchStartTime.setText(ipdOprSchedStTime);
                    editSchEndTime.setText(ipdOprSchedEndTime);
                    editActDate.setText(ipdOprActualDate);
                    editActStartTime.setText(ipdOprActualStTime);
                    editActEndTime.setText(ipdOprActualEndTime);
                    editSurgeonName.setText(ipdOprSurgonName);
                    editAssistedBy.setText(ipdOprAssistedBy);
                    editAnaethetist.setText(ipdOprAnaesthetist);
                    editPreOperationDiagnosis.setText(ipdPreOprDiagnosis);
                    editPostOperationDiagnosis.setText(ipdPostOprDiagnosis);
                    editProcedureInDetails.setText(ipdOprProcedure);
                    editOperativeFinding.setText(ipdOprFindings);
                    updatedBy.setText(ipdOprCreatedByUser);
                    updatedOn.setText(ipdOprCreationTime);
                    editSurgeryName.setText(ipdOprName);


                }

                catch (JSONException e) {
                    e.printStackTrace();
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }

        });

    }

    private void managePatientOperationDetails(){
            ApiService apiService = new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in").addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.class);
            Call<ResponseBody> call1= apiService.managePatientOperationDetails("kH4J3RXsw5cBMKvpLEwTDUCVi",
                    userId,
                    "1",
                    ipdSrlNo,
                    editSurgeryName.getText().toString(),
                    editActDate.getText().toString(),
                    editActStartTime.getText().toString(),
                    editActEndTime.getText().toString(),
                    editSurgeonName.getText().toString(),
                    editAssistedBy.getText().toString(),
                    editAnaethetist.getText().toString(),
                    editPreOperationDiagnosis.getText().toString(),
                    editPostOperationDiagnosis.getText().toString(),
                    editProcedureInDetails.getText().toString(),
                    editOperativeFinding.getText().toString());

        call1.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String activitySuccessFlag = jsonObject.getString("activitySuccessFlag");
                        String activityMessage = jsonObject.getString("activityMessage");
                        Toast.makeText(TreatmentOperationSubmit.this, "success"+activityMessage, Toast.LENGTH_SHORT).show();
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

    private boolean checktimings(String time, String endtime) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date startTime = sdf.parse(time);
            Date endTime = sdf.parse(endtime);

            if(startTime.before(endTime)) {
                return true;
            } else {
                return  false;

            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }


}

