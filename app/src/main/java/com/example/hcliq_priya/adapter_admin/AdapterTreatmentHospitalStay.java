package com.example.hcliq_priya.adapter_admin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.activity_admin.TreatmentAddHospitalStay;
import com.example.hcliq_priya.model_admin.ModelIpdPatientList;
import com.example.hcliq_priya.model_admin.ModelTreatmentHospitalStay;

import java.util.ArrayList;


public class AdapterTreatmentHospitalStay extends RecyclerView.Adapter<AdapterTreatmentHospitalStay.ViewHolder> {
    private Context context;
    ArrayList<ModelTreatmentHospitalStay> treatmentHospitalStays ;

    public AdapterTreatmentHospitalStay(Context context, ArrayList<ModelTreatmentHospitalStay> treatmentHospitalStays){
        this.context=context;
        this.treatmentHospitalStays=treatmentHospitalStays;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_treatment_hospital_stay,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final ModelTreatmentHospitalStay modelTreatmentHospitalStay = treatmentHospitalStays.get(i);

        viewHolder.ipdDlyDoctorNotes.setText(modelTreatmentHospitalStay.getIpdDlyDoctorNotes());
        viewHolder.ipdDlyTrtmt.setText(modelTreatmentHospitalStay.getIpdDlyTrtmt());
        viewHolder.ipdDlyPrcdr.setText(modelTreatmentHospitalStay.getIpdDlyPrcdr());
        viewHolder.textDate.setText(modelTreatmentHospitalStay.getIpdDlyDateDesc());

        if (modelTreatmentHospitalStay.getIpdOperationName().isEmpty()) {

            viewHolder.linearLayout.setVisibility(View.GONE);

        }else{
            viewHolder.linearLayout.setVisibility(View.VISIBLE);
        }
        viewHolder.createdBy.setText(modelTreatmentHospitalStay.getDlyDetailCreatedByUserName());
        viewHolder.ipdOperationName.setText(modelTreatmentHospitalStay.getIpdOperationName());
        viewHolder.ipdOperationStartTime.setText(modelTreatmentHospitalStay.getIpdOperationStartTime());
        viewHolder.ipdOperationEndTime.setText(modelTreatmentHospitalStay.getIpdOperationEndTime());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,TreatmentAddHospitalStay.class);

                intent.putExtra("ipdDlySrlNo",modelTreatmentHospitalStay.getIpdDlySrlNo());
                intent.putExtra("ipdDlyDateDesc",modelTreatmentHospitalStay.getIpdDlyDateDesc());
                intent.putExtra("ipdDlyPrcdr",modelTreatmentHospitalStay.getIpdDlyPrcdr());
                intent.putExtra("ipdDlyTrtmt",modelTreatmentHospitalStay.getIpdDlyTrtmt());
                intent.putExtra("ipdDlyDoctorNotes",modelTreatmentHospitalStay.getIpdDlyDoctorNotes());
                intent.putExtra("hospitalStayStartTime",modelTreatmentHospitalStay.getIpdOperationStartTime());
                intent.putExtra("hospitalStayEndTime",modelTreatmentHospitalStay.getIpdOperationEndTime());
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return treatmentHospitalStays.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView textDate,  ipdDlyPrcdr,ipdDlyTrtmt,createdBy,ipdDlyDoctorNotes,dlyDetailCreatedByUserName, ipdOperationName, ipdOperationStartTime, ipdOperationEndTime;
        LinearLayout linearLayout,linearLayout1;
        public ViewHolder(@NonNull View itemView ){
            super(itemView);
            textDate=(TextView)itemView.findViewById(R.id.text_hospital_stay_date);
            ipdDlyPrcdr=(TextView)itemView.findViewById(R.id.text_procedure_details);
            ipdDlyTrtmt=(TextView)itemView.findViewById(R.id.text_treatment_details);
            ipdDlyDoctorNotes = (TextView)itemView.findViewById(R.id.text_doctor_note);
            ipdOperationName = (TextView)itemView.findViewById(R.id.text_op_name);
            ipdOperationStartTime = (TextView)itemView.findViewById(R.id.text_start_time);
            ipdOperationEndTime = (TextView)itemView.findViewById(R.id.text_end_time);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
            linearLayout1=(LinearLayout)itemView.findViewById(R.id.linear_layout1);
            createdBy = (TextView)itemView.findViewById(R.id.text_created_by);
        }
    }

}
