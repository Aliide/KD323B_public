package se.mah.k3.skaneAPI.xmlparser;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;



import java.util.ArrayList;

import se.mah.k3.skaneAPI.R;
import se.mah.k3.skaneAPI.model.Journey;
import se.mah.k3.skaneAPI.model.Station;

/**
 * Created by K3LARA on 13/04/2015.
 */
public class MyAdapter extends BaseExpandableListAdapter {
    private ArrayList<Journey> journeys;
    private Context c;

    public MyAdapter(Context c, ArrayList<Journey> journeys){
        this.c=c;
        this.journeys = journeys;
    }

    @Override
    public int getGroupCount() {
        return journeys.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Journey journey = journeys.get(groupPosition);

        if(convertView == null) {
            LayoutInflater li = (LayoutInflater) this.c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.group_layout, null);
        }


        TextView avgang = (TextView) convertView.findViewById(R.id.tv_dest);
        Station startStationName = journey.getStartStation();
        avgang.setText(startStationName.getStationName());
        TextView destination =(TextView) convertView.findViewById(R.id.tv_tid);
        Station endStationName = journey.getEndStation();
        destination.setText(endStationName.getStationName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Journey journey = journeys.get(groupPosition);

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) this.c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.child, null);
        }
        TextView lineTypeName = (TextView) convertView.findViewById(R.id.lineTypeName);
        String lineName = journey.getLineTypeName();
        Log.d("Line type name", lineName);
        lineTypeName.setText(lineName);
        TextView timeToDeparture = (TextView) convertView.findViewById(R.id.timeToDeparture);
        String departure = journey.getTimeToDeparture();
        //hämtar symbolen och visar om det är mindre än 5 min kvar till avgång
        ImageView skynda = (ImageView) convertView.findViewById(R.id.skynda);
        if (Integer.parseInt(journey.getTimeToDeparture()) < 5) {
                Log.i("tiden", "fungerar");
                skynda.setVisibility(View.VISIBLE);
            }else{
                skynda.setVisibility(View.INVISIBLE);

        }
        timeToDeparture.setText(departure);
        TextView travelMinutes = (TextView) convertView.findViewById(R.id.travelMinutes);
        String minutes = journey.getTravelMinutes();
        travelMinutes.setText(minutes);
        TextView noOfChanges = (TextView) convertView.findViewById(R.id.noOfChanges);
        String changes = journey.getNoOfChanges();
        noOfChanges.setText(changes);

        return convertView;
    }


    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}


