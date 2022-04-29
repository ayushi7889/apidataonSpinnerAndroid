package com.example.setapionrecyclerview.Interface;

import com.example.setapionrecyclerview.entities.State;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiPassId {


    String JSONURL = "https://demonuts.com/Demonuts/JsonTest/Tennis/";

    @GET("json_parsing.php")
    Call<String> getJSONString();








}
