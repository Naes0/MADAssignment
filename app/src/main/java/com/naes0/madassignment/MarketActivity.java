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
    private Item buyItem;
    private Item sellItem;

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
        Fragment buyFrameFrag = fm.findFragmentById(R.id.buyframe);
        Fragment sellFrameFrag = fm.findFragmentById(R.id.sellframe);
        Fragment statusBar = fm.findFragmentById(R.id.statusbar);

        if (buyFrameFrag == null)
        {
            buyFrameFrag = new BuyListFragment();
            fm.beginTransaction().add(R.id.buyframe, buyFrameFrag).commit();
        }

        if (sellFrameFrag == null)
        {
            sellFrameFrag = new SellListFragment();
            fm.beginTransaction().add(R.id.sellframe, sellFrameFrag).commit();
        }

        if (statusBar == null)
        {
            statusBar = new StatusBarFrag();
            ((StatusBarFrag) statusBar).setPlayer(player);
            fm.beginTransaction().add(R.id.statusbar, statusBar).commit();
        }
    }

}
