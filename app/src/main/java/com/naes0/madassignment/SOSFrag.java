package com.naes0.madassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private List<Item> combinedList;
    private ItemAdapter adapter;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        data = GameData.get();
        player = data.getPlayer();
        combinedList = setCombinedList();
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

        public void bind(Item item)
        {
            name.setText(item.getDesc());
            eastwest.setText("Value: " + item.getValue());
            northsouth.setText("Mass: " + Double.toString(item.getMassOrHealth()));
            this.item = item;
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
            holder.bind(combinedList.get(position));
        }

        @Override
        public int getItemCount()
        {
            return combinedList.size();
        }
    }

    public List<Item> setCombinedList()
    {
        List<Item> itemList = new ArrayList<Item>();
        int pRow = player.getRow();
        int pCol = player.getCol();


        int upperRow = Math.max(pRow+2, GameData.HEIGHT);
        int upperCol = Math.max(0, pCol-2);
        int lowerRow = Math.min(0, pRow-2);
        int lowerCol = Math.min(pCol+2, GameData.WIDTH);

        for(int i = upperRow; i < lowerRow; i++)
        {
            for(int j = upperCol; j < lowerCol; j++ )
            {
                combinedList.addAll(data.getArea(i,j).getitemList());
            }
        }
        return itemList;
    }


    public void update()
    {
        data = GameData.get();
        player = data.getPlayer();
        combinedList = setCombinedList();
        adapter.notifyDataSetChanged();
    }

}
