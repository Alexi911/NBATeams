package com.example.nba.presentation.controller;

import android.content.SharedPreferences;

import com.example.nba.presentation.model.CavaliersPlayers;
import com.example.nba.presentation.view.Bulls;
import com.example.nba.Singletons;
import com.example.nba.presentation.model.BullsPlayers;
import com.example.nba.presentation.model.RestNBA;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BullsController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private Bulls view;

    public BullsController(Bulls bulls, Gson gson, SharedPreferences sharedPreferences){
        this.view = bulls;
        this.gson = gson;
        this.sharedPreferences  = sharedPreferences;
    }

    public void onStart(){

        List<BullsPlayers> bullsList = getDataFromCache();
        if(bullsList != null){
            view.showList(bullsList);
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
                    List<BullsPlayers> bullsList = response.body().getBulls_players();
                    saveList(bullsList);
                    view.showList(bullsList);
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

    private void saveList(List<BullsPlayers> bullsList) {
        String jsonString = gson.toJson(bullsList);

        sharedPreferences
                .edit()
                .putString("jsonbullsList", jsonString)
                .apply();
    }

    private List<BullsPlayers> getDataFromCache() {
        String jsonPlayers = sharedPreferences.getString("jsonbullsList", null);

        if(jsonPlayers == null){
            return null;
        } else {
            Type listType = new TypeToken<ArrayList<BullsPlayers>>() {
            }.getType();
            return gson.fromJson(jsonPlayers, listType);
        }
    }

    public void onItemClick(BullsPlayers bullsPlayers) {
        view.navigateToDetails(bullsPlayers);
    }
}
