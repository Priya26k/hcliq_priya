package com.example.hcliq_priya.activity_admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.adapter_admin.AdapterConsentLetterMaster;
import com.example.hcliq_priya.interfaces.OnItemPopInListener;
import com.example.hcliq_priya.model_admin.ModelConsentLetterMaster;
import com.example.hcliq_priya.service.ApiService;

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

public class ConsentLetterMaster extends AppCompatActivity implements OnItemPopInListener {
    ArrayList<ModelConsentLetterMaster> letterMasters = new ArrayList();
    ArrayList<ModelConsentLetterMaster> selectedData = new ArrayList();
    EditText editpara1,editpara2,editpara3,editpara4,editValidFrom,editHeading,editHeading2,editWitnessDeclaration,editDoctorDeclaration;
    Boolean flag1=true,flag2=true,flag3=true,flag4=true;
    TextView textPara1Length,textPara2Length,textPara3Length,textPara4Length;
    String edittextDetectField = "0",userId;
    RecyclerView recyclerViewSuggestions,recyclerViewSuggestions2,recyclerViewSuggestions3,recyclerViewSuggestions4;
    CircleImageView circleImageView,circleImageView2,circleImageView3,circleImageView4;
    Button btnSave,btnPreview;
    CheckBox checkBoxDoctorDeclaration,checkBoxWitnessDeclaration;
    String doctorDeclarationValue="F",witnessDeclarationValue="F";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent_letter_master);

        editValidFrom=(EditText) findViewById(R.id.edit_valid_from);
        editHeading=(EditText) findViewById(R.id.edit_heading);
        editHeading2=(EditText) findViewById(R.id.edit_heading2);
        editWitnessDeclaration=(EditText) findViewById(R.id.edit_Witness_Declaration);
        editDoctorDeclaration=(EditText) findViewById(R.id.edit_Doctor_Declaration);

        textPara1Length=(TextView)findViewById(R.id.text_para1_length);
        textPara2Length=(TextView)findViewById(R.id.text_para2_length);
        textPara3Length=(TextView)findViewById(R.id.text_para3_length);
        textPara4Length=(TextView)findViewById(R.id.text_para4_length);

        recyclerViewSuggestions=(RecyclerView)findViewById(R.id.recycler_view_suggestions);
        recyclerViewSuggestions2=(RecyclerView)findViewById(R.id.recycler_view_suggestions2);
        recyclerViewSuggestions3=(RecyclerView)findViewById(R.id.recycler_view_suggestions3);
        recyclerViewSuggestions4=(RecyclerView)findViewById(R.id.recycler_view_suggestions4);

        circleImageView=(CircleImageView)findViewById(R.id.circle_image_view);
        circleImageView2=(CircleImageView)findViewById(R.id.circle_image_view2);
        circleImageView3=(CircleImageView)findViewById(R.id.circle_image_view3);
        circleImageView4=(CircleImageView)findViewById(R.id.circle_image_view4);

        editpara1=(EditText)findViewById(R.id.edit_para1);
        editpara2=(EditText)findViewById(R.id.edit_para2);
        editpara3=(EditText)findViewById(R.id.edit_para3);
        editpara4=(EditText)findViewById(R.id.edit_para4);
        editpara1.setMovementMethod(new ScrollingMovementMethod());
        editpara1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editpara1.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });
        editpara2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editpara2.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });
        editpara3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editpara3.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });
        editpara4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editpara4.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });

        btnSave=(Button)findViewById(R.id.button_Save) ;

        checkBoxDoctorDeclaration=(CheckBox)findViewById(R.id.checkbox_doctor_declaration);
        checkBoxWitnessDeclaration=(CheckBox)findViewById(R.id.checkbox_witness_declaration);

        letterMasters.add(new ModelConsentLetterMaster("Patient Name"));
        letterMasters.add(new ModelConsentLetterMaster("Patient Age"));
        letterMasters.add(new ModelConsentLetterMaster("Relationship"));
        letterMasters.add(new ModelConsentLetterMaster("Guardian Name"));
        letterMasters.add(new ModelConsentLetterMaster("Doctor Name"));
        letterMasters.add(new ModelConsentLetterMaster("Hospital Name"));
        letterMasters.add(new ModelConsentLetterMaster("Treatment Name"));
        letterMasters.add(new ModelConsentLetterMaster("Hospital Address"));
        letterMasters.add(new ModelConsentLetterMaster("Patient Address"));
        letterMasters.add(new ModelConsentLetterMaster("Date"));

        AdapterConsentLetterMaster adapterConsentLetterMaster = new AdapterConsentLetterMaster(getApplicationContext(), letterMasters);
        recyclerViewSuggestions.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewSuggestions.setAdapter(adapterConsentLetterMaster);
        recyclerViewSuggestions2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewSuggestions2.setAdapter(adapterConsentLetterMaster);
        recyclerViewSuggestions3.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewSuggestions3.setAdapter(adapterConsentLetterMaster);
        recyclerViewSuggestions4.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewSuggestions4.setAdapter(adapterConsentLetterMaster);
        adapterConsentLetterMaster.setOnItemPopInListener(this);

        final SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String currentDate=sdf.format(new Date());
        editValidFrom.setText(currentDate);

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
            editValidFrom.setText(sdf.format(myCalendar.getTime()));
            }
        };

        checkBoxDoctorDeclaration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    doctorDeclarationValue = "T";
                    editDoctorDeclaration.setVisibility(View.VISIBLE);
                } else {
                    editDoctorDeclaration.setVisibility(View.GONE);
                    doctorDeclarationValue = "F";
                }
            }
        });

        checkBoxWitnessDeclaration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                   witnessDeclarationValue = "T";
                    editWitnessDeclaration.setVisibility(View.VISIBLE);
                } else {
                    editWitnessDeclaration.setVisibility(View.GONE);
                    witnessDeclarationValue = "F";
                }
            }
        });

       editValidFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(ConsentLetterMaster.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


       editpara1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               edittextDetectField = "1";
           }
       });

        editpara2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edittextDetectField = "2";
            }
        });

        editpara3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edittextDetectField = "3";
            }
        });

        editpara4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edittextDetectField = "4";
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEditConsentLetterTemplate();
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               edittextDetectField = "1";
                if(flag1){
                   recyclerViewSuggestions.setVisibility(View.VISIBLE);
                    Animation animation= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.slide_in);
                    recyclerViewSuggestions.startAnimation(animation);
                    animation.setDuration(500);
                    Animation animation1= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.mixed_anim);
                    circleImageView.startAnimation(animation1);
                    animation1.setDuration(500);
                    flag1=false;
               }
                else {
                     Animation animation= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.slide_out);
                     recyclerViewSuggestions.startAnimation(animation);
                     animation.setDuration(500);
                     Animation animation1= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.mixed_anim1);
                     circleImageView.startAnimation(animation1);
                     animation1.setDuration(500);
                     recyclerViewSuggestions.setVisibility(View.GONE);
                     flag1=true;
                }
           }
       });

        circleImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittextDetectField = "2";
               if(flag2){
                    recyclerViewSuggestions2.setVisibility(View.VISIBLE);
                    Animation animation= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.slide_in);
                    recyclerViewSuggestions2.startAnimation(animation);
                    animation.setDuration(500);
                     Animation animation1= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.mixed_anim);
                     circleImageView2.startAnimation(animation1);
                     animation1.setDuration(500);
                     flag2=false;
                }
               else {
                    Animation animation= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.slide_out);
                    recyclerViewSuggestions2.startAnimation(animation);
                    animation.setDuration(500);
                    Animation animation1= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.mixed_anim1);
                    circleImageView2.startAnimation(animation1);
                    animation1.setDuration(500);
                    recyclerViewSuggestions2.setVisibility(View.GONE);
                    flag2=true;
                }
            }
        });

        circleImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittextDetectField = "3";

                if(flag3){
                   recyclerViewSuggestions3.setVisibility(View.VISIBLE);
                    Animation animation= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.slide_in);
                    recyclerViewSuggestions3.startAnimation(animation);
                    animation.setDuration(500);
                    Animation animation1= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.mixed_anim);
                    circleImageView3.startAnimation(animation1);
                    animation1.setDuration(500);
                    flag3=false;

               }
               else {
                    Animation animation= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.slide_out);
                    recyclerViewSuggestions3.startAnimation(animation);
                   animation.setDuration(500);
                   Animation animation1= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.mixed_anim1);
                    circleImageView3.startAnimation(animation1);
                    animation1.setDuration(500);
                   recyclerViewSuggestions3.setVisibility(View.GONE);
                    flag3=true;
                }
            }
        });

        circleImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittextDetectField = "4";

                if(flag4){
                    recyclerViewSuggestions4.setVisibility(View.VISIBLE);
                    Animation animation= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.slide_in);
                    recyclerViewSuggestions4.startAnimation(animation);
                    animation.setDuration(500);
                    Animation animation1= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.mixed_anim);
                    circleImageView4.startAnimation(animation1);
                    animation1.setDuration(500);
                    flag4=false;

                }
                else {
                   Animation animation= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.slide_out);
                    recyclerViewSuggestions4.startAnimation(animation);
                    animation.setDuration(500);
                    Animation animation1= AnimationUtils.loadAnimation(ConsentLetterMaster.this,R.anim.mixed_anim1);
                    circleImageView4.startAnimation(animation1);
                    animation1.setDuration(500);
                    recyclerViewSuggestions4.setVisibility(View.GONE);
                    flag4=true;
                }
            }
        });

        editpara1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textPara1Length.setText(String.valueOf(500-s.toString().length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editpara2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textPara2Length.setText(String.valueOf(500-s.toString().length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editpara3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textPara3Length.setText(String.valueOf(500-s.toString().length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editpara4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textPara4Length.setText(String.valueOf(500-s.toString().length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in/").build();
        final ApiService apiService=retrofit.create(ApiService.class);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Data fetching from server");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<ResponseBody> call= apiService.getConsentLetterTemplate("kH4J3RXsw5cBMKvpLEwTDUCVi", "25","1");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String clValidFrom = jsonObject.getString("clValidFrom");
                    String clHeading1 = jsonObject.getString("clHeading1");
                    String clHeading2 = jsonObject.getString("clHeading2");
                    String clPara1 = jsonObject.getString("clPara1");
                    String clPara2 = jsonObject.getString("clPara2");
                    String clPara3 = jsonObject.getString("clPara3");
                    String clPara4 = jsonObject.getString("clPara4");
                    String clWitnessDclrn = jsonObject.getString("clWitnessDclrn");
                    String clWitnessReq= jsonObject.getString("clWitnessReq");
                    String clWitness2Req = jsonObject.getString("clWitness2Req");
                    String clDocReq = jsonObject.getString("clDocReq");
                    String clDocDclrn = jsonObject.getString("clDocDclrn");
                    progressDialog.dismiss();

                    editValidFrom.setText(clValidFrom);
                    editHeading.setText(clHeading1);
                    editHeading2.setText(clHeading2);
                    editpara1.setText(clPara1);
                    editpara2.setText(clPara2);
                    editpara3.setText(clPara3);
                    editpara4.setText(clPara4);
                    editWitnessDeclaration.setText(clWitnessDclrn);
                    editDoctorDeclaration.setText(clDocDclrn);

                    if (clDocReq.equals("T")){
                        checkBoxDoctorDeclaration.setChecked(true);
                    } else {
                        checkBoxDoctorDeclaration.setChecked(false);
                    }

                    if (clWitnessReq.equals("T")){
                        checkBoxWitnessDeclaration.setChecked(true);
                    } else {
                        checkBoxWitnessDeclaration.setChecked(false);
                    }
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
                Toast.makeText(ConsentLetterMaster.this, "on failure"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

   @Override
    public void setOnPop(int position) {
        if (edittextDetectField.equals("1")){
            ArrayList<String> collectedData = new ArrayList<>();
            collectedData.add(editpara1.getText().toString()+" " + " {{"+letterMasters.get(position).getPatient()+"}} ");
            editpara1.setText(collectedData.get(0));
            editpara1.setSelection(editpara1.getText().length());
        } else if (edittextDetectField.equals("2")){
            ArrayList<String> collectedData = new ArrayList<>();
            collectedData.add(editpara2.getText().toString()+" " + " {{"+letterMasters.get(position).getPatient()+"}} ");
            editpara2.setText(collectedData.get(0));
            editpara2.setSelection(editpara2.getText().length());
        } else if (edittextDetectField.equals("3")){
            ArrayList<String> collectedData = new ArrayList<>();
            collectedData.add(editpara3.getText().toString()+" " + " {{"+letterMasters.get(position).getPatient()+"}} ");
            editpara3.setText(collectedData.get(0));
            editpara3.setSelection(editpara3.getText().length());
        }  else if (edittextDetectField.equals("4")){
            ArrayList<String> collectedData = new ArrayList<>();
            collectedData.add(editpara4.getText().toString()+" " + " {{"+letterMasters.get(position).getPatient()+"}} ");
            editpara4.setText(collectedData.get(0));
            editpara4.setSelection(editpara4.getText().length());
        }
    }

    public void createEditConsentLetterTemplate(){
        ApiService apiService = new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in").addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.class);
        Call<ResponseBody> calll = apiService.createEditConsentLetterTemplate("kH4J3RXsw5cBMKvpLEwTDUCVi",
                userId,
                "1",
                editHeading.getText().toString(),
                editHeading2.getText().toString(),
                editpara1.getText().toString(),
                editpara2.getText().toString(),
                editpara3.getText().toString(),
                editpara4.getText().toString(),
                witnessDeclarationValue,
                editWitnessDeclaration.getText().toString(),
                "T",
                doctorDeclarationValue,
                editDoctorDeclaration.getText().toString(),
                editValidFrom.getText().toString());

        calll.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String activitySuccessFlag = jsonObject.getString("activitySuccessFlag");
                    String activityMessage = jsonObject.getString("activityMessage");

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
