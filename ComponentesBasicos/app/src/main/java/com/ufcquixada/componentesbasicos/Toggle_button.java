package com.ufcquixada.componentesbasicos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Toggle_button extends Activity {

    private ToggleButton toggleButton1, toggleButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_button);

        toggleButton1 = findViewById(R.id.toggleButton1);
        toggleButton2 = findViewById(R.id.toggleButton2);
    }

    public void onClick_ToggleButton( View view ) {
        StringBuffer result = new StringBuffer();
        result.append("toggleButton1 : ").append(toggleButton1.getText());
        result.append("\ntoggleButton2 : ").append(toggleButton2.getText());

        Toast.makeText(this, result.toString(),
                Toast.LENGTH_SHORT).show();
    }

    public void onClick_back(View v) {
        finish();
    }
}
