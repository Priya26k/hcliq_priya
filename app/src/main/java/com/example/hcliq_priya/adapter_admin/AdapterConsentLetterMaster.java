package com.example.hcliq_priya.adapter_admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.interfaces.OnItemPopInListener;
import com.example.hcliq_priya.model_admin.ModelConsentLetterMaster;

import java.util.ArrayList;

    public class AdapterConsentLetterMaster extends RecyclerView.Adapter<AdapterConsentLetterMaster.ViewHolder> {
        private Context context;
        private ArrayList<ModelConsentLetterMaster> arrayList;


       private OnItemPopInListener mListener;
        public AdapterConsentLetterMaster(Context context, ArrayList<ModelConsentLetterMaster> arrayList){
            this.context=context;
            this.arrayList=arrayList;
        }

        public void setOnItemPopInListener(OnItemPopInListener listener){
            mListener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_consent_letter_master,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
            ModelConsentLetterMaster modelConsentLetterMaster=arrayList.get(i);
            viewHolder.textPatient.setText(modelConsentLetterMaster.getPatient());
            viewHolder.textPatient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   mListener.setOnPop(i);
                    viewHolder.textPatient.setTextColor(context.getResources().getColor(R.color.colorRed));
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView textPatient;
            public ViewHolder(@NonNull View view){
                super(view);
                textPatient=(TextView)view.findViewById(R.id.text_patient);
            }

        }
 }
