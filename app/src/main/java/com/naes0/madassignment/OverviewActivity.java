package com.naes0.madassignment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OverviewActivity extends AppCompatActivity
{
    private Fragment mapFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        FragmentManager fm = getSupportFragmentManager();
        mapFrag = (MapFragment) fm.findFragmentById(R.id.frame);
        if (mapFrag == null)
        {
            mapFrag = new MapFragment();

            fm.beginTransaction().add(R.id.frame, mapFrag).commit();
        }
        else
        {
            ((MapFragment) mapFrag).update();
        }
    }
}
