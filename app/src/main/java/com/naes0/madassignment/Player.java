package com.naes0.madassignment;

import java.util.ArrayList;
import java.util.List;

public class Player
{
    private int row;
    private int col;
    private int cash;
    private double health;
    private double equipMass;
    private List<Equipment> equipmentlist;
    private static final double MAX_HEALTH = 100.0;

    public Player()
    {
        this.row = 0;
        this.col = 0;
        this.cash = 0;
        this.health = MAX_HEALTH;
        this.equipMass = 0.0;
        this.equipmentlist = new ArrayList<Equipment>();
    }

    public int getRow()
    {
        return row;
    }

    public void addRow(int row)
    {
        this.row += row;
    }

    public int getCol()
    {
        return col;
    }

    public void addCol(int col)
    {
        this.col += col;
    }

    public int getCash()
    {
        return cash;
    }

    public void addCash(int cash)
    {
        this.cash += cash;
    }

    public double getHealth()
    {
        return health;
    }

    public void addHealth(double health)
    {
        if (this.health + health >= 100.0)
        {
            this.health = 100.0;
        }
        else
        {
            this.health += health;
        }
    }

    public void setHealth(double health)
    {
        this.health = health;
    }

    public double getEquipMass()
    {
        return equipMass;
    }

    public void addEquipMass(double equipMass)
    {
        this.equipMass += equipMass;
    }

    public String getPos()
    {
        return ("[" + row + "]" + "[" + col + "]");
    }

    public List<Equipment> getEquipmentlist()
    {
        return equipmentlist;
    }

    public void addEquipment(Equipment equip) throws WinException
    {
        equipmentlist.add(equip);
        addEquipMass(equip.getMassOrHealth());
        boolean sword = false;
        boolean shield = false;
        boolean necklace = false;
        for(Equipment eq : equipmentlist)
        {
            if(!sword)
            {
                sword = eq.getDesc().equals("Jade Monkey");
            }
            if(!shield)
            {
                shield = eq.getDesc().equals("Roadmap");
            }
            if(!necklace)
            {
                necklace = eq.getDesc().equals("Ice Scraper");
            }
        }
        if(sword && shield && necklace)
        {
            throw new WinException("You got the secret items!\n               You Win!");
        }
    }

    public boolean addItem(Item buyItem) throws WinException
    {
        boolean boo = false;
        if (cash >= buyItem.getValue())
        {
            boo = true;
            addCash(-buyItem.getValue());
            if (buyItem instanceof Equipment)
            {
                addEquipment((Equipment) buyItem);
            }
            else
            {
                addHealth(buyItem.getMassOrHealth());
            }
        }
        return boo;
    }

    public void addItemNoCash(Item buyItem) throws WinException
    {
        if (buyItem instanceof Equipment)
        {
            addEquipment((Equipment) buyItem);
        }
        else
        {
            addHealth(buyItem.getMassOrHealth());
        }
    }

    public void removeEquipment(Equipment equip)
    {
        equipmentlist.remove(equip);
        addEquipMass(-equip.getMassOrHealth());
    }

    public void decreaseHealth() throws DeadException
    {
        setHealth(Math.max(0.0, health - 5.0 - (equipMass / 2.0)));
        if (health <= 0.0)
        {
            throw new DeadException("You lost all your health!\n            You Lose!");
        }
    }

    public boolean contains(Item item)
    {
        boolean boo = false;
        for (Equipment e : equipmentlist)
        {
            if(e.getDesc().equals(item.getDesc()))
            {
                boo = true;
            }
        }
        return boo;
    }
}
