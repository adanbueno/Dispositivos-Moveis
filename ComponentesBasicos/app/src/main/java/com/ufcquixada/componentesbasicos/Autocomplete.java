package com.ufcquixada.componentesbasicos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Autocomplete extends Activity {

    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete);

        autoCompleteTextView = findViewById( R.id.autoCompleteTextView );

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                new ArrayList<String>(
                        Arrays.asList("PS2", "PS3", "PS4", "Xbox", "PSP", "Nintendo wi")
                ));

        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adp);
    }

    public void onClickButton_submitAutocomplete( View view ) {
        String textEdtAutoComplete = autoCompleteTextView.getText().toString();

        Toast.makeText(this, textEdtAutoComplete, Toast.LENGTH_SHORT).show();
    }


}
