package com.ufcquixada.meutroco.serverCommunication;

import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.ufcquixada.meutroco.constantes.MEUTROCO;
import com.ufcquixada.meutroco.fragments.Offers;
import com.ufcquixada.meutroco.models.Store;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetStore extends Thread {

    Offers activity;
    Store[] stores;
    String token;
    ListView listViewStores;
    double latitude;
    double longitude;

    public GetStore(Offers activity, ListView listViewStores, String token, double latitude, double longitude) {
        this.activity = activity;
        this.listViewStores = listViewStores;
        this.token = token;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void run() {
        String router = "/searchs?longitude=" + this.longitude + "&latitude=" + this.latitude;
        HttpURLConnection urlConnection = null;
        String result = null;

        try {
            URL url = new URL(MEUTROCO.URLSTRING + router);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("authorization", "Bearer " + this.token);
            urlConnection.connect();


            final int responseCode = urlConnection.getResponseCode();

            InputStream test;
            if(responseCode >= 400) {
                test = urlConnection.getErrorStream();
                activity.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getActivity(), "Error login", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                test = urlConnection.getInputStream();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(test, "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();
            Log.d("Result", result);

            Gson gson = new Gson();
            this.stores = gson.fromJson( result, Store[].class );

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    activity.loadStore(stores);
                }
            };


            listViewStores.post(runnable);
        } catch ( Exception ex ) {
            ex.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

}
