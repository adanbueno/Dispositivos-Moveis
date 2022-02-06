package com.ufcquixada.meutroco.serverCommunication;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.ufcquixada.meutroco.Views.RegisterActivity;
import com.ufcquixada.meutroco.constantes.MEUTROCO;
import com.ufcquixada.meutroco.fragments.EditInforUser;
import com.ufcquixada.meutroco.models.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PutUserInfor extends Thread {

    EditInforUser activity;
    String name;
    String email;
    String token;


    public PutUserInfor(EditInforUser activity, String name, String email, String token) {
        this.activity = activity;
        this.name = name;
        this.email = email;
        this.token = token;
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
            urlConnection.setRequestProperty("authorization", "Bearer " + this.token);
            urlConnection.setRequestMethod("PUT");
            urlConnection.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("name", this.name);
            jsonParam.put("email", this.email);

            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.close();
            outputStream.close();

            final int responseCode = urlConnection.getResponseCode();
            final InputStream response;
            if(responseCode >= 400) {
                response = urlConnection.getErrorStream();
            } else {
                response = urlConnection.getInputStream();
            }


            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response, "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();

            if(responseCode >= 400) {
                final String finalResult = result;
                activity.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getActivity(), finalResult,  Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }

            Gson gson = new Gson();
            final User user = gson.fromJson( result, User.class );

            Log.d("Result", result);
            Log.d("New user", user.toString());

            activity.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity.getActivity(), "Usuário atualizado com sucesso", Toast.LENGTH_LONG).show();
                    activity.finishSave(user);
                }
            });


        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
}
