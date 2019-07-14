package com.example.hcliq_priya.adapter_admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.model_admin.ModelDoctorSelection;

import java.util.ArrayList;

public class AdapterDoctorSelection extends RecyclerView.Adapter<AdapterDoctorSelection.ViewHolder>{

    private Context context;
    ArrayList<ModelDoctorSelection> doctorSelections;

    public AdapterDoctorSelection(Context context, ArrayList<ModelDoctorSelection> doctorSelections) {
        this.context = context;
        this.doctorSelections = doctorSelections;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_doctor_selection,viewGroup,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ModelDoctorSelection modelDoctorSelection = doctorSelections.get(i);
        viewHolder.checkBoxDoctorSelection.setText(modelDoctorSelection.getDoctorId());
        viewHolder.checkBoxDoctorSelection.setText(modelDoctorSelection.getDoctorName());
        if (modelDoctorSelection.getDoctorSelectionFlag().equals("T")){
            viewHolder.checkBoxDoctorSelection.setChecked(true);
        } else {
            viewHolder.checkBoxDoctorSelection.setChecked(false);
        }
        viewHolder.checkBoxDoctorSelection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    modelDoctorSelection.setDoctorSelectionFlag("T");
                } else {
                    modelDoctorSelection.setDoctorSelectionFlag("F");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorSelections.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBoxDoctorSelection;
        public ViewHolder (@NonNull View view){
            super(view);
            checkBoxDoctorSelection = (CheckBox)view.findViewById(R.id.checkbox_doctor_selection);
        }
    }
}
