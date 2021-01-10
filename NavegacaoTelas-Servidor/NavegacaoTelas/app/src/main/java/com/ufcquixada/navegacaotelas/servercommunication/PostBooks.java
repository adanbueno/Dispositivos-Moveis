package com.ufcquixada.navegacaotelas.servercommunication;

import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.ufcquixada.navegacaotelas.MainActivity;
import com.ufcquixada.navegacaotelas.controllers.BookController;
import com.ufcquixada.navegacaotelas.model.Book;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostBooks extends Thread {

    MainActivity activity;
    ExpandableListView expandableListView;
    Book book;

    public PostBooks( MainActivity activity, ExpandableListView expandableListView, Book book ) {
        this.activity = activity;
        this.expandableListView = expandableListView;
        this.book = book;
    }

    public void run() {
        String stringURL = "http://192.168.1.67:3333/books";
        HttpURLConnection urlConnection;
        String result = null;


        try {
            URL url = new URL(stringURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("title", this.book.getTitle());
            jsonParam.put("author", this.book.getAuthor());
            jsonParam.put("value", this.book.getValue());

            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.close();
            outputStream.close();


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
            this.book = gson.fromJson( result, Book.class );


            int responseCode = urlConnection.getResponseCode();

            Runnable runnable;

            if(responseCode > 400) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        activity.error( );
                    }
                };
            } else {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        activity.postBook( book );
                    }
                };
            }

            expandableListView.post( runnable );

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
}
