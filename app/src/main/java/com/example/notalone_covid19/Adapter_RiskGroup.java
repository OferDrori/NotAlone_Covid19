package com.example.notalone_covid19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notalone_covid19.ui.LocationFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Adapter_RiskGroup extends BaseAdapter {

    private LayoutInflater inflater;
    private List<RiskGroupPerson> riskGroupPersonArrayList;
    private TextView location, name,km;
    LocationFragment.ListViewCallback<RiskGroupPerson> callback = null;

    public  Adapter_RiskGroup(LayoutInflater inflater, List<RiskGroupPerson> arrayList) {
        this.inflater = inflater;
        this.riskGroupPersonArrayList = arrayList;
    }
    public  Adapter_RiskGroup(LayoutInflater inflater, List<RiskGroupPerson> arrayList,
                              LocationFragment.ListViewCallback<RiskGroupPerson> callback) {
        this.inflater = inflater;
        this.riskGroupPersonArrayList = arrayList;
        this.callback = callback;
    }
    @Override
    public int getCount() {
        return riskGroupPersonArrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return riskGroupPersonArrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = inflater.inflate(R.layout.list_note,parent,false);
        ImageView button = listItem.findViewById(R.id.img_next_note);
        name = listItem.findViewById(R.id.fullName_LBL_title);
        location = listItem.findViewById(R.id.location_LBL_title);
        km= listItem.findViewById(R.id.km_LBL_title);
        name.setText(String.format(Locale.ENGLISH," %s", riskGroupPersonArrayList.get(position).getFullName()));
        location.setText(String.format(Locale.ENGLISH, " %s", riskGroupPersonArrayList.get(position).getAddress()));
        km.setText(" 0.9 km");
        if(callback != null)
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onSelected(riskGroupPersonArrayList.get(position));
                }
            });
        return listItem;
    }
}
