package com.example.hcliq_priya.adapter_admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.model_admin.ModelPreOperationReport;

import java.util.ArrayList;

public class AdapterPreOpReport extends RecyclerView.Adapter<AdapterPreOpReport.ViewHolder>{

    private Context context;
    ArrayList<ModelPreOperationReport>preOperationReports;

    public AdapterPreOpReport(Context context, ArrayList<ModelPreOperationReport> preOperationReports) {
        this.context = context;
        this.preOperationReports = preOperationReports;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_preoperation_report,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ModelPreOperationReport modelPreOperationReport= preOperationReports.get(i);
        viewHolder.textPreOpReport.setText(modelPreOperationReport.getRptName());
        viewHolder.textPreOpReportDate.setText(modelPreOperationReport.getDate());
        viewHolder.textPreOpReportUpdatedBy.setText("Updated by : "+modelPreOperationReport.getUpdatedby());
    }

    @Override
    public int getItemCount() {
        return preOperationReports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textPreOpReportDate,textPreOpReport,textPreOpReportUpdatedBy;
        public ViewHolder(@NonNull View view){
            super(view);
            textPreOpReport = (TextView)view.findViewById(R.id.text_pre_op_report);
            textPreOpReportDate = (TextView)view.findViewById(R.id.text_pre_op_report_date);
            textPreOpReportUpdatedBy = (TextView)view.findViewById(R.id.text_pre_op_report_updated_by);
        }
    }
}
