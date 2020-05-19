package com.example.nba.presentation.controller;

import android.content.SharedPreferences;

import com.example.nba.Singletons;
import com.example.nba.presentation.model.RestNBA;
import com.example.nba.presentation.model.WarriorsPlayers;
import com.example.nba.presentation.view.Warriors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WarriorsController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private Warriors view;

    public WarriorsController(Warriors warriors,Gson gson,SharedPreferences sharedPreferences){
        this.view = warriors;
        this.gson = gson;
        this.sharedPreferences  = sharedPreferences;
    }

    public void onStart(){

        List<WarriorsPlayers> playersList = getDataFromCache();
        if(playersList != null){
            view.showList(playersList);
        } else {
            makeApiCall();
        }

    }

    private void makeApiCall() {

        Call<RestNBA> call = Singletons.getNbaApi().getNBAResponse();
        call.enqueue(new Callback<RestNBA>() {
            @Override
            public void onResponse(Call<RestNBA> call, Response<RestNBA> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<WarriorsPlayers> playersList = response.body().getWarriors_players();
                    saveList(playersList);
                    view.showList(playersList);
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestNBA> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List<WarriorsPlayers> playersList2) {
        String jsonString = gson.toJson(playersList2);

        sharedPreferences
                .edit()
                .putString("jsonPlayersList", jsonString)
                .apply();
    }

    private List<WarriorsPlayers> getDataFromCache() {
        String jsonPlayers = sharedPreferences.getString("jsonPlayersList", null);

        if(jsonPlayers == null){
            return null;
        } else {
            Type listType = new TypeToken<ArrayList<WarriorsPlayers>>() {
            }.getType();
            return gson.fromJson(jsonPlayers, listType);
        }
    }

    public void onItemClick(WarriorsPlayers warriorsPlayers){
        view.navigateToDetails(warriorsPlayers);
    }
}
