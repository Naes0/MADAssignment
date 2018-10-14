package com.naes0.madassignment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class SOSActivity extends AppCompatActivity
{
    private Button leaveButton;
    private Fragment combinedListFrag;
    private GameData data;
    private Player player;
    private Area currArea;
    private List<Item> itemlist;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        data = GameData.get();
        player = data.getPlayer();
        currArea = data.getCurrArea();
        itemlist = new ArrayList<Item>();

        leaveButton = (Button) findViewById(R.id.leave);

        FragmentManager fm = getSupportFragmentManager();
        combinedListFrag = (SOSFrag) fm.findFragmentById(R.id.frame);
        if (combinedListFrag == null)
        {
            combinedListFrag = new SOSFrag();
            fm.beginTransaction().add(R.id.frame, combinedListFrag).commit();
        }

        leaveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

    }
}
