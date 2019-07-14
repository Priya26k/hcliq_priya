package com.example.hcliq_priya.activity_admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.service.ApiService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConsentLetter extends AppCompatActivity {
    TextView textHeading1,textHeading2,textPara,textWitnessDeclaration,textDoctorDeclaration;
    CardView cardWitnessDeclaration,cardDoctorDeclaration;
    ImageView buttonSign,buttonWitnessSign,buttonDoctorSign,buttonWitness2Sign;
    String relationavlbl,useExistingSign="F",ipdSrlNo,userId="25";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent_letter);
        textHeading1 = (TextView)findViewById(R.id.text_heading);
        textHeading2 = (TextView)findViewById(R.id.text_heading2);
        textPara =(TextView)findViewById(R.id.text_para);
        textPara.setMovementMethod(new ScrollingMovementMethod());
        textWitnessDeclaration = (TextView)findViewById(R.id.text_Witness_Declaration);
        textDoctorDeclaration = (TextView)findViewById(R.id.text_Doctor_Declaration);
        cardWitnessDeclaration = (CardView)findViewById(R.id.cardview_witness_declaration);
        cardDoctorDeclaration = (CardView)findViewById(R.id.cardview_doctor_declaration);
        buttonSign = (ImageView) findViewById(R.id.button_sign);
        buttonWitnessSign = (ImageView) findViewById(R.id.button_witness_sign);
        buttonWitness2Sign = (ImageView) findViewById(R.id.button_witness2_sign);
        buttonDoctorSign = (ImageView) findViewById(R.id.button_doctor_sign);

        ipdSrlNo = getIntent().getStringExtra("ipdSrlNo");

        patientConsentLetterDetails();

    }

    public void patientConsentLetterDetails(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://testapi.wcss.co.in/").build();
        final ApiService apiService = retrofit.create(ApiService.class);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Data fetching from server");
        progressDialog.setMessage("Please wait...");

        Call<ResponseBody> call= apiService.getPatientConsentLetterDetails("kH4J3RXsw5cBMKvpLEwTDUCVi",
                userId,"1",ipdSrlNo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //Toast.makeText(ConsentLetter.this, ""+response.body().string(), Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String ipdSrlNo = jsonObject.getString("ipdSrlNo");
                    String ipdNo = jsonObject.getString("ipdNo");
                    String ipdStDate = jsonObject.getString("ipdStDate");
                    String ipdEndDate = jsonObject.getString("ipdEndDate");
                    String clId = jsonObject.getString("clId");
                    String clHeading1 = jsonObject.getString("clHeading1");
                    String clHeading2 = jsonObject.getString("clHeading2");
                    String clPara1 = jsonObject.getString("clPara1");
                    String clPara2 = jsonObject.getString("clPara2");
                    String clPara3 = jsonObject.getString("clPara3");
                    String clPara4 = jsonObject.getString("clPara4");
                    final String ipdClGuardianSgntrPath = jsonObject.getString("ipdClGuardianSgntrPath");
                    String ipdClGuardianSgntrTime = jsonObject.getString("ipdClGuardianSgntrTime");
                    String ipdClGuardianSgntrRelation = jsonObject.getString("ipdClGuardianSgntrRelation");
                    final String ipdClWitness1SgntrPath = jsonObject.getString("ipdClWitness1SgntrPath");
                    final String clWitnessReq = jsonObject.getString("clWitnessReq");
                    String clWitnessDclrn = jsonObject.getString("clWitnessDclrn");
                    String ipdClWitness1SgntrTime = jsonObject.getString("ipdClWitness1SgntrTime");
                    final String clWitness2Req = jsonObject.getString("clWitness2Req");
                    final String ipdClWitness2SgntrPath = jsonObject.getString("ipdClWitness2SgntrPath");
                    String ipdClWitness2SgntrTime = jsonObject.getString("ipdClWitness2SgntrTime");
                    String ipdDoctorSgntrPath = jsonObject.getString("ipdDoctorSgntrPath");
                    String clDocReq = jsonObject.getString("clDocReq");
                    String clDocDclrn = jsonObject.getString("clDocDclrn");
                    String ipdDoctorSgntrTime = jsonObject.getString("ipdDoctorSgntrTime");
                    String signatureAvlbl = jsonObject.getString("signatureAvlbl");
                    String isDoctorFlag = jsonObject.getString("isDoctorFlag");
                    final String savedSignaturePath = jsonObject.getString("savedSignaturePath");
                    final String doctorName = jsonObject.getString("doctorName");

                    progressDialog.dismiss();

                    textHeading1.setText(clHeading1);
                    textHeading2.setText(clHeading2);
                    textPara.setText(clPara1+"\n\n"+clPara2+"\n\n"+clPara3+"\n\n"+clPara4);
                    textWitnessDeclaration.setText(clWitnessDclrn);
                    textDoctorDeclaration.setText(clDocDclrn);
                    Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/ConsentLetterSignature/defaultSign.png").fit().into(buttonDoctorSign);

                    if(ipdClWitness1SgntrPath.equals("")) {
                        Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/ConsentLetterSignature/defaultSign.png").fit().into(buttonWitnessSign);
                    } else {
                        Picasso.with(getApplicationContext()).load(ipdClWitness1SgntrPath).fit().into(buttonWitnessSign);
                    }
                    if(ipdClWitness2SgntrPath.equals("")) {
                        Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/ConsentLetterSignature/defaultSign.png").fit().into(buttonWitness2Sign);
                    } else {
                        Picasso.with(getApplicationContext()).load(ipdClWitness2SgntrPath).fit().into(buttonWitness2Sign);
                    }
                    if(ipdClGuardianSgntrPath.equals("")) {
                        Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/ConsentLetterSignature/defaultSign.png").fit().into(buttonSign);
                    } else {
                        Picasso.with(getApplicationContext()).load(ipdClGuardianSgntrPath).fit().into(buttonSign);
                    }
                    buttonSign.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ConsentLetter.this,SignatureScreen1.class);
                            intent.putExtra("relationavlbl","GuardianSign");
                            intent.putExtra("ipdClGuardianSgntrPath",ipdClGuardianSgntrPath);
                            startActivity(intent);
                        }
                    });
                        if(clWitnessReq.equals("T")){
                        cardWitnessDeclaration.setVisibility(View.VISIBLE);
                        buttonWitnessSign.setVisibility(View.VISIBLE);

                        buttonWitnessSign.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ConsentLetter.this,SignatureScreen1.class);
                                    intent.putExtra("relationavlbl","Witness1Sign");
                                    intent.putExtra("ipdClWitness1SgntrPath",ipdClWitness1SgntrPath);
                                    startActivity(intent);
                                }
                            });
                    }
                        if(clWitness2Req.equals("T")){
                        cardWitnessDeclaration.setVisibility(View.VISIBLE);
                        buttonWitness2Sign.setVisibility(View.VISIBLE);

                        buttonWitness2Sign.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ConsentLetter.this,SignatureScreen1.class);
                                    intent.putExtra("relationavlbl","Witness2Sign");
                                    intent.putExtra("ipdClWitness2SgntrPath",ipdClWitness2SgntrPath);
                                    startActivity(intent);
                                }
                            });
                    }
                        if(clWitnessReq.equals("T")&&clWitness2Req.equals("T")){
                        cardWitnessDeclaration.setVisibility(View.VISIBLE);
                        buttonWitnessSign.setVisibility(View.VISIBLE);
                        buttonWitness2Sign.setVisibility(View.VISIBLE);

                        buttonWitnessSign.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ConsentLetter.this,SignatureScreen1.class);
                                    intent.putExtra("relationavlbl","Witness1Sign");
                                    intent.putExtra("ipdClWitness1SgntrPath",ipdClWitness1SgntrPath);
                                    startActivity(intent);
                                }
                            });
                        buttonWitness2Sign.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ConsentLetter.this,SignatureScreen1.class);
                                    intent.putExtra("relationavlbl","Witness2Sign");
                                    intent.putExtra("ipdClWitness2SgntrPath",ipdClWitness2SgntrPath);
                                    startActivity(intent);
                                }
                            });

                    } else {
                        cardWitnessDeclaration.setVisibility(View.GONE);
                    }
                    if(isDoctorFlag.equals("T")) {
                        cardDoctorDeclaration.setVisibility(View.VISIBLE);
                        if (signatureAvlbl.equals("T")) {
                            buttonDoctorSign.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (useExistingSign.equals("F")) {
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(ConsentLetter.this);
                                        View view = LayoutInflater.from(ConsentLetter.this).inflate(R.layout.layout_doctor_signature, null);
                                        builder.setView(view);
                                        final AlertDialog alertDialog = builder.create();
                                        builder.setCancelable(false);
                                        ImageView imageView = (ImageView) view.findViewById(R.id.image);
                                        Picasso.with(getApplicationContext()).load(savedSignaturePath).fit().into(imageView);
                                        builder.setPositiveButton("Use this", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                Picasso.with(getApplicationContext()).load(savedSignaturePath).fit().into(buttonDoctorSign);
                                                useExistingSign = "T";
                                            }
                                        });
                                        builder.setNegativeButton("New Signature", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                Intent intent = new Intent(ConsentLetter.this, SignatureScreen1.class);
                                                intent.putExtra("doctorName",doctorName);
                                                intent.putExtra("relationavlbl", "DoctorSign");
                                                startActivity(intent);
                                            }
                                        });
                                        builder.show();
                                    } else {
                                        buttonDoctorSign.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(ConsentLetter.this, SignatureScreen1.class);
                                                intent.putExtra("relationavlbl", "DoctorSigned");
                                                intent.putExtra("savedSignaturePath", savedSignaturePath);
                                                intent.putExtra("doctorName",doctorName);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }
                            });
                        } else if(signatureAvlbl.equals("F")){
                            buttonDoctorSign.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ConsentLetter.this,SignatureScreen1.class);
                                    intent.putExtra("relationavlbl","DoctorSign");
                                    intent.putExtra("savedSignaturePath",savedSignaturePath);
                                    startActivity(intent);
                                }
                            });
                        }
                    } else {
                        cardDoctorDeclaration.setVisibility(View.GONE);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ConsentLetter.this, "JSONException"+e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (
                        IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ConsentLetter.this, "IOException"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ConsentLetter.this, "Failure"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
