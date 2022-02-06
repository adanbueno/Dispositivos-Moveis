package com.ufcquixada.navegacaotelas.serverconection;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ufcquixada.navegacaotelas.model.Book;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetLivro extends Thread {
    Wallet activity;
    TextView textView;
    Book book;
    LoggedUser loggedUser;

    public GetLivro(Wallet activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
    }

    public void run() {
        String router = "/livros";
        HttpURLConnection urlConnection = null;
        String result = null;

        try {
            URL url = new URL("http://192.168.1.67:3333" + router);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();


            final int responseCode = urlConnection.getResponseCode();

            InputStream test;
            if(responseCode >= 400) {
                test = urlConnection.getErrorStream();
                activity.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getActivity(), "Error get livro", Toast.LENGTH_LONG).show();
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
            this.book = gson.fromJson( result, Book.class );

            loggedUser.getInstance().setUser(book);

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
