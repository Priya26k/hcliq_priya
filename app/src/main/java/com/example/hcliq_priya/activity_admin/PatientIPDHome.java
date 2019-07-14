package com.example.hcliq_priya.activity_admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hcliq_priya.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientIPDHome extends AppCompatActivity {
    CardView cardBedAlloc,cardUploadDiagReport,cardAdmissionSummary,cardHospitalStay,cardDischarge,cardOperationSch,cardOperationSummary,cardConsentLetter,cardIPDPatientProfile,cardPatientProfile;
    Animation blink,righttoleft,lefttoright,bounce,slidedown;
    CircleImageView imageBedAllocation,imageUploadDiagReport,imageAdmissionSummary,imageHospitalStay,imageDischarge,imageOperationSchedule,imageOperationSummary,imageConsentLetter,imagePatientIPDProfile,circleImageView;
    TextView patientName,patientId;
    String patientNameValue,hospitalPatientId,patientImgPath,ipdDischargeType,ipdNo,ipdStDateDesc,ipdEndDateDesc,ipdSrlNo,ipdClStatus,isOprtvTrtmtFlag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_ipd_home);

        patientName = (TextView)findViewById(R.id.text_patient_name);
        patientId = (TextView)findViewById(R.id.text_patient_id);

        cardBedAlloc = (CardView)findViewById(R.id.card_bed_alloc);
        cardUploadDiagReport = (CardView)findViewById(R.id.card_upload_diag_report);
        cardAdmissionSummary = (CardView)findViewById(R.id.card_admission_summary);
        cardHospitalStay = (CardView)findViewById(R.id.card_hospital_stay);
        cardDischarge = (CardView)findViewById(R.id.card_discharge);
        cardOperationSch = (CardView)findViewById(R.id.card_operation_sch);
        cardOperationSummary = (CardView)findViewById(R.id.card_operation_summary);
        cardConsentLetter = (CardView)findViewById(R.id.card_consent_letter);
        cardIPDPatientProfile = (CardView)findViewById(R.id.card_ipd_patient_profile);
        cardPatientProfile = (CardView)findViewById(R.id.card_patient_profile);

        imageBedAllocation = (CircleImageView)findViewById(R.id.image_bed_allocation);
        imageUploadDiagReport = (CircleImageView)findViewById(R.id.image_upload_diag_report);
        imageAdmissionSummary = (CircleImageView)findViewById(R.id.image_admission_summary);
        imageHospitalStay = (CircleImageView)findViewById(R.id.image_hospital_stay);
        imageDischarge = (CircleImageView)findViewById(R.id.image_discharge);
        imageOperationSchedule = (CircleImageView)findViewById(R.id.image_operation_schedule);
        imageOperationSummary = (CircleImageView)findViewById(R.id.image_operation_summary);
        imageConsentLetter = (CircleImageView)findViewById(R.id.image_consent_letter);
        imagePatientIPDProfile = (CircleImageView)findViewById(R.id.image_patient_ipd_profile);
        circleImageView = (CircleImageView)findViewById(R.id.circle_image_view);

        blink = AnimationUtils.loadAnimation(PatientIPDHome.this,R.anim.blink_anim);
        righttoleft = AnimationUtils.loadAnimation(PatientIPDHome.this,R.anim.righttoleft);
        lefttoright = AnimationUtils.loadAnimation(PatientIPDHome.this,R.anim.lefttoright);
        bounce = AnimationUtils.loadAnimation(PatientIPDHome.this,R.anim.bounce);
        slidedown = AnimationUtils.loadAnimation(PatientIPDHome.this,R.anim.slide_down);

        cardIPDPatientProfile.startAnimation(lefttoright);
        cardBedAlloc.startAnimation(righttoleft);
        cardUploadDiagReport.startAnimation(righttoleft);
        cardAdmissionSummary.startAnimation(lefttoright);
        cardHospitalStay.startAnimation(lefttoright);
        cardDischarge.startAnimation(righttoleft);
        cardOperationSch.startAnimation(lefttoright);
        cardOperationSummary.startAnimation(righttoleft);
        cardConsentLetter.startAnimation(lefttoright);
        cardPatientProfile.startAnimation(slidedown);

        Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/appicon/bedAllocation.png").fit().into(imageBedAllocation);
        Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/appicon/diagnosticReports.png").fit().into(imageUploadDiagReport);
        Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/appicon/addmissionSummary.png").fit().into(imageAdmissionSummary);
        Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/appicon/hospitalStay.png").fit().into(imageHospitalStay);
        Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/appicon/discharge.png").fit().into(imageDischarge);
        Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/appicon/operationSchedule.png").fit().into(imageOperationSchedule);
        Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/appicon/operationSummary.png").fit().into(imageOperationSummary);
        Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/appicon/consentLetter.png").fit().into(imageConsentLetter);
        Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/appicon/addmissionSummary.png").fit().into(imagePatientIPDProfile);

        patientNameValue = getIntent().getStringExtra("patientName");
        hospitalPatientId = getIntent().getStringExtra("hospitalPatientId");
        patientImgPath = getIntent().getStringExtra("patientImgPath");
        ipdNo = getIntent().getStringExtra("ipdNo");
        ipdDischargeType = getIntent().getStringExtra("ipdDischargeType");
        ipdStDateDesc = getIntent().getStringExtra("ipdStDateDesc");
        ipdEndDateDesc = getIntent().getStringExtra("ipdEndDateDesc");
        ipdSrlNo = getIntent().getStringExtra("ipdSrlNo");
        ipdClStatus = getIntent().getStringExtra("ipdClStatus");
        isOprtvTrtmtFlag = getIntent().getStringExtra("isOprtvTrtmtFlag");

        patientName.setText(patientNameValue);
        patientId.setText("Patient #"+hospitalPatientId);
        if (patientImgPath.equals("")){
            Picasso.with(getApplicationContext()).load("http://api.wcss.co.in/Images/Patients/defualtUserImage.png").fit().into(circleImageView);
        } else {
            Picasso.with(getApplicationContext()).load(patientImgPath).fit().into(circleImageView);
        }

        if(ipdClStatus.equals("")){
            cardConsentLetter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PatientIPDHome.this, "Consent Letter is not required for this IPD", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            cardConsentLetter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PatientIPDHome.this,ConsentLetter.class);
                    intent.putExtra("ipdSrlNo",ipdSrlNo);
                    startActivity(intent);
                }
            });
        }
        if(isOprtvTrtmtFlag.equals("F")){
            cardOperationSummary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PatientIPDHome.this, "Operation Summary is not required for this IPD", Toast.LENGTH_LONG).show();
                }
            });
            cardOperationSch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PatientIPDHome.this, "Operation Schedule is not required for this IPD", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            cardOperationSummary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PatientIPDHome.this,TreatmentOperationSubmit.class);
                    intent.putExtra("patientName",patientNameValue);
                    intent.putExtra("hospitalPatientId",hospitalPatientId);
                    intent.putExtra("patientImgPath",patientImgPath);
                    intent.putExtra("ipdSrlNo",ipdSrlNo);
                    startActivity(intent);
                }
            });

            cardOperationSch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PatientIPDHome.this,OperationSchecule_IPD.class);
                    intent.putExtra("patientName",patientNameValue);
                    intent.putExtra("hospitalPatientId",hospitalPatientId);
                    intent.putExtra("patientImgPath",patientImgPath);
                    intent.putExtra("ipdSrlNo",ipdSrlNo);
                    startActivity(intent);
                }
            });
        }
        cardIPDPatientProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientIPDHome.this,PatientIPDProfile.class);
                intent.putExtra("patientName",patientNameValue);
                intent.putExtra("hospitalPatientId",hospitalPatientId);
                intent.putExtra("ipdNo",ipdNo);
                intent.putExtra("ipdDischargeType",ipdDischargeType);
                intent.putExtra("patientImgPath",patientImgPath);
                intent.putExtra("ipdStDateDesc",ipdStDateDesc);
                intent.putExtra("ipdEndDateDesc",ipdEndDateDesc);
                intent.putExtra("ipdSrlNo",ipdSrlNo);
                startActivity(intent);
            }
        });
        cardUploadDiagReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientIPDHome.this,IpdDiagnosisReport.class);
                intent.putExtra("ipdSrlNo",ipdSrlNo);
                startActivity(intent);
            }
        });
        cardDischarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientIPDHome.this,TreatmentDischarge.class);
                intent.putExtra("patientName",patientNameValue);
                intent.putExtra("hospitalPatientId",hospitalPatientId);
                intent.putExtra("patientImgPath",patientImgPath);
                intent.putExtra("ipdSrlNo",ipdSrlNo);
                startActivity(intent);
            }
        });
        cardAdmissionSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientIPDHome.this,AdmissionSummery.class);
                intent.putExtra("patientName",patientNameValue);
                intent.putExtra("hospitalPatientId",hospitalPatientId);
                intent.putExtra("patientImgPath",patientImgPath);
                intent.putExtra("ipdSrlNo",ipdSrlNo);
                startActivity(intent);
            }
        });

        cardHospitalStay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientIPDHome.this, TreatmentHospitalStay.class);
                intent.putExtra("patientName",patientNameValue);
                intent.putExtra("hospitalPatientId",hospitalPatientId);
                intent.putExtra("patientImgPath",patientImgPath);
                intent.putExtra("ipdSrlNo",ipdSrlNo);
                startActivity(intent);
            }
        });
        cardBedAlloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientIPDHome.this,BedAllocation.class);
                intent.putExtra("patientName",patientNameValue);
                intent.putExtra("hospitalPatientId",hospitalPatientId);
                intent.putExtra("patientImgPath",patientImgPath);
                intent.putExtra("ipdSrlNo",ipdSrlNo);
                startActivity(intent);
            }
        });

    }
}