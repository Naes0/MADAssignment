package com.naes0.madassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Map;

public class MapFragment extends Fragment
{
    private GameData data;
    private GridAdapter adapter;
    private Area selectedArea;
    private AreaInfoFrag areaInfoFrag;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        data = GameData.get();
        adapter = new GridAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.fragment_map, ui,false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.mapRecyclerView);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), GameData.HEIGHT, GridLayoutManager.HORIZONTAL, false));
        adapter = new GridAdapter();
        rv.setAdapter(adapter);
        return view;
    }

    private class GridViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView imageView1;
        private ImageView imageView2;
        private ImageView imageView3;
        private ImageView imageView4;
        private ImageView imageView5;
        private ImageView imageView6;
        private ImageView imageView7;
        private ImageView imageView8;
        private Area area;

        //Viewholder
        public GridViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.grid_cell, parent, false));

            int size = parent.getMeasuredHeight() / GameData.HEIGHT+1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;

            imageView1 = (ImageView) itemView.findViewById(R.id.imageView1);
            imageView2 = (ImageView) itemView.findViewById(R.id.imageView2);
            imageView3 = (ImageView) itemView.findViewById(R.id.imageView3);
            imageView4 = (ImageView) itemView.findViewById(R.id.imageView4);
            imageView5 = (ImageView) itemView.findViewById(R.id.imageView5);
            imageView6 = (ImageView) itemView.findViewById(R.id.imageView6);
            imageView7 = (ImageView) itemView.findViewById(R.id.imageView7);
            imageView8 = (ImageView) itemView.findViewById(R.id.imageView8);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    areaInfoFrag.updateForArea(area);
                }
            });
        }

        public void bind(Area area)
        {
            imageView1.setImageResource(area.getNorthWest());
            imageView2.setImageResource(area.getNorthEast());
            imageView3.setImageResource(area.getSouthWest());
            imageView4.setImageResource(area.getSouthEast());
            imageView5.setImageResource(area.getStructure());
            imageView6.setImageResource(area.getUnexplored());
            imageView7.setImageResource(area.getUnstarred());
            imageView8.setImageResource(R.color.transparent);

            if(area.equals(data.getCurrArea()))
            {
                imageView8.setImageResource(R.drawable.player);
            }
            this.area = area;
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
            int row = position % GameData.HEIGHT;
            int col = position / GameData.HEIGHT;
            holder.bind(data.getArea(row, col));
        }

        @Override
        public int getItemCount()
        {
            return GameData.HEIGHT*GameData.WIDTH;
        }
    }

    public void update()
    {
        data = GameData.get();
        adapter.notifyDataSetChanged();
    }

    public void setAreaInfoFrag(AreaInfoFrag areaInfoFrag)
    {
        this.areaInfoFrag = areaInfoFrag;
    }

    public Area getSelectedArea()
    {
        return selectedArea;
    }

}
