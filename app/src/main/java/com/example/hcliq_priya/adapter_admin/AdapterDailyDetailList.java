package com.example.hcliq_priya.adapter_admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.model_admin.ModelDailyDetailList;

import java.util.ArrayList;

public class AdapterDailyDetailList extends RecyclerView.Adapter<AdapterDailyDetailList.ViewHolder>{

        private Context context;
        ArrayList<ModelDailyDetailList> dailyDetailList;

        public AdapterDailyDetailList(Context context, ArrayList<ModelDailyDetailList> dailyDetailList) {
            this.context = context;
            this.dailyDetailList = dailyDetailList;
        }

        @NonNull
        @Override
        public AdapterDailyDetailList.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detail_list,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AdapterDailyDetailList.ViewHolder viewHolder, int i) {
            ModelDailyDetailList modelDailyDetailList= dailyDetailList.get(i);

            viewHolder.textProcedure.setText(modelDailyDetailList.getProcedure());
            viewHolder.textTreatment.setText(modelDailyDetailList.getTreatment());
            viewHolder.textDate.setText(modelDailyDetailList.getDate());
            viewHolder.textUpdatedBy.setText("Updated by : "+modelDailyDetailList.getUpdatedby());
        }

        @Override
        public int getItemCount() {
            return dailyDetailList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView textDate,textTreatment,textUpdatedBy,textProcedure;
            public ViewHolder(@NonNull View view){
                super(view);
                textDate = (TextView)view.findViewById(R.id.text_date_created);
                textProcedure = (TextView)view.findViewById(R.id.text_procedure);
                textTreatment = (TextView)view.findViewById(R.id.text_treatment);
                textUpdatedBy = (TextView)view.findViewById(R.id.text_update);
            }
        }
}

