package com.example.nba.data;

import com.example.nba.presentation.model.RestNBA;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NBAApi {



    @GET("draft.json")
    Call<RestNBA> getNBAResponse();

}
