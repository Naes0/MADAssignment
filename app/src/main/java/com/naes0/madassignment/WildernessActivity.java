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

public class WildernessActivity extends AppCompatActivity
{
    private Button pickButton;
    private Button dropButton;
    private Button leaveButton;
    private Button useButton;

    private Fragment pickFrameFrag;
    private Fragment dropFrameFrag;
    private Fragment statusBar;

    private GameData data;
    private Player player;
    private Area currArea;
    private Item pickItem;
    private Equipment dropItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilderness);

        update();

        pickButton = (Button) findViewById(R.id.pickup);
        dropButton = (Button) findViewById(R.id.drop);
        leaveButton = (Button) findViewById(R.id.leave);
        useButton = (Button) findViewById(R.id.use);

        FragmentManager fm = getSupportFragmentManager();
        pickFrameFrag = fm.findFragmentById(R.id.pickframe);
        dropFrameFrag = fm.findFragmentById(R.id.dropframe);
        statusBar = fm.findFragmentById(R.id.statusbar);

        if (pickFrameFrag == null)
        {
            pickFrameFrag = new BuyListFragment();
            fm.beginTransaction().add(R.id.pickframe, pickFrameFrag).commit();
        }

        if (dropFrameFrag == null)
        {
            dropFrameFrag = new SellListFragment();
            fm.beginTransaction().add(R.id.dropframe, dropFrameFrag).commit();
        }

        if (statusBar == null)
        {
            statusBar = new StatusBarFrag();
            fm.beginTransaction().add(R.id.statusbar, statusBar).commit();
        }

        pickButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pickItem = ((BuyListFragment) pickFrameFrag).getSelectedItem();
                if (pickItem != null)
                {
                    try
                    {
                        player.addItemNoCash(pickItem);
                    }
                    catch(WinException e)
                    {
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        data.reset();
                        startActivity(new Intent(WildernessActivity.this, MainActivity.class));
                    }
                    currArea.removeItem(pickItem);
                    data.updateArea(currArea);
                    data.updatePlayer(player);
                    ((SellListFragment) dropFrameFrag).update();
                    ((BuyListFragment) pickFrameFrag).update();
                    ((StatusBarFrag) statusBar).update();
                    ((BuyListFragment) pickFrameFrag).clearSelection();
                }

            }
        });

        dropButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dropItem = ((SellListFragment) dropFrameFrag).getSelectedEquipment();
                if (dropItem != null)
                {
                    player.removeEquipment(dropItem);
                    data.updatePlayer(player);
                    currArea.addItem(dropItem);
                    data.updateArea(currArea);
                    ((SellListFragment) dropFrameFrag).update();
                    ((BuyListFragment) pickFrameFrag).update();
                    ((StatusBarFrag) statusBar).update();
                    ((SellListFragment) dropFrameFrag).clearSelection();
                }
            }
        });

        leaveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                data.updatePlayer(player);
                data.updateArea(currArea);
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
                dropItem = ((SellListFragment) dropFrameFrag).getSelectedEquipment();
                if(dropItem != null && dropItem.isUsable())
                {
                    dropItem.use(WildernessActivity.this);
                    update();
                    player.removeEquipment(dropItem);
                    data.updatePlayer(player);
                    update();
                    ((SellListFragment) dropFrameFrag).update();
                    ((BuyListFragment) pickFrameFrag).update();
                    ((StatusBarFrag) statusBar).update();
                    ((SellListFragment) dropFrameFrag).clearSelection();
                }
            }
        });

    }

    public void update()
    {
        data = GameData.get(getApplicationContext());
        player = data.getPlayer();
        currArea = data.getCurrArea();
        data.updatePlayer(player);
        data.updateArea(currArea);
    }
}
