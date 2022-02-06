package com.ufcquixada.meutroco.serverCommunication;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ufcquixada.meutroco.Views.LoginActivity;
import com.ufcquixada.meutroco.constantes.MEUTROCO;
import com.ufcquixada.meutroco.models.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

class ResponseLogin {
    User user;
    String token;
}

public class PostLogin extends Thread {

    LoginActivity activity;
    ResponseLogin responseLogin;
    String email;
    String password;


    public PostLogin(LoginActivity activity, String email, String password) {
        this.activity = activity;
        this.email = email;
        this.password = password;
    }

    public void run() {
        String router = "/sessions/users";
        HttpURLConnection urlConnection;
        String result = null;


        try {
            URL url = new URL(MEUTROCO.URLSTRING + router);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("email", this.email);
            jsonParam.put("password", this.password);

            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.close();
            outputStream.close();

            final int responseCode = urlConnection.getResponseCode();
            if(responseCode >= 400) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Error login", Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            result = sb.toString();


            Gson gson = new Gson();
            this.responseLogin = gson.fromJson( result, ResponseLogin.class );

            Log.d("Result", result);
            Log.d("User id", this.responseLogin.user.get_id());
            Log.d("Token", this.responseLogin.token);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.saveUser(responseLogin.user, responseLogin.token);
                }
            });

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
}
