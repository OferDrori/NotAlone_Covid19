package com.example.notalone_covid19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_RiskGroup extends BaseAdapter {

    private Context context;
    private ArrayList<RiskGroupPerson> riskGroupPersonArrayList;
    private TextView location, name,km;

    public  Adapter_RiskGroup(Context context, ArrayList<RiskGroupPerson> arrayList) {
        this.context = context;
        this.riskGroupPersonArrayList = arrayList;
    }
    @Override
    public int getCount() {
        return riskGroupPersonArrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.list_note, parent, false);
        name = convertView.findViewById(R.id.fullName_LBL_title);
        location = convertView.findViewById(R.id.location_LBL_title);
        km= convertView.findViewById(R.id.location_LBL_title);
        name.setText(" " + riskGroupPersonArrayList.get(position).getFullName());
        location.setText(" " +riskGroupPersonArrayList.get(position).getAddress());
        location.setText(" 0.9 km");
        return convertView;
    }
}
