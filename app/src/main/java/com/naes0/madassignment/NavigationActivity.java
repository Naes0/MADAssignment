package com.naes0.madassignment;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class NavigationActivity extends AppCompatActivity
{
    private ConstraintLayout layout;
    private TextView locationView;
    private TextView descView;
    private EditText descEdit;
    private Switch starred;

    private Button northButton;
    private Button southButton;
    private Button westButton;
    private Button eastButton;
    private Button optionButton;
    private Button overviewButton;

    private TextView cashView;
    private TextView healthView;
    private TextView massView;
    private Button restartButton;

    private Player player;
    private GameData data;
    private Area currArea;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        data = GameData.get();
        player = data.getPlayer();

        initialiseComponents();
    }

    public void initialiseComponents()
    {
        northButton = (Button) findViewById(R.id.north);
        southButton = (Button) findViewById(R.id.south);
        westButton = (Button) findViewById(R.id.west);
        eastButton = (Button) findViewById(R.id.east);
        restartButton = (Button) findViewById(R.id.reset);
        optionButton = (Button) findViewById(R.id.option);
        overviewButton = (Button) findViewById(R.id.overview);
        locationView = (TextView) findViewById(R.id.location);
        descView = (TextView) findViewById(R.id.descView);
        descEdit = (EditText) findViewById(R.id.descEdit);
        starred = (Switch) findViewById(R.id.starred);
        healthView = (TextView) findViewById(R.id.health);
        cashView = (TextView) findViewById(R.id.cash);
        massView = (TextView) findViewById(R.id.mass);
        layout = (ConstraintLayout) findViewById(R.id.navlayout);
    }
}
