package com.ufcquixada.componentesbasicos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Spinner_list extends Activity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinners);

        spinner = findViewById( R.id.spn_games );

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                new ArrayList<String>(
                        Arrays.asList("PS2", "PS3", "PS4", "Xbox", "PSP", "Nintendo wi")
                ));

        spinner.setAdapter(adp);
    }

    public void onClickButton_SubmitSpinners( View view ) {
        String textEdtAutoComplete = spinner.getSelectedItem().toString();

        Toast.makeText(this, textEdtAutoComplete, Toast.LENGTH_SHORT).show();
    }

}
