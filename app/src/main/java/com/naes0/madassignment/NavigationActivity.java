package com.naes0.madassignment;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

    private Button northButton;
    private Button southButton;
    private Button westButton;
    private Button eastButton;
    private Button optionButton;
    private Button overviewButton;

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
        playerSetup();

        FragmentManager fm = getSupportFragmentManager();
        Fragment areaInfo = fm.findFragmentById(R.id.areainfo);
        Fragment statusBar = fm.findFragmentById(R.id.statusbar);
        if (areaInfo == null)
        {
            areaInfo = new AreaInfoFrag();
            ((AreaInfoFrag) areaInfo).setCurrArea(currArea);
            ((AreaInfoFrag) areaInfo).setPlayer(player);
            fm.beginTransaction().add(R.id.areainfo, areaInfo).commit();
        }
        if (statusBar == null)
        {
            statusBar = new StatusBarFrag();
            ((StatusBarFrag) statusBar).setPlayer(player);
            fm.beginTransaction().add(R.id.statusbar, statusBar).commit();
        }

    }

    public void initialiseComponents()
    {
        locationView = (TextView) findViewById(R.id.location);
        northButton = (Button) findViewById(R.id.north);
        southButton = (Button) findViewById(R.id.south);
        westButton = (Button) findViewById(R.id.west);
        eastButton = (Button) findViewById(R.id.east);
        optionButton = (Button) findViewById(R.id.option);
        overviewButton = (Button) findViewById(R.id.overview);
        layout = (ConstraintLayout) findViewById(R.id.navlayout);
    }

    public Area getCurrArea()
    {
        return currArea;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void playerSetup()
    {
        player.addEquipment(new Equipment("Sword", 10, 5));
        player.addEquipment(new Equipment("Shield", 5, 3));
        player.addCash(50);
        currArea = data.getArea(player.getRow(), player.getCol());
        locationView.setText(player.getPos());
    }
}
