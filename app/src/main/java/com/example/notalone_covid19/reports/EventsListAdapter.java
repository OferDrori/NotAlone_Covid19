package com.example.notalone_covid19.reports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.notalone_covid19.R;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EventsListAdapter extends ArrayAdapter<Event> {

    private Context cntx;

    public EventsListAdapter(@NonNull Context context, int resource, @NonNull List<Event> objects) {
        super(context, resource, objects);
        cntx = context;
    }

    String getDateString(long date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date));
        String month = calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.ENGLISH);
        return String.format(Locale.ENGLISH,"%2d %s",
                calendar.get(Calendar.DAY_OF_MONTH),month);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(cntx).inflate(R.layout.list_event,parent,false);
        TextView date = listItem.findViewById(R.id.list_event_date);
        TextView action = listItem.findViewById(R.id.list_event_action);
        date.setText(getDateString(event.getTimeInMillis()));
        action.setText(event.getData().toString());
        return listItem;
    }
}
