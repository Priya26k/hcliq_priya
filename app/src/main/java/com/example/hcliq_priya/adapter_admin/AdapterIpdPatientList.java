package com.example.hcliq_priya.adapter_admin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import android.widget.Button;
import android.widget.TextView;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.activity_admin.ConsentLetter;
import com.example.hcliq_priya.activity_admin.PatientIPDHome;
import com.example.hcliq_priya.activity_admin.PatientIPDProfile;
import com.example.hcliq_priya.model_admin.ModelIpdPatientList;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterIpdPatientList extends RecyclerView.Adapter<AdapterIpdPatientList.ViewHolder> {
          private Context context;
          ArrayList<ModelIpdPatientList> appointmentPatientLists;
          public AdapterIpdPatientList(Context context, ArrayList<ModelIpdPatientList> adapterappointmentpatientlists){
              this.context=context;
              this.appointmentPatientLists = adapterappointmentpatientlists;
          }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewgroup, int i) {
        View view = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.item_ipd_patient_list, viewgroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ModelIpdPatientList modelappointmentpatientlist = appointmentPatientLists.get(i);
        viewHolder.ipdNo.setText("IPD No : "+modelappointmentpatientlist.getIpdNo());
        viewHolder.patientName.setText(modelappointmentpatientlist.getPatientName());
        viewHolder.hospitalPatientId.setText("Patient #"+modelappointmentpatientlist.getHospitalPatientId());
        viewHolder.ipdStDateDesc.setText("Start Date : "+modelappointmentpatientlist.getIpdStDateDesc());
        viewHolder.ipdEndDateDesc.setText("End Date : "+modelappointmentpatientlist.getIpdEndDateDesc());
        viewHolder.ipdClStatus.setText("Consent Letter : "+modelappointmentpatientlist.getIpdClStatus());
        viewHolder.ipdBedNo.setText("Bed No : "+modelappointmentpatientlist.getFacilityBedDesc());
        viewHolder.ipdWardNo.setText("Ward No : "+modelappointmentpatientlist.getFacilityUnitName());
        if (modelappointmentpatientlist.getPatientImgPath().equals("")){
            Picasso.with(context).load("http://api.wcss.co.in/Images/Patients/defualtUserImage.png").fit().into(viewHolder.circleimageView);
        } else {

            Picasso.with(context).load(modelappointmentpatientlist.getPatientImgPath()).fit().into(viewHolder.circleimageView);
        }

        if (modelappointmentpatientlist.getPatientPaymentStatus().equals("1")){
            viewHolder.patientPaymentStatus.setText("Payment Status : Unpaid");
        }

        if (modelappointmentpatientlist.getPatientPaymentStatus().equals("2")){
            viewHolder.patientPaymentStatus.setText("Payment Status : Partially Paid");
        }

        if (modelappointmentpatientlist.getPatientPaymentStatus().equals("3")){
            viewHolder.patientPaymentStatus.setText("Payment Status : Paid");
        }
        if(modelappointmentpatientlist.getIpdClStatus().equals("")){

            viewHolder.buttonSignConsentLetter.setVisibility(View.GONE);
            viewHolder.ipdClStatus.setVisibility(View.GONE);
            viewHolder.circleImageViewTick.setVisibility(View.GONE);

        }else  if(modelappointmentpatientlist.getIpdClStatus().equals("F")){
            viewHolder.ipdClStatus.setText("Consent Letter : Unsigned");
            viewHolder.circleImageViewTick.setVisibility(View.GONE);
            viewHolder.buttonSignConsentLetter.setVisibility(View.GONE);
        }
        else {
            viewHolder.ipdClStatus.setText("Consent Letter");
            viewHolder.circleImageViewTick.setVisibility(View.VISIBLE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PatientIPDHome.class);
                intent.putExtra("patientName",modelappointmentpatientlist.getPatientName());
                intent.putExtra("hospitalPatientId",modelappointmentpatientlist.getHospitalPatientId());
                intent.putExtra("patientImgPath",modelappointmentpatientlist.getPatientImgPath());
                intent.putExtra("ipdNo",modelappointmentpatientlist.getIpdNo());
                intent.putExtra("ipdDischargeType",modelappointmentpatientlist.getIpdDischargeType());
                intent.putExtra("ipdStDateDesc",modelappointmentpatientlist.getIpdStDateDesc());
                intent.putExtra("ipdEndDateDesc",modelappointmentpatientlist.getIpdEndDateDesc());
                intent.putExtra("ipdSrlNo",modelappointmentpatientlist.getIpdSrlNo());
                intent.putExtra("ipdClStatus",modelappointmentpatientlist.getIpdClStatus());
                intent.putExtra("isOprtvTrtmtFlag",modelappointmentpatientlist.getIsOprtvTrtmtFlag());
                context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

            }
        });
        viewHolder.buttonSignConsentLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConsentLetter.class);
                context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

    }

    @Override
    public int getItemCount() {
        return appointmentPatientLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleimageView,circleImageViewTick;
        TextView patientName, hospitalPatientId, ipdNo, ipdStDateDesc, ipdEndDateDesc, patientPaymentStatus, ipdClStatus,ipdBedNo,ipdWardNo;
        Button buttonAllocateBed,buttonSignConsentLetter;
        public ViewHolder(@NonNull View itemview) {
            super(itemview);
            circleimageView = (CircleImageView) itemView.findViewById(R.id.imageView);
            circleImageViewTick = (CircleImageView)itemview.findViewById(R.id.circle_image_view_tick);
            patientName = (TextView) itemView.findViewById(R.id.patientName);
            hospitalPatientId = (TextView) itemView.findViewById(R.id.hospitalPatientId);
            ipdNo = (TextView) itemview.findViewById(R.id.ipdNo);
            ipdStDateDesc = (TextView) itemview.findViewById(R.id.ipdStDateDesc);
            ipdEndDateDesc = (TextView) itemview.findViewById(R.id.ipdEndDateDesc);
            patientPaymentStatus = (TextView) itemview.findViewById(R.id.patient_payment_status);
            ipdClStatus = (TextView) itemview.findViewById(R.id.ipd_cl_status);
            ipdBedNo = (TextView)itemview.findViewById(R.id.text_bed_no);
            ipdWardNo = (TextView)itemview.findViewById(R.id.text_ward_no);
            buttonSignConsentLetter=(Button)itemview.findViewById(R.id.button_sign_consent_letter);
        }
    }

    public void filterList(ArrayList<ModelIpdPatientList> filteredList){
        appointmentPatientLists= filteredList;
        notifyDataSetChanged();
    }
}


