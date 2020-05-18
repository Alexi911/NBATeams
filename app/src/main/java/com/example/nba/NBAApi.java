package com.example.nba;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NBAApi {



    @GET("draft.json")
    Call<RestNBA> getNBAResponse();

}
