package com.example.nba.Cavaliers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.nba.NBAApi;
import com.example.nba.PlayersDetails;
import com.example.nba.R;
import com.example.nba.RestNBA;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cavaliers extends AppCompatActivity implements CavaliersAdapter.CavaliersOnNoteListener {

    private static final String BASE_URL = "https://raw.githubusercontent.com/Alexi911/NBA/master/";
    private static final String TAG = "Cavaliers";

    private RecyclerView recyclerView;
    private CavaliersAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        sharedPreferences = getSharedPreferences("Cavaliers", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<CavaliersPlayers> cavaliersList = getDataFromCache();
        if(cavaliersList != null){
            showList(cavaliersList);
        } else {
            makeApiCall();
        }
    }

    private List<CavaliersPlayers> getDataFromCache() {
        String jsonPlayers = sharedPreferences.getString("jsonCavaliersList", null);

        if(jsonPlayers == null){
            return null;
        } else {
            Type listType = new TypeToken<ArrayList<CavaliersPlayers>>() {
            }.getType();
            return gson.fromJson(jsonPlayers, listType);
        }
    }

    private void showList(List<CavaliersPlayers> cavaliersList) {

        recyclerView = (RecyclerView) findViewById(R.id.bulls_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new CavaliersAdapter(cavaliersList,this);
        recyclerView.setAdapter(mAdapter);
    }

    private void makeApiCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        NBAApi NBApi = retrofit.create(NBAApi.class);

        Call<RestNBA> call = NBApi.getNBAResponse();
        call.enqueue(new Callback<RestNBA>() {
            @Override
            public void onResponse(Call<RestNBA> call, Response<RestNBA> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<CavaliersPlayers> cavaliersList = response.body().getCavaliers_players();
                    saveList(cavaliersList);
                    showList(cavaliersList);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestNBA> call, Throwable t) {
                showError();
            }
        });
    }

    private void saveList(List<CavaliersPlayers> cavaliersList) {
        String jsonString = gson.toJson(cavaliersList);

        sharedPreferences
                .edit()
                .putString("jsonCavaliersList", jsonString)
                .apply();

        Toast.makeText(getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();
    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoteCLick(int position) {
        Log.d(TAG,"onNoteClick: clicked"+position);
        Intent intent = new Intent(this, PlayersDetails.class);
        startActivity(intent);
    }
}