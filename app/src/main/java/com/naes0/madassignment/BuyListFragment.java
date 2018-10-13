package com.naes0.madassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class BuyListFragment extends Fragment
{
    private GameData data;
    private Player player;
    private Area currArea;
    private List<Item> itemList;
    private ItemAdapter adapter;
    private Item selectedItem;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        data = GameData.get();
        player = data.getPlayer();
        currArea = data.getArea(player.getRow(), player.getCol());
        itemList = currArea.getitemList();
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
        private TextView value;
        private TextView masshealth;
        private Item item;

        public ItemViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_entry, parent, false));

            name = (TextView) itemView.findViewById(R.id.itemname);
            value = (TextView) itemView.findViewById(R.id.value);
            masshealth = (TextView) itemView.findViewById(R.id.masshealth);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                   selectedItem = item;
                }
            });
        }

        public void bind(Item item)
        {
            name.setText(item.getDesc());
            value.setText("Value: " + item.getValue());
            masshealth.setText("Mass: " + Double.toString(item.getMassOrHealth()));
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
            holder.bind(itemList.get(position));
        }

        @Override
        public int getItemCount()
        {
            return itemList.size();
        }
    }

    public Item getSelectedItem()
    {
        return selectedItem;
    }

    public void clearSelection()
    {
        selectedItem = null;
    }

    public void update()
    {
        adapter.notifyDataSetChanged();
    }

}
