package com.naes0.madassignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import android.support.v7.app.AppCompatActivity;

public class MarketActivity extends AppCompatActivity
{
    private TextView itemTypeBuyView;
    private TextView descBuyView;
    private TextView valueBuyView;
    private TextView masshealthBuyView;
    private Button prevBuyButton;
    private Button nextBuyButton;
    private Button buyButton;
    private TextView descSellView;
    private TextView valueSellView;
    private TextView massHealthSellView;
    private Button prevSellButton;
    private Button nextSellButton;
    private Button sellButton;
    private Button leaveButton;
    private TextView healthView;
    private TextView cashView;
    private TextView massView;
    private Player player;
    private Area area;
    private List<Item> itemList;
    private List<Equipment> equipmentList;
    private Item buyItem;
    private Equipment sellItem;
    private int currBuyIndex;
    private int currSellIndex;
    private boolean marketIsEmpty;
    private boolean playerIsEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        buttonInitialise();
        setup();
        initialise();

        nextBuyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!marketIsEmpty)
                {
                    if (currBuyIndex == itemList.size() - 1)
                    {
                        currBuyIndex = -1;
                    }
                    currBuyIndex++;
                    buyItem = itemList.get(currBuyIndex);
                    updateBuyItem();
                }
            }
        });

        prevBuyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!marketIsEmpty)
                {
                    if(currBuyIndex == 0)
                    {
                        currBuyIndex = itemList.size();
                    }
                    currBuyIndex--;
                    buyItem = itemList.get(currBuyIndex);
                    updateBuyItem();
                }
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if((!marketIsEmpty) && (player.getCash() >= buyItem.getValue()))
                {
                    if(buyItem instanceof Equipment)
                    {
                        player.addEquipment((Equipment) buyItem);
                    }
                    else
                    {
                        player.addHealth(buyItem.getMassOrHealth());
                    }
                    player.addCash(-buyItem.getValue());
                    itemList.remove(buyItem);
                    sellItem = equipmentList.get(0);
                    playerIsEmpty = false;
                    currBuyIndex = 0;
                    if(itemList.isEmpty())
                    {
                        marketIsEmpty = true;
                    }
                    else
                    {
                        buyItem = itemList.get(currBuyIndex);
                    }
                }
                updateBuyItem();
                updateSellItem();
                checkWin();
                updateStatusBar();
            }
        });

        nextSellButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!playerIsEmpty)
                {
                    if (currSellIndex == equipmentList.size() - 1)
                    {
                        currSellIndex = -1;
                    }
                    currSellIndex++;
                    sellItem = equipmentList.get(currSellIndex);
                    updateSellItem();
                }
            }
        });

        prevSellButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!playerIsEmpty)
                {
                    if(currSellIndex == 0)
                    {
                        currSellIndex = equipmentList.size();
                    }
                    currSellIndex--;
                    sellItem = equipmentList.get(currSellIndex);
                    updateSellItem();
                }
            }
        });

        sellButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!playerIsEmpty)
                {
                    player.addCash((int) ((double)sellItem.getValue() * 0.75));
                    sellItem = equipmentList.get(currSellIndex);
                    player.removeEquipment(sellItem);
                    area.addItem(sellItem);
                    buyItem = itemList.get(0);
                    marketIsEmpty = false;
                    currSellIndex = 0;
                    if(equipmentList.isEmpty())
                    {
                        playerIsEmpty = true;
                    }
                    else
                    {
                        sellItem = equipmentList.get(currSellIndex);
                    }
                }
                updateSellItem();
                updateBuyItem();
                checkWin();
                updateStatusBar();
            }
        });

        leaveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.putExtra("player", player);
                intent.putExtra("area", area);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        if (savedInstanceState != null)
        {
            player = savedInstanceState.getParcelable("Player");
            area = savedInstanceState.getParcelable("Area");
            updateSellItem();
            updateBuyItem();
            updateStatusBar();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putParcelable("Player", player);
        outState.putParcelable("Area", area);
    }

    public void updateBuyItem()
    {
        if (!marketIsEmpty)
        {
            itemTypeBuyView.setText(itemType(buyItem));
            descBuyView.setText(buyItem.getDesc());
            valueBuyView.setText("Value: " + buyItem.getValue());
            masshealthBuyView.setText(healthOrMass(buyItem) + Double.toString(buyItem.getMassOrHealth()));
        }
        else
        {
            itemTypeBuyView.setText("N/A");
            descBuyView.setText("EMPTY");
            valueBuyView.setText("N/A");
            masshealthBuyView.setText("N/A");
        }
    }

    public void updateSellItem()
    {
        if (!playerIsEmpty)
        {
            descSellView.setText(sellItem.getDesc());
            valueSellView.setText("Value: " + sellItem.getValue());
            massHealthSellView.setText(healthOrMass(sellItem) + Double.toString(sellItem.getMassOrHealth()));
        }
        else
        {
            descSellView.setText("EMPTY");
            valueSellView.setText("N/A");
            massHealthSellView.setText("N/A");
        }
    }

    public void buttonInitialise()
    {
        itemTypeBuyView = (TextView) findViewById(R.id.itemTitle);
        descBuyView = (TextView) findViewById(R.id.descBuyView);
        valueBuyView = (TextView) findViewById(R.id.valueBuyView);
        masshealthBuyView = (TextView) findViewById(R.id.massOrHealthBuyView);
        prevBuyButton = (Button) findViewById(R.id.prevBuyButton);
        nextBuyButton = (Button) findViewById(R.id.nextBuyButton);
        buyButton = (Button) findViewById(R.id.buyButton);
        descSellView = (TextView) findViewById(R.id.descSellView);
        valueSellView = (TextView) findViewById(R.id.valueSellView);
        massHealthSellView = (TextView) findViewById(R.id.massOrHealthSellView);
        prevSellButton = (Button) findViewById(R.id.prevSellButton);
        nextSellButton = (Button) findViewById(R.id.nextSellButton);
        sellButton = (Button) findViewById(R.id.sellButton);
        leaveButton = (Button) findViewById(R.id.leaveButton);
        healthView = (TextView) findViewById(R.id.healthView);
        cashView = (TextView) findViewById(R.id.cashView);
        massView = (TextView) findViewById(R.id.massView);
    }

    public void setup()
    {
        Intent intent = getIntent();
        player = intent.getParcelableExtra("player");
        area = intent.getParcelableExtra("currArea");
        itemList = area.getItemList();
        equipmentList = player.getEquipmentlist();
        marketIsEmpty = itemList.isEmpty();
        playerIsEmpty = equipmentList.isEmpty();
        if(!marketIsEmpty)
        {
            buyItem = itemList.get(0);
        }
        if (!playerIsEmpty)
        {
            sellItem = equipmentList.get(0);
        }
        currBuyIndex = 0;
        currSellIndex = 0;
    }

    public void initialise()
    {
        updateStatusBar();
        if (!marketIsEmpty)
        {
            itemTypeBuyView.setText(itemType(buyItem));
            descBuyView.setText(buyItem.getDesc());
            valueBuyView.setText("Value: " + buyItem.getValue());
            masshealthBuyView.setText(healthOrMass(buyItem) + Double.toString(buyItem.getMassOrHealth()));
        }
        else
        {
            updateBuyItem();
        }
        if(!playerIsEmpty)
        {
            descSellView.setText(sellItem.getDesc());
            valueSellView.setText("Value: " + sellItem.getValue());
            massHealthSellView.setText("Mass: " + sellItem.getMassOrHealth());
        }
        else
        {
            updateSellItem();
        }
    }

    public void updateStatusBar()
    {
        healthView.setText("Health: " + Double.toString(player.getHealth()) + "/100.0");
        cashView.setText("Cash: $" + player.getCash());
        massView.setText("Mass: " + Double.toString(player.getEquipMass()) + " kg");
    }

    public String itemType(Item item)
    {
        if (item instanceof Equipment)
        {
            return "Equipment";
        }
        else
        {
            return "Food";
        }
    }

    public String healthOrMass(Item item)
    {
        if (item instanceof Equipment)
        {
            return "Mass: ";
        }
        else
        {
            return "Health: ";
        }
    }

    public void checkWin()
    {
        boolean sword = false;
        boolean shield = false;
        boolean necklace = false;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "You Win!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        for(Equipment eq : equipmentList)
        {
            if(!sword)
            {
                sword = eq.getDesc().equals("Sword");
            }
            if(!shield)
            {
                shield = eq.getDesc().equals("Shield");
            }
            if(!necklace)
            {
                necklace = eq.getDesc().equals("Necklace");
            }
        }
        if(sword && shield && necklace)
        {
            toast.show();
        }
    }
}
