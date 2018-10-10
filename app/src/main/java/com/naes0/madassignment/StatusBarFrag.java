package com.naes0.madassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StatusBarFrag extends Fragment
{
    private TextView cashView;
    private TextView healthView;
    private TextView massView;
    private Button restartButton;
    private Player player;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b)
    {
        View view = li.inflate(R.layout.status_bar, parent, false);

        healthView = (TextView) view.findViewById(R.id.health);
        cashView = (TextView) view.findViewById(R.id.cash);
        massView = (TextView) view.findViewById(R.id.mass);
        restartButton = (Button) view.findViewById(R.id.reset);

        healthView.setText("Health: " + Double.toString(player.getHealth()) + "/100.0");
        cashView.setText("Cash: $" + player.getCash());
        massView.setText("Mass: " + Double.toString(player.getEquipMass()) + " kg");

        restartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //currArea set description to that;
            }
        });

        return view;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
        if (healthView != null && cashView != null && massView != null)
        {
            healthView.setText("Health: " + Double.toString(player.getHealth()) + "/100.0");
            cashView.setText("Cash: $" + player.getCash());
            massView.setText("Mass: " + Double.toString(player.getEquipMass()) + " kg");
        }
    }

}
