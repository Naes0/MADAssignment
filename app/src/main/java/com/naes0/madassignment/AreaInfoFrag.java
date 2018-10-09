package com.naes0.madassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class AreaInfoFrag extends Fragment
{

    private TextView descView;
    private EditText descEdit;
    private Switch starred;
    private Area currArea;
    private Player player;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b)
    {
        View view = li.inflate(R.layout.area_info, parent, false);


        descView = (TextView) view.findViewById(R.id.descView);
        descEdit = (EditText) view.findViewById(R.id.descEdit);
        starred = (Switch) view.findViewById(R.id.starred);

        descView.setText(setWildOrTown());

        descEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //currArea set description to that;
            }
        });


        return view;
    }

    public void setCurrArea(Area currArea)
    {
        this.currArea = currArea;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public String setWildOrTown()
    {
        String wildOrTown = "";
        if (currArea.isTown())
        {
            wildOrTown = "Town";
        }
        else
        {
            wildOrTown = "Wilderness";
        }
        return  wildOrTown;
    }
}
