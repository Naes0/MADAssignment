package com.naes0.madassignment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class SellListFragment extends Fragment
{
    private GameData data;
    private Player player;
    private List<Equipment> equipmentList;
    private ItemAdapter adapter;
    private Equipment selectedEquipment;
    private int selectedPos = RecyclerView.NO_POSITION;


    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        data = GameData.get(getContext());
        player = data.getPlayer();
        equipmentList = player.getEquipmentlist();
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
        private Equipment equipment;

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
                    if (getAdapterPosition() == RecyclerView.NO_POSITION)
                    {
                        return;
                    }

                    selectedEquipment = equipment;
                    //notifys adapter
                    adapter.notifyItemChanged(selectedPos);
                    selectedPos = getAdapterPosition();
                    adapter.notifyItemChanged(selectedPos);
                }
            });
        }

        public void bind(Equipment equipment)
        {
            name.setText(equipment.getDesc());
            value.setText("Value: " + equipment.getValue());
            masshealth.setText("Mass: " + Double.toString(equipment.getMassOrHealth()));
            this.equipment = equipment;
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
            //used to highlight the selected viewholder.
            if(selectedPos == position)
            {
                holder.name.setTextColor(Color.WHITE);
                holder.value.setTextColor(Color.WHITE);
                holder.masshealth.setTextColor(Color.WHITE);
            }
            else
            {
                holder.name.setTextColor(Color.BLACK);
                holder.value.setTextColor(Color.BLACK);
            }
            holder.bind(equipmentList.get(position));
        }

        @Override
        public int getItemCount()
        {
            return equipmentList.size();
        }
    }

    public void update()
    {
        data = GameData.get(getContext());
        player = data.getPlayer();
        equipmentList = player.getEquipmentlist();
        data.updatePlayer(player);
        adapter.notifyDataSetChanged();
    }

    public Equipment getSelectedEquipment()
    {
        return selectedEquipment;
    }

    public void clearSelection()
    {
        selectedEquipment = null;
    }
}
