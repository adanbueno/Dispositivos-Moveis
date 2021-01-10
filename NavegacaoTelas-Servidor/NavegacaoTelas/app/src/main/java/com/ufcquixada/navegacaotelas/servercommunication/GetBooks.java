package com.ufcquixada.navegacaotelas.servercommunication;

import android.util.Log;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.ufcquixada.navegacaotelas.MainActivity;
import com.ufcquixada.navegacaotelas.model.Book;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class GetBooks extends Thread {

    MainActivity activity;
    ExpandableListView expandableListView;

    public GetBooks(MainActivity activity, ExpandableListView expandableListView) {
        this.activity = activity;
        this.expandableListView = expandableListView;
    }

    public void run() {
        String stringURL = "http://192.168.1.67:3333/books";
        try {
            URL url = new URL(stringURL);

            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String resp = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) resp += line;


            bufferedReader.close();

            Gson gson = new Gson();
            Book[] books = gson.fromJson( resp, Book[].class );

            final List<Book> listBooks = new ArrayList();
            for (Book book: books) listBooks.add(book);

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    activity.getListBook( listBooks );
                }
            };

            expandableListView.post( runnable );

        } catch ( IOException ex ) {
            ex.printStackTrace();
        }

    }
}
