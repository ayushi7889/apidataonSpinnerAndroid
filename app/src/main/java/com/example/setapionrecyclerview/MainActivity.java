package com.example.setapionrecyclerview;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.setapionrecyclerview.Interface.ApiPassId;
import com.example.setapionrecyclerview.entities.State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ArrayList<State> goodModelArrayList;
    private ArrayList<String> playerNames = new ArrayList<String>();
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinner);
        getState();


    }

    private void getState() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiPassId.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();


        ApiPassId apiPassId = retrofit.create(ApiPassId.class);
        Call<String> call = apiPassId.getJSONString();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse:" + response.body().toString());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("Success", response.body().toString());

                        String getResponse = response.body().toString();
                        spinJson(getResponse);
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    private void spinJson(String response) {
        try {

            JSONObject obj = new JSONObject(response);
            if (obj.optString("status").equals("true")) {

                goodModelArrayList = new ArrayList<>();
                JSONArray dataArray = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    State spinnerModel = new State();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    spinnerModel.setName(dataobj.getString("name"));
                    spinnerModel.setCountry(dataobj.getString("country"));
                    spinnerModel.setCity(dataobj.getString("city"));
                    spinnerModel.setImgURL(dataobj.getString("imgURL"));

                    goodModelArrayList.add(spinnerModel);

                }

                for (int i = 0; i < goodModelArrayList.size(); i++) {
                    playerNames.add(goodModelArrayList.get(i).getName().toString());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, playerNames);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner.setAdapter(spinnerArrayAdapter);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}







