package com.ufcquixada.meutroco.serverCommunication;

import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ufcquixada.meutroco.Views.ExtractActivity;
import com.ufcquixada.meutroco.constantes.MEUTROCO;
import com.ufcquixada.meutroco.fragments.Wallet;
import com.ufcquixada.meutroco.models.LoggedUser;
import com.ufcquixada.meutroco.models.Transfer;
import com.ufcquixada.meutroco.models.User;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetUser extends Thread {
    Wallet activity;
    TextView textView;
    User user;
    LoggedUser loggedUser;

    public GetUser(Wallet activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
    }

    public void run() {
        String router = "/users";
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
                activity.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getActivity(), "Error get user", Toast.LENGTH_LONG).show();
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
            this.user = gson.fromJson( result, User.class );

            loggedUser.getInstance().setUser(user);

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    activity.loadUser();
                }
            };


            textView.post(runnable);
        } catch ( Exception ex ) {
            ex.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
