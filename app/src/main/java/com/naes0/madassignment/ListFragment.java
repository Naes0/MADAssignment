package com.naes0.madassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment
{
    private GameData data;
    private List<Item> itemList;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        GameData data = GameData.get();
        itemList = new ArrayList<Item>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.list, ui,false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.buyRecyclerView);
        rv.setLayoutManager(new ConstraintLayoutManager());
        adapter = new GridAdapter();
        rv.setAdapter(adapter);
        return view;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder
    {
        private TextView text;
        private Item item;

        //Viewholder
        public GridViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.grid_cell, parent, false));

            int size = parent.getMeasuredHeight() / MapData.HEIGHT+1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;

            imageView1 = (ImageView) itemView.findViewById(R.id.imageView1);
            imageView2 = (ImageView) itemView.findViewById(R.id.imageView2);
            imageView3 = (ImageView) itemView.findViewById(R.id.imageView3);
            imageView4 = (ImageView) itemView.findViewById(R.id.imageView4);
            imageView5 = (ImageView) itemView.findViewById(R.id.imageView5);
            buildable = false;

            imageView5.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Structure selection = selector.getStructure();
                    if( (selection != null) && buildable)
                    {
                        mapEle.setStructure(selection);
                        adapter.notifyItemChanged(getAdapterPosition());
                    }
                }
            });
        }

        public void bind(MapElement mapEle)
        {
            buildable = mapEle.isBuildable();
            imageView1.setImageResource(mapEle.getNorthWest());
            imageView2.setImageResource(mapEle.getNorthEast());
            imageView3.setImageResource(mapEle.getSouthWest());
            imageView4.setImageResource(mapEle.getSouthEast());
            if (mapEle.getStructure() != null)
            {
                imageView5.setImageResource(mapEle.getStructure().getDrawableId());
            }
            else
            {
                imageView5.setImageResource(0);
            }
            this.mapEle = mapEle;
        }
    }

    //adapter
    public class GridAdapter extends RecyclerView.Adapter<GridViewHolder>
    {
        @Override
        public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new GridViewHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(GridViewHolder holder, int position)
        {
            int row = position % MapData.HEIGHT;
            int col = position / MapData.HEIGHT;
            holder.bind(map.get(row, col));
        }

        @Override
        public int getItemCount()
        {
            return MapData.HEIGHT*MapData.WIDTH;
        }
    }

}
