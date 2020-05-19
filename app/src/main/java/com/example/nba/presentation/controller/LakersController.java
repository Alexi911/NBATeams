package com.example.nba.presentation.controller;

import android.content.SharedPreferences;

import com.example.nba.Singletons;
import com.example.nba.presentation.model.LakersPlayers;
import com.example.nba.presentation.model.RestNBA;
import com.example.nba.presentation.view.Lakers.Lakers;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LakersController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private Lakers view;

    public LakersController(Lakers lakers, Gson gson, SharedPreferences sharedPreferences){
        this.view = lakers;
        this.gson = gson;
        this.sharedPreferences  = sharedPreferences;
    }

    public void onStart(){

        List<LakersPlayers> lakerList = getDataFromCache();
        if(lakerList != null){
            view.showList(lakerList);
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
                    List<LakersPlayers> lakersList = response.body().getLakers_players();
                    saveList(lakersList);
                    view.showList(lakersList);
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

    private void saveList(List<LakersPlayers> lakersList2) {
        String jsonString = gson.toJson(lakersList2);

        sharedPreferences
                .edit()
                .putString("jsonlakersList", jsonString)
                .apply();
    }

    private List<LakersPlayers> getDataFromCache() {
        String jsonPlayers = sharedPreferences.getString("jsonlakersList", null);

        if(jsonPlayers == null){
            return null;
        } else {
            Type listType = new TypeToken<ArrayList<LakersPlayers>>() {
            }.getType();
            return gson.fromJson(jsonPlayers, listType);
        }
    }

    public void onItemClick(LakersPlayers lakersPlayers) {
        view.navigateToDetails(lakersPlayers);
    }
}
