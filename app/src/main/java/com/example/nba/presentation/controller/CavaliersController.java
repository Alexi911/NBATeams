package com.example.nba.presentation.controller;

import android.content.SharedPreferences;

import com.example.nba.presentation.view.Cavaliers.Cavaliers;
import com.example.nba.Singletons;
import com.example.nba.presentation.model.RestNBA;
import com.example.nba.presentation.model.CavaliersPlayers;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CavaliersController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private Cavaliers view;

    public CavaliersController(Cavaliers cavaliers, Gson gson, SharedPreferences sharedPreferences){
        this.view = cavaliers;
        this.gson = gson;
        this.sharedPreferences  = sharedPreferences;
    }

    public void onStart(){

        List<CavaliersPlayers> cavaliersList = getDataFromCache();
        if(cavaliersList != null){
            view.showList(cavaliersList);
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
                    List<CavaliersPlayers> cavaliersList = response.body().getCavaliers_players();
                    saveList(cavaliersList);
                    view.showList(cavaliersList);
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

    private void saveList(List<CavaliersPlayers> cavaliersList) {
        String jsonString = gson.toJson(cavaliersList);

        sharedPreferences
                .edit()
                .putString("jsoncavaliersList", jsonString)
                .apply();
    }

    private List<CavaliersPlayers> getDataFromCache() {
        String jsonPlayers = sharedPreferences.getString("jsoncavaliersList", null);

        if(jsonPlayers == null){
            return null;
        } else {
            Type listType = new TypeToken<ArrayList<CavaliersPlayers>>() {
            }.getType();
            return gson.fromJson(jsonPlayers, listType);
        }
    }

    public void onItemClick(CavaliersPlayers cavaliersPlayers) {
        view.navigateToDetails(cavaliersPlayers);
    }
}