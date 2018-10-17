package com.naes0.madassignment;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MarketActivity extends AppCompatActivity
{
    private Button buyButton;
    private Button sellButton;
    private Button leaveButton;
    private Button useButton;
    private Fragment buyFrameFrag;
    private Fragment sellFrameFrag;
    private Fragment statusBar;

    private GameData data;
    private Player player;
    private Area currArea;
    private Item buyItem;
    private Equipment sellItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        data = GameData.get(getApplicationContext());
        player = data.getPlayer();
        currArea = data.getArea(player.getRow(), player.getCol());

        buyButton = (Button) findViewById(R.id.buy);
        sellButton = (Button) findViewById(R.id.sell);
        leaveButton = (Button) findViewById(R.id.leave);
        useButton = (Button) findViewById(R.id.use);

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
               if (buyItem != null)
               {
                   try
                   {
                       if (player.addItem(buyItem)) //if player accepts item remove it from the area.
                       {
                           currArea.removeItem(buyItem);
                       }
                   }
                   catch(WinException e)
                   {
                       Context context = getApplicationContext();
                       Toast toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
                       toast.setGravity(Gravity.CENTER, 0, 0);
                       toast.show();
                       data.reset();
                       startActivity(new Intent(MarketActivity.this, MainActivity.class));
                   }
                   ((SellListFragment) sellFrameFrag).update();
                   ((BuyListFragment) buyFrameFrag).update();
                   ((StatusBarFrag) statusBar).update();
                   ((BuyListFragment) buyFrameFrag).clearSelection();
               }

            }
        });

        sellButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sellItem = ((SellListFragment) sellFrameFrag).getSelectedEquipment();
                if(sellItem != null)
                {
                    player.removeEquipment(sellItem);
                    player.addCash((int) ((double)sellItem.getValue()*0.75));
                    currArea.addItem(sellItem);
                    ((SellListFragment) sellFrameFrag).update();
                    ((BuyListFragment) buyFrameFrag).update();
                    ((StatusBarFrag) statusBar).update();
                    ((SellListFragment) sellFrameFrag).clearSelection();
                }
            }
        });

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

        useButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sellItem = ((SellListFragment) sellFrameFrag).getSelectedEquipment();
                if(sellItem != null && sellItem.isUsable())
                {
                    sellItem.use(MarketActivity.this);
                    player.removeEquipment(sellItem);
                    update();
                    ((SellListFragment) sellFrameFrag).update();
                    ((BuyListFragment) buyFrameFrag).update();
                    ((SellListFragment) sellFrameFrag).clearSelection();
                }
            }
        });
    }

    public void update()
    {
        data = GameData.get(getApplicationContext());
        player = data.getPlayer();
        currArea = data.getCurrArea();
    }

}
