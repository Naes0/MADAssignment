package com.naes0.madassignment;

import android.content.Intent;
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
    private GameData data;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        data = GameData.get(getContext());
        player = data.getPlayer();
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
        massView.setText("Mass: " + player.getEquipMass() + " kg");

        restartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                GameData data = GameData.get(getContext());
                data.reset();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        return view;
    }

    public void update()
    {
        healthView.setText("Health: " + Double.toString(player.getHealth()) + "/100.0");
        cashView.setText("Cash: $" + player.getCash());
        massView.setText("Mass: " + player.getEquipMass() + " kg");
    }
}
