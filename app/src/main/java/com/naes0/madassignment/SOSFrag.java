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
        data = GameData.get();
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
            String itemNameLocation = stringList.get(position);
            String[] parts = itemNameLocation.split("\\|\\|\\|");
            Log.d("STRING", parts.toString());
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

        int upperRow = Math.max(pRow-2, 0);
        int upperCol = Math.max(0, pCol-2);
        int lowerRow = Math.min(pRow+2, GameData.HEIGHT-1);
        int lowerCol = Math.min(pCol+2, GameData.WIDTH-1);

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

    public void update()
    {
        data = GameData.get();
        player = data.getPlayer();
        adapter.notifyDataSetChanged();
    }

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
        else
        {
            eastWest = "Current Col";
        }

        return eastWest;
    }

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
        else
        {
            northSouth = "Current Row";
        }
        return northSouth;
    }



}
