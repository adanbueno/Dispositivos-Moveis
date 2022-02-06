package com.ufcquixada.meutroco.serverCommunication;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ufcquixada.meutroco.Views.RegisterActivity;
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

public class PostUser extends Thread {

    RegisterActivity activity;
    String name;
    String cpf;
    String email;
    String password;


    public PostUser(RegisterActivity activity, String name, String cpf, String email, String password) {
        this.activity = activity;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
    }

    public void run() {
        String router = "/users";
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
            jsonParam.put("name", this.name);
            jsonParam.put("email", this.email);
            jsonParam.put("cpf", this.cpf);
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
                        Toast.makeText(activity, "Error Register",  Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }


            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();

            Gson gson = new Gson();
            User user = gson.fromJson( result, User.class );

            Log.d("Result", result);
            Log.d("User id", user.get_id());

            if(user.get_id() == null || user.get_id().trim().isEmpty()) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Usuário não registrado", Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show();
                    activity.finishRegister();
                }
            });


        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
}
