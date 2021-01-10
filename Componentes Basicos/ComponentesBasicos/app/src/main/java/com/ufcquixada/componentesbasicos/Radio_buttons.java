package com.ufcquixada.componentesbasicos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Radio_buttons extends Activity {

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiobuttons);

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
    }

    public void onClear(View v) {
        radioGroup.clearCheck();
    }

    public void onSubmit(View v) {
        RadioButton rb = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());

        Toast.makeText(this, rb.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
