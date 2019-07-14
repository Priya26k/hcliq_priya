package com.example.hcliq_priya.adapter_admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.model_admin.ModelIpdBedMaster;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterIpdBedMaster extends RecyclerView.Adapter<AdapterIpdBedMaster.ViewHolder> {
    private Context context;
    ArrayList<ModelIpdBedMaster> arrayList;
    public AdapterIpdBedMaster(Context context, ArrayList<ModelIpdBedMaster> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bed_allocation,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        ModelIpdBedMaster modelBedFacility=arrayList.get(i);
        viewHolder.bed1.setText(modelBedFacility.getBed1());
        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(i);
                notifyItemRangeChanged(i,arrayList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
           CircleImageView remove;
           TextView bed1;
           public ViewHolder(@NonNull View view){
               super(view);
               remove=(CircleImageView)view.findViewById(R.id.remove);
               bed1=(TextView)view.findViewById(R.id.text_bed_name);
           }

    }
}
