package com.naes0.madassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Map;

public class AreaInfoFrag extends Fragment
{

    private TextView descView;
    private EditText descEdit;
    private Switch starred;
    private Area currArea;
    private GameData data;
    private MapFragment mapFrag;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        data = GameData.get();
        currArea = data.getCurrArea();
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b)
    {
        View view = li.inflate(R.layout.area_info, parent, false);

        descView = (TextView) view.findViewById(R.id.descView);
        descEdit = (EditText) view.findViewById(R.id.descEdit);
        starred = (Switch) view.findViewById(R.id.starred);

        descView.setText(setWildOrTown());

        descEdit.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                currArea.setDescription(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        starred.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                starred.setChecked(!currArea.getStarred());
                currArea.setStarred(!currArea.getStarred());
                updateForMap();
            }
        });

        return view;
    }

    public void update()
    {
        currArea = data.getCurrArea();
        descView.setText(setWildOrTown());
        descEdit.setText(currArea.getDescription());
        starred.setChecked(currArea.getStarred());
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

    public void updateForArea(Area area)
    {
        currArea = area;
        descView.setText(setWildOrTown());
        descEdit.setText(area.getDescription());
        starred.setChecked(area.getStarred());
    }

    public void setMapFrag(MapFragment mapFrag)
    {
        this.mapFrag = mapFrag;
    }

    public void updateForMap()
    {
        if(mapFrag != null)
        {
            mapFrag.update();
        }

    }
}
