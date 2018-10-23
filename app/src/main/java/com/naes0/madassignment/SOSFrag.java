package com.naes0.madassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SOSFrag extends Fragment
{
    private GameData data;
    private Player player;
    private List<String> stringList;
    private ItemAdapter adapter;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        data = GameData.get(getContext());
        player = data.getPlayer();
        stringList = setStringList(); //also sets areas list
        adapter = new ItemAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.list, ui,false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemAdapter();
        rv.setAdapter(adapter);
        return view;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name;
        private TextView eastwest;
        private TextView northsouth;
        private Item item;

        public ItemViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_entry, parent, false));

            name = (TextView) itemView.findViewById(R.id.itemname);
            eastwest = (TextView) itemView.findViewById(R.id.value);
            northsouth = (TextView) itemView.findViewById(R.id.masshealth);
        }

        public void bind(String itemName, String eastWest, String northSouth)
        {
            name.setText(itemName);
            eastwest.setText(eastWest);
            northsouth.setText(northSouth);
        }
    }

    //adapter
    public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>
    {
        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new ItemViewHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position)
        {
            //gets the itemName and location string from the list
            String itemNameLocation = stringList.get(position);
            //split on "|||" to get the [name, east/west, north/south]
            String[] parts = itemNameLocation.split("\\|\\|\\|");
            Log.d("STRING", parts.toString());
            //pass parts into bind
            holder.bind(parts[0], parts[1], parts[2]);
        }

        @Override
        public int getItemCount()
        {
            return stringList.size();
        }
    }

    public List<String> setStringList()
    {
        List<String> stringList = new ArrayList<String>();
        int pRow = player.getRow();
        int pCol = player.getCol();

        //calculates the upper and lower bounds of the 2x2 grid to display items from.
        int upperRow = Math.max(pRow-2, 0);
        int upperCol = Math.max(0, pCol-2);
        int lowerRow = Math.min(pRow+2, GameData.HEIGHT-1);
        int lowerCol = Math.min(pCol+2, GameData.WIDTH-1);

        // iterates through the 2x2 grid getting the itemName and its east/west position and
        // north/south position, stores all three variables in a string sepereated by "|||"
        // added that string to the list for the recycler view.
        for(int i = upperRow; i <= lowerRow; i++)
        {
            for(int j = upperCol; j <= lowerCol; j++ )
            {
                String northSouth = getNorthSouth(i);
                String eastWest = getEastWest(j);
                List<Item> itemList = data.getArea(i,j).getitemList();
                for(Item item : itemList)
                {
                    String itemNameLocation = item.getDesc() + "|||" + eastWest + "|||" + northSouth;
                    stringList.add(itemNameLocation);
                }
            }
        }
        return stringList;
    }

    // calculates the amount of cells in the east/west direction based on current location of player
    // and the current location of item.
    public String getEastWest(int j)
    {
        String eastWest = "";
        int currJ = player.getCol();
        if (j > currJ)
        {
            eastWest = (j-currJ) + " East";
        }
        else if(j < currJ)
        {
            eastWest = (currJ-j) + " West";
        }
        else //if they're equal
        {
            eastWest = "Current Col";
        }

        return eastWest;
    }

    // calculates the amount of cells in the north/south direction based on current location of player
    // and the current location of item.
    public String getNorthSouth(int i)
    {
        String northSouth = "";
        int currI = player.getRow();
        if (i < currI)
        {
            northSouth = (currI - i) + " North";
        }
        else if(i > currI)
        {
            northSouth = (i - currI) + " South";
        }
        else //they're equal
        {
            northSouth = "Current Row";
        }
        return northSouth;
    }



}
