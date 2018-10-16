package com.naes0.madassignment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class OverviewActivity extends AppCompatActivity
{
    private Fragment mapFrag;
    private Fragment statusBarFrag;
    private Fragment areaInfoFrag;
    private Button leaveButton;
    private Area selectedArea;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        leaveButton = (Button) findViewById(R.id.leave);

        FragmentManager fm = getSupportFragmentManager();
        areaInfoFrag = (AreaInfoFrag) fm.findFragmentById(R.id.areainfo);
        statusBarFrag = (StatusBarFrag) fm.findFragmentById(R.id.statusbar);
        mapFrag = (MapFragment) fm.findFragmentById(R.id.frame);

        if(areaInfoFrag == null)
        {
            areaInfoFrag = new AreaInfoFrag();
            fm.beginTransaction().add(R.id.areainfo, areaInfoFrag).commit();
        }
        if (mapFrag == null)
        {
            mapFrag = new MapFragment();

            fm.beginTransaction().add(R.id.frame, mapFrag).commit();
        }
        if (statusBarFrag == null)
        {
            statusBarFrag = new StatusBarFrag();
            fm.beginTransaction().add(R.id.statusbar, statusBarFrag).commit();
        }
        ((MapFragment) mapFrag).setAreaInfoFrag((AreaInfoFrag) areaInfoFrag);
        ((AreaInfoFrag) areaInfoFrag).setMapFrag((MapFragment) mapFrag);

        leaveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
