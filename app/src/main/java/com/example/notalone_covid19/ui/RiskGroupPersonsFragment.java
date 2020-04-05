package com.example.notalone_covid19.ui;

import android.util.Log;
import com.example.notalone_covid19.RiskGroupPerson;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class RiskGroupPersonsFragment {
    ArrayList<RiskGroupPerson> riskGroupPersons = new ArrayList<>();
    private final String RISK_GROUP_PERSONS_DB_NAME = "RiskGroupPersonDB";

    public void readFromDatabase()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("message");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Count ", "" + dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    riskGroupPersons.add(postSnapshot.getValue(RiskGroupPerson.class));
                    Log.i("Person ", "" + postSnapshot.getValue(RiskGroupPerson.class).toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("read from dababase", "Failed to read value.", databaseError.toException());
            }
        };
        ref.addValueEventListener(postListener);
    }

    public ArrayList<RiskGroupPerson> getAllRiskGroupPersons() {
        readFromDatabase();
        return riskGroupPersons;
    }
}
