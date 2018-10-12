package com.naes0.madassignment;

import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.List;

public class MarketActivity extends AppCompatActivity
{
    private Button buyButton;
    private Button sellButton;
    private Fragment buyFrameFrag;
    private Fragment sellFrameFrag;
    private Fragment statusBar;

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

        buyButton = (Button) findViewById(R.id.buy);
        sellButton = (Button) findViewById(R.id.sell);

        FragmentManager fm = getSupportFragmentManager();
        buyFrameFrag = fm.findFragmentById(R.id.buyframe);
        sellFrameFrag = fm.findFragmentById(R.id.sellframe);
        statusBar = fm.findFragmentById(R.id.statusbar);

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
            fm.beginTransaction().add(R.id.statusbar, statusBar).commit();
        }

        buyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               buyItem = ((BuyListFragment) buyFrameFrag).getSelectedItem();
               if (buyItem instanceof Equipment)
               {
                   player.addEquipment((Equipment) buyItem);
               }
               else
               {
                   player.addHealth(buyItem.getMassOrHealth());
               }
               ((SellListFragment) sellFrameFrag).update();
               ((BuyListFragment) buyFrameFrag).update();
            }
        });

    }

}
