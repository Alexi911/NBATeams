package com.example.nba.presentation.view.Warriors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nba.R;
import com.example.nba.Singletons;
import com.example.nba.presentation.model.WarriorsPlayers;
import com.squareup.picasso.Picasso;

public class WarriorsDetails extends AppCompatActivity {

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
        String warriorsJson = intent.getStringExtra("warriorsKey");
        WarriorsPlayers warriorsPlayers = Singletons.getGson().fromJson(warriorsJson, WarriorsPlayers.class);
        showDetail(warriorsPlayers);
    }

    private void showDetail(WarriorsPlayers warriorsPlayers) {
        txtfirstName.setText(warriorsPlayers.getWarriors_firstName());
        txtlastName.setText(warriorsPlayers.getWarriors_lastName());
        txtjersey.setText("#"+warriorsPlayers.getWarriors_jersey());
        txtage.setText(warriorsPlayers.getWarriors_age());
        txtpos.setText(warriorsPlayers.getWarriors_pos());
        txtheight.setText(warriorsPlayers.getWarriors_heightMeters()+" m");
        txtweight.setText(warriorsPlayers.getWarriors_weightKilograms()+" kg");

        Picasso.get().load(warriorsPlayers.getWarriors_image()).into(image);
    }
}
