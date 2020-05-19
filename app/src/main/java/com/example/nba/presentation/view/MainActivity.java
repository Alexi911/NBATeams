package com.example.nba.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.nba.presentation.model.MainModel;
import com.example.nba.R;

import com.example.nba.presentation.view.Bulls.Bulls;
import com.example.nba.presentation.view.Cavaliers.Cavaliers;
import com.example.nba.presentation.view.Lakers.Lakers;
import com.example.nba.presentation.view.Warriors.Warriors;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnNoteListener {

    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;

    private SharedPreferences sharedPreferences;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        Integer[] langLogo = {R.mipmap.ic_gsw_foreground,R.mipmap.ic_cavaliers_foreground,R.mipmap.ic_lakers_foreground,R.mipmap.ic_bulls_foreground};

        String[] langName = {"Warriors","Cavaliers","Lakers","Bulls"};

        mainModels = new ArrayList<>();
        for (int i=0;i<langLogo.length;i++){
            MainModel model = new MainModel(langLogo[i],langName[i]);
            mainModels.add(model);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                MainActivity.this,LinearLayoutManager.HORIZONTAL,false
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mainAdapter = new MainAdapter(MainActivity.this,mainModels,this);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    public void onNoteCLick(int position) {
        Log.d(TAG,"onNoteClick: clicked"+ position);

        if (position==0){
            Intent intent = new Intent(this, Warriors.class);
            startActivity(intent);
        }else if(position==1) {
            Intent intento = new Intent(this, Cavaliers.class);
            startActivity(intento);
        }else if(position==2) {
            Intent intento = new Intent(this, Lakers.class);
            startActivity(intento);
        }else if(position==3) {
            Intent intento = new Intent(this, Bulls.class);
            startActivity(intento);
        }
    }


}