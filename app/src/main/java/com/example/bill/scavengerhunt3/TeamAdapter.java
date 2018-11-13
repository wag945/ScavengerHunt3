package com.example.bill.scavengerhunt3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class TeamAdapter extends ArrayAdapter{

    public TeamAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // It recover the view to the inflate

        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.content_list_item, parent, false);
        }

        // Get the object located at this position in the list
        Team team = (Team) getItem(position);

        // Find the TextView in the xml layout with the ID
        TextView textViewTeamname = (TextView) listItemView.findViewById(R.id.text_view_teamname);

        // Get the team name from the current Team object and
        // set this text on the team name TextView
        textViewTeamname.setText(team.getName());

        TextView textViewNumPlayers = (TextView) listItemView.findViewById(R.id.text_view_numplayers);

        // Get the number of players from the current Team object and
        // set this text on the numplayers TextView
        textViewNumPlayers.setText("Num Players: " + team.getNumPlayers());

        // Find the TextView in the xml layout with the ID version_number
        TextView textViewRecord = (TextView) listItemView.findViewById(R.id.text_view_record);

        // Get the record from the current Team object and
        // set this text on the record TextView
        textViewRecord.setText(team.getRecord());


        // TODO You can also customize the ImageView.

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
