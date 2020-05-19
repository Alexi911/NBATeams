package com.example.nba.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nba.R;
import com.example.nba.Singletons;
import com.example.nba.presentation.model.CavaliersPlayers;
import com.squareup.picasso.Picasso;

public class CavaliersDetails extends AppCompatActivity {

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
        String cavaliersJson = intent.getStringExtra("cavaliersKey");
        CavaliersPlayers cavaliersPlayers = Singletons.getGson().fromJson(cavaliersJson, CavaliersPlayers.class);
        showDetail(cavaliersPlayers);
    }

    private void showDetail(CavaliersPlayers cavaliersPlayers) {
        txtfirstName.setText(cavaliersPlayers.getCavaliers_firstName());
        txtlastName.setText(cavaliersPlayers.getCavaliers_lastName());
        txtjersey.setText("#"+cavaliersPlayers.getCavaliers_jersey());
        txtage.setText(cavaliersPlayers.getCavaliers_age());
        txtpos.setText(cavaliersPlayers.getCavaliers_pos());
        txtheight.setText(cavaliersPlayers.getCavaliers_heightMeters()+" m");
        txtweight.setText(cavaliersPlayers.getCavaliers_weightKilograms()+" kg");

        Picasso.get().load(cavaliersPlayers.getCavaliers_image()).into(image);
    }
}