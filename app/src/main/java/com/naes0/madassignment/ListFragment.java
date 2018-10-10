package com.naes0.madassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment
{
    private GameData data;
    private List<Item> itemList = new ArrayList<Item>();
    private ItemAdapter adapter = new ItemAdapter();

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        GameData data = GameData.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.list, ui,false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.buyRecyclerView);
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

    public void setItemList(List<Item> itemList)
    {
        this.itemList.addAll(itemList);
        adapter.notifyDataSetChanged();
    }


}
