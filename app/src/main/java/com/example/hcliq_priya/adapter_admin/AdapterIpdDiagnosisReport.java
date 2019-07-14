package com.example.hcliq_priya.adapter_admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.model_admin.ModelIpdDiagnosisReport;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterIpdDiagnosisReport extends RecyclerView.Adapter<AdapterIpdDiagnosisReport.ViewHolder> {
    private Context context;
    ArrayList<ModelIpdDiagnosisReport> ipdDiagnosisReports;
    public AdapterIpdDiagnosisReport(Context context, ArrayList<ModelIpdDiagnosisReport> arrayList) {
        this.context = context;
        this.ipdDiagnosisReports = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ipd_diagnosis_report,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final ModelIpdDiagnosisReport modelIpdDiagnosisReport = ipdDiagnosisReports.get(i);

        viewHolder.editedByName.setText(modelIpdDiagnosisReport.getIpdRptCreatedByUser());
        viewHolder.editedByDate.setText(modelIpdDiagnosisReport.getIpdRptCreationDate());
        viewHolder.nameOfReport.setText(modelIpdDiagnosisReport.getIpdRptName());
        viewHolder.removeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ipdDiagnosisReports.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, ipdDiagnosisReports.size());
            }
        });
        viewHolder.nameOfReport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                modelIpdDiagnosisReport.setIpdRptName(s.toString());
            }
        });


        viewHolder.checkBoxGeneral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    modelIpdDiagnosisReport.setIpdRptType("1");
                }
            }
        });

        viewHolder.checkBoxPostOperation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    modelIpdDiagnosisReport.setIpdRptType("2");
                }
            }
        });

        viewHolder.chckboxPreOperation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    modelIpdDiagnosisReport.setIpdRptType("3");
                }
            }
        });


        if (modelIpdDiagnosisReport.ipdRptType.equals("3")) {
            viewHolder.chckboxPreOperation.setChecked(true);
        } else {
            viewHolder.chckboxPreOperation.setChecked(false);
        }

        if (modelIpdDiagnosisReport.ipdRptType.equals("2")) {
            viewHolder.checkBoxPostOperation.setChecked(true);
        } else {
            viewHolder.checkBoxPostOperation.setChecked(false);
        }
        if (modelIpdDiagnosisReport.ipdRptType.equals("1")) {
            viewHolder.checkBoxGeneral.setChecked(true);
        }

        if (!modelIpdDiagnosisReport.getIpdRptFilePath().equals("")){
            viewHolder.thumbNail.setVisibility(View.VISIBLE);
            Picasso.with(context).load(modelIpdDiagnosisReport.getIpdRptFilePath()).fit().centerInside().into(viewHolder.thumbNail);
        } else {
            viewHolder.thumbNail.setVisibility(View.GONE);
        }

        viewHolder.thumbNail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Report Name Save", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return ipdDiagnosisReports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView removeImageView;
        EditText nameOfReport;
        TextView editedByDate,editedByName,reportName;
        CheckBox checkBoxPostOperation,chckboxPreOperation,checkBoxGeneral;
        ImageView thumbNail;
        ImageButton uploadButton;
        public ViewHolder(@NonNull View view){
            super(view);

            removeImageView=(CircleImageView)view.findViewById(R.id.remove);
            checkBoxPostOperation=(CheckBox) view.findViewById(R.id.checkbox_post_operation);
            chckboxPreOperation=(CheckBox) view.findViewById(R.id.checkbox_pre_operation);
            checkBoxGeneral=(CheckBox)view.findViewById(R.id.checkbox_general);
            nameOfReport=(EditText)view.findViewById(R.id.edit_report_name);
            editedByDate=(TextView)view.findViewById(R.id.text_created_date);
            editedByName=(TextView)view.findViewById(R.id.text_created_by);
            thumbNail=(ImageView)view.findViewById(R.id.thumbnail);
            uploadButton=(ImageButton)view.findViewById(R.id.pdf);
            reportName=(TextView)view.findViewById(R.id.file_name);
        }

    }

}
