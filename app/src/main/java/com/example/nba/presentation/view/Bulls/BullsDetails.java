package com.example.nba.presentation.view.Bulls;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nba.R;
import com.example.nba.Singletons;
import com.example.nba.presentation.model.BullsPlayers;
import com.example.nba.presentation.model.WarriorsPlayers;
import com.squareup.picasso.Picasso;

public class BullsDetails extends AppCompatActivity {

    private TextView txtfirstName;
    private TextView txtlastName;
    private TextView txtjersey;
    private TextView txtage;
    private TextView txtpos;
    private TextView txtheight;
    private TextView txtweight;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_details);

        txtfirstName = findViewById(R.id.FirstName_details);
        txtlastName = findViewById(R.id.LastName_details);
        txtjersey = findViewById(R.id.Jersey_details);
        txtage = findViewById(R.id.Age_details);
        txtpos = findViewById(R.id.Pos_details);
        txtheight = findViewById(R.id.HeightMeters_details);
        txtweight = findViewById(R.id.WeightKilograms_details);
        image = findViewById(R.id.Image_details);
        Intent intent = getIntent();
        String bullsJson = intent.getStringExtra("bullsKey");
        BullsPlayers bullsPlayers = Singletons.getGson().fromJson(bullsJson, BullsPlayers.class);
        showDetail(bullsPlayers);
    }

    private void showDetail( BullsPlayers bullsPlayers) {
        txtfirstName.setText(bullsPlayers.getBulls_firstName());
        txtlastName.setText(bullsPlayers.getBulls_lastName());
        txtjersey.setText("#"+bullsPlayers.getBulls_jersey());
        txtage.setText(bullsPlayers.getBulls_age());
        txtpos.setText(bullsPlayers.getBulls_pos());
        txtheight.setText(bullsPlayers.getBulls_heightMeters()+" m");
        txtweight.setText(bullsPlayers.getBulls_weightKilograms()+" kg");

        Picasso.get().load(bullsPlayers.getBulls_image()).into(image);
    }
}
