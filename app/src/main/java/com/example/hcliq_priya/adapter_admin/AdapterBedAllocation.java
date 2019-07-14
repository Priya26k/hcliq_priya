package com.example.hcliq_priya.adapter_admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.model_admin.ModelBedAllocation;
import com.example.hcliq_priya.model_admin.ModelIpdBedMaster;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBedAllocation extends RecyclerView.Adapter<AdapterBedAllocation.ViewHolder>  {

    private Context context;
    ArrayList<ModelBedAllocation> bedAllocationArrayList;

    public AdapterBedAllocation(Context context,   ArrayList<ModelBedAllocation> bedAllocationArrayList){
        this.context=context;
        this.bedAllocationArrayList=bedAllocationArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bed_allocation,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        final ModelBedAllocation modelBedAllocation=bedAllocationArrayList.get(i);
        viewHolder.facilityName.setText(modelBedAllocation.getFacilityName().concat(" / ").concat(modelBedAllocation.getFacilityUnitName()).concat(" / ").concat(modelBedAllocation.getFacilityBedDesc()));viewHolder.bedAllocationDate.setText(modelBedAllocation.getIpdDetailStDateDesc().concat(" / ").concat(modelBedAllocation.getIpdDetailEndDateDesc()));
        viewHolder.bedAllocationDate.setText(modelBedAllocation.getIpdDetailStDateDesc());
        viewHolder.endDate.setText(modelBedAllocation.getIpdDetailEndDateDesc());

        if(modelBedAllocation.getIpdBedDeleteFlag().equals("T")){
            viewHolder.removeImageView.setVisibility(View.VISIBLE);
            viewHolder.removeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bedAllocationArrayList.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i,bedAllocationArrayList.size());
                }
            });
        }else {
            viewHolder.removeImageView.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return bedAllocationArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView facilityName, bedAllocationDate,endDate;
        CircleImageView removeImageView;
        public ViewHolder(@NonNull View view) {
            super(view);

            facilityName=(TextView)view.findViewById(R.id.text_view_facility_name);
            bedAllocationDate=(TextView)view.findViewById(R.id.text_view_start_date);
            endDate=(TextView)view.findViewById(R.id.text_view_end_date);
            removeImageView=(CircleImageView)view.findViewById(R.id.remove);
        }
    }
}
