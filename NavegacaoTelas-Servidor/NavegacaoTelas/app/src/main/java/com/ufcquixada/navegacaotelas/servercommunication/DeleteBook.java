package com.ufcquixada.navegacaotelas.servercommunication;

import android.widget.ExpandableListView;

import com.ufcquixada.navegacaotelas.MainActivity;

import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteBook extends Thread {

    MainActivity activity;
    ExpandableListView expandableListView;
    int id;

    public DeleteBook( MainActivity activity, ExpandableListView expandableListView, int id ) {
        this.activity = activity;
        this.expandableListView = expandableListView;
        this.id = id;
    }

    public void run() {
        String stringURL = "http://192.168.1.67:3333/books" + "/" + String.valueOf(this.id);
        HttpURLConnection urlConnection;

        try {
            URL url = new URL(stringURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");

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
                        activity.deleteBook( id );
                    }
                };
            }



            expandableListView.post( runnable );

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
}
