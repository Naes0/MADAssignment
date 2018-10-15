package com.naes0.madassignment;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private Fragment areaInfo;
    private Fragment statusBar;

    private Player player;
    private GameData data;
    private Area currArea;

    public static final int REQUEST_CODE = 0;

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
        areaInfo = fm.findFragmentById(R.id.areainfo);
        statusBar = fm.findFragmentById(R.id.statusbar);
        if (areaInfo == null)
        {
            areaInfo = new AreaInfoFrag();
            fm.beginTransaction().add(R.id.areainfo, areaInfo).commit();
        }
        if (statusBar == null)
        {
            statusBar = new StatusBarFrag();
            fm.beginTransaction().add(R.id.statusbar, statusBar).commit();
        }

        northButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (player.getRow() < GameData.HEIGHT - 1)
                {
                    player.addRow(1);
                    playerMoves();
                }
            }
        });

        southButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (player.getRow() > 0)
                {
                    player.addRow(-1);
                    playerMoves();
                }
            }
        });

        eastButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (player.getCol() < GameData.WIDTH - 1)
                {
                    player.addCol(1);
                    playerMoves();
                }
            }
        });

        westButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (player.getCol() > 0)
                {
                    player.addCol(-1);
                    playerMoves();
                }
            }
        });

        optionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent;
                if (currArea.isTown())
                {
                    intent = new Intent(NavigationActivity.this, MarketActivity.class);
                } else
                {
                    intent = new Intent(NavigationActivity.this, WildernessActivity.class);
                }
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        overviewButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(NavigationActivity.this, OverviewActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            ((StatusBarFrag) statusBar).update();
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

    public void playerMoves()
    {
        /*try
        {
            player.decreaseHealth();
        }
        catch (DecreaseHealthException e)
        {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "You Lose", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            startActivity(new Intent(NavigationActivity.this, MainActivity.class));
        }*/
        locationView.setText(player.getPos());
        currArea = data.getCurrArea();
        currArea.setExplored(true);
        ((AreaInfoFrag) areaInfo).update();
        ((StatusBarFrag) statusBar).update();
    }

    public void playerSetup()
    {
        player.addEquipment(new Equipment("Sword", 10, 5));
        player.addEquipment(new Equipment("Shield", 5, 3));
        player.addEquipment(new PortableSmellOScope("Portable Smell-O-Scope", 25, 5 ));
        player.addCash(50);
        currArea = data.getArea(player.getRow(), player.getCol());
        locationView.setText(player.getPos());
    }
}
