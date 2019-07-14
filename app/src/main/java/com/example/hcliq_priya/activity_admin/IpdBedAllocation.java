package com.example.hcliq_priya.activity_admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.hcliq_priya.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IpdBedAllocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipd_bed_allocation);
        final EditText editFromDate=(EditText)findViewById(R.id.edit_from_date);
        final EditText editToDate=(EditText)findViewById(R.id.edit_to_date);
        EditText editWardNo=(EditText)findViewById(R.id.edit_Ward_no);
        EditText editBedNo=(EditText)findViewById(R.id.edit_Bed_no);
        EditText editFacilityName=(EditText)findViewById(R.id.edit_facility_name);
        final SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String currentDate=sdf.format(new Date());
        final Calendar myCalendar=Calendar.getInstance();
        editFromDate.setText(" "+currentDate);
        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
            private void updateLabel(){
                editFromDate.setText(" "+sdf.format(myCalendar.getTime()));
            }
        };
        final Calendar myCalendar1=Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR,year);
                myCalendar1.set(Calendar.MONTH,month);
                myCalendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
            private void updateLabel(){
                editToDate.setText(" "+sdf.format(myCalendar1.getTime()));
            }
        };

        editFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(IpdBedAllocation.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        editToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(IpdBedAllocation.this,date1,myCalendar1.get(Calendar.YEAR),myCalendar1.get(Calendar.MONTH),myCalendar1.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


    }
}
