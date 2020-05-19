package com.example.nba.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nba.R;
import com.example.nba.Singletons;
import com.example.nba.presentation.model.LakersPlayers;
import com.example.nba.presentation.model.WarriorsPlayers;
import com.squareup.picasso.Picasso;

public class LakersDetails extends AppCompatActivity {

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
        String lakersJson = intent.getStringExtra("lakersKey");
        LakersPlayers lakersPlayers = Singletons.getGson().fromJson(lakersJson, LakersPlayers.class);
        showDetail(lakersPlayers);
    }

    private void showDetail(LakersPlayers lakersPlayers) {
        txtfirstName.setText(lakersPlayers.getLakers_firstName());
        txtlastName.setText(lakersPlayers.getLakers_lastName());
        txtjersey.setText("#"+lakersPlayers.getLakers_jersey());
        txtage.setText(lakersPlayers.getLakers_age());
        txtpos.setText(lakersPlayers.getLakers_pos());
        txtheight.setText(lakersPlayers.getLakers_heightMeters()+" m");
        txtweight.setText(lakersPlayers.getLakers_weightKilograms()+" kg");

        Picasso.get().load(lakersPlayers.getLakers_image()).into(image);
    }
}
