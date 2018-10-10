package com.naes0.madassignment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MarketActivity extends AppCompatActivity
{
    private GameData data;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        GameData data = GameData.get();
        player = data.getPlayer();

        Intent intent = getIntent();
        Area currArea = intent.getParcelableExtra("currArea");
        List<Item> itemList = currArea.getitemList();
        FragmentManager fm = getSupportFragmentManager();
        Fragment buyFrameFrag = fm.findFragmentById(R.id.buyFrame);
        Fragment statusBar = fm.findFragmentById(R.id.statusbar);

        if (buyFrameFrag == null)
        {
            buyFrameFrag = new ListFragment();
            ((ListFragment) buyFrameFrag).setItemList(itemList);
            fm.beginTransaction().add(R.id.buyFrame, buyFrameFrag).commit();
        }
        if (statusBar == null)
        {
            statusBar = new StatusBarFrag();
            ((StatusBarFrag) statusBar).setPlayer(player);
            fm.beginTransaction().add(R.id.statusbar, statusBar).commit();
        }
    }
}
