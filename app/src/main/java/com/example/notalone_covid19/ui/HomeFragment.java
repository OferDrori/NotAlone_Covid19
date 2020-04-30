package com.example.notalone_covid19.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notalone_covid19.Adapter_NoteModel;
import com.example.notalone_covid19.Adapter_RiskGroup;
import com.example.notalone_covid19.MyApp;
import com.example.notalone_covid19.R;
import com.example.notalone_covid19.RiskGroupPerson;
import com.example.notalone_covid19.User;
import com.example.notalone_covid19.reports.ActivityCalender;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment
{
    private Context cntx;
    private ListView list_LST_notes;
    private Adapter_NoteModel adapter_noteModel;
    private Adapter_RiskGroup adapter_riskGroup;
    private ImageView img_next_note;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        this.cntx = context;
        FirebaseDatabase.getInstance().getReference().child(MyApp.getMyUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<RiskGroupPerson> notesForGroup = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.exists()) {
                        RiskGroupPerson person = snapshot.getValue(RiskGroupPerson.class);
                        if(person != null && person.getFullName() != null && !person.getFullName().isEmpty())
                            notesForGroup.add(person);
                    }
                }
                Log.e("HomeFragment","Got: " + notesForGroup.size());
                list_LST_notes.setAdapter(new Adapter_RiskGroup(getLayoutInflater(), notesForGroup, new LocationFragment.ListViewCallback<RiskGroupPerson>() {
                    @Override
                    public void onSelected(RiskGroupPerson o) {
                        goToCalendarActivity(o);
                    }

                    @Override
                    public void onClicked(RiskGroupPerson o) {

                    }
                }));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        list_LST_notes = root.findViewById(R.id.list_LST_notes);
        return root;
    }

        private void goToCalendarActivity(RiskGroupPerson user){
        Intent intent = new Intent(getActivity() , ActivityCalender.class);
        intent.putExtra("uid",user.getId());
        intent.putExtra("my_uid", MyApp.getMyUid());
        startActivity(intent);
    }
}
