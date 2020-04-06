package com.example.notalone_covid19.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.notalone_covid19.Adapter_RiskGroup;
import com.example.notalone_covid19.MyApp;
import com.example.notalone_covid19.R;
import com.example.notalone_covid19.RiskGroupPerson;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class LocationFragment extends Fragment {

    private Context cntx;
    private MapViewFragment mapFragment;
    private final String RISK_GROUP_PERSONS_DB_NAME = "RiskGroupPersonDB";
    private final List<RiskGroupPerson> people = new ArrayList<>();
    private ListView listView;

    public LocationFragment() {
        this.cntx = getActivity();
        mapFragment = new MapViewFragment(cntx);
        FirebaseDatabase.getInstance().getReference(RISK_GROUP_PERSONS_DB_NAME).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                people.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    people.add(snapshot.getValue(RiskGroupPerson.class));
                }
                mapFragment.setPeople(people);
                listView.setAdapter(new Adapter_RiskGroup(getLayoutInflater(), people, new ListViewCallback<RiskGroupPerson>() {
                    @Override
                    public void onSelected(RiskGroupPerson o) {
                        Dialog dialog = getConfirmationDialog(o, new DialogCallback() {
                            @Override
                            public void onConfirmed() {
                                //TODO: Handel user confirmed
                            }
                        });
                        dialog.show();
                    }
                }));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.cntx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        getChildFragmentManager().beginTransaction().replace(R.id.map_fragment,mapFragment).commit();
        listView = view.findViewById(R.id.risk_group_map_list);
        return view;
    }

    public interface ListViewCallback<T>{
        void onSelected(T o);
    }

    private interface DialogCallback{
        void onConfirmed();
    }

    private Dialog getConfirmationDialog(RiskGroupPerson person, final DialogCallback callback){
        final Dialog dialog = new Dialog(cntx);
        View view = getLayoutInflater().inflate(R.layout.confirmation_dialog,null);
        Button confirm = view.findViewById(R.id.dialog_confirm);
        Button cancel = view.findViewById(R.id.dialog_cancel);
        TextView message = view.findViewById(R.id.dialog_text);
        message.setText(String.format(Locale.ENGLISH, "Thank you!\nPlease confirm you want to help %s", person.getFullName()));
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onConfirmed();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        return dialog;
    }
}
