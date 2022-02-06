package com.ufcquixada.meutroco.serverCommunication;

import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ufcquixada.meutroco.Views.ExtractActivity;
import com.ufcquixada.meutroco.constantes.MEUTROCO;
import com.ufcquixada.meutroco.models.LoggedUser;
import com.ufcquixada.meutroco.models.Transfer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetExtract extends Thread {
    ExtractActivity activity;
    ListView listView;
    Transfer[] transfers;
    LoggedUser loggedUser;

    public GetExtract(ExtractActivity activity, ListView listView) {
        this.activity = activity;
        this.listView = listView;
    }

    public void run() {
        String router = "/extracts";
        HttpURLConnection urlConnection = null;
        String result = null;

        try {
            URL url = new URL(MEUTROCO.URLSTRING + router);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("authorization", "Bearer " + this.loggedUser.getInstance().getToken());
            urlConnection.connect();


            final int responseCode = urlConnection.getResponseCode();

            InputStream test;
            if(responseCode >= 400) {
                test = urlConnection.getErrorStream();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Error extracts", Toast.LENGTH_LONG).show();
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
            this.transfers = gson.fromJson( result, Transfer[].class );

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    activity.loadTransfer(transfers);
                }
            };


            listView.post(runnable);
        } catch ( Exception ex ) {
            ex.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
