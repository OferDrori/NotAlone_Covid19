package com.example.notalone_covid19.reports;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notalone_covid19.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;

import static com.github.sundeepk.compactcalendarview.CompactCalendarView.FILL_LARGE_INDICATOR;
import static com.github.sundeepk.compactcalendarview.CompactCalendarView.SMALL_INDICATOR;

public class ActivityCalender extends AppCompatActivity {

    public enum HelpType{
        PHONE_CALL("Phone Call"),BUYING_FOOD("Buying Food"),
        PURCHASE("Purchase of medicines"),WARM_MEAL("Warm meal"),OTHER("Other");
        private String name;
        HelpType(String name){
            this.name = name;
        }
        @Override
        public String toString(){
            return name;
        }
    }
    private CompactCalendarView calendarView;
    private List<Event> events;
    private DatabaseReference reference;
    private ImageView fabAdd;
    private ListView eventsListView;

    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        calendarView = findViewById(R.id.compactcalendar_view);
        fabAdd = findViewById(R.id.fab_add);
        calendarView.setEventIndicatorStyle(FILL_LARGE_INDICATOR);
        eventsListView = findViewById(R.id.events_list);
        if(getIntent().getExtras() == null)
            return;
        final String myUid = getIntent().getExtras().getString("my_uid");
        final String uid = getIntent().getExtras().getString("uid");
        if(myUid == null || myUid.isEmpty() || uid == null || uid.isEmpty())
            return;
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityCalender.this,AddActionActivity.class);
                intent.putExtra("uid",uid);
                intent.putExtra("my_uid",myUid);
                startActivity(intent);
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("events").child(myUid).child(uid);
        final Context cntx = this;
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                events = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event e = new Event(0xFF1745BD,Long.parseLong(snapshot.getKey()),snapshot.getValue());
                    events.add(e);
                }
                eventsListView.setAdapter(new EventsListAdapter(cntx,R.layout.list_event,events));
                calendarView.removeAllEvents();
                calendarView.addEvents(events);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(reference != null && eventListener != null)
            reference.addValueEventListener(eventListener);
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(reference != null && eventListener != null)
            reference.removeEventListener(eventListener);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
