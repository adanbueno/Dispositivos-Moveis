package com.ufcquixada.componentesbasicos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Long_press extends Activity {

    private TextView txtView_longpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longpress);

        txtView_longpress = findViewById(R.id.textView_longpress);
        txtView_longpress.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("WrongConstant")
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),
                        "Você pressionou por muito tempo :)", 4000).show();
                return true;
            }
        });
    }

    public void onClick_textview(View v) {
        // TODO Auto-generated method stub
        Toast.makeText(getApplicationContext(), "Não pressionou por muito tempo :(",1000).show();
    }

}
