package com.example.notalone_covid19.reports;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.notalone_covid19.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddActionActivity extends AppCompatActivity {

    private Spinner actionSpinner;
    private View datePickButton;
    private Button saveButton;
    private boolean datePicked = false;
    private long date;
    private String myUid,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_action);
        if(getIntent().getExtras() == null)
            return;
        date = System.currentTimeMillis();
        uid = getIntent().getExtras().getString("uid");
        myUid = getIntent().getExtras().getString("my_uid");
        datePickButton = findViewById(R.id.date_picker_button);
        actionSpinner = findViewById(R.id.activityAdd_actionSpinner);
        saveButton = findViewById(R.id.add_action_save);
        actionSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ActivityCalender.HelpType.values()));
        setDateText(System.currentTimeMillis());
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEvent();
            }
        });
        datePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });
    }

    void updateEvent(){
        FirebaseDatabase.getInstance().getReference().child("events").child(myUid).child(uid)
                .child(String.valueOf(date)).setValue(actionSpinner.getSelectedItem())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
            }
        });
    }

    void setDateText(long date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date));
        TextView dateView = findViewById(R.id.activityAdd_textView_date);
        String month = calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.ENGLISH);
        dateView.setText(String.format(Locale.ENGLISH,"%2d %s",
                calendar.get(Calendar.DAY_OF_MONTH),month));
    }

    void pickDate(){
        final Dialog dialog = new Dialog(this);
        View view = getLayoutInflater().inflate(R.layout.date_picker_dialog,null);
        final DatePicker datePicker = view.findViewById(R.id.date_picker_dialog_calender);
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,monthOfYear,dayOfMonth);
                date = calendar.getTimeInMillis();
                setDateText(date);
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
}
