package com.ufcquixada.componentesbasicos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class Edittext extends Activity {

    private EditText edtLog;
    private EditText edtInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);

        edtLog = findViewById( R.id.edtLog );
        edtInput = findViewById( R.id.edtInput );
    }

    public void onClick_submission( View view ) {
        String textEdtInput = edtInput.getText().toString();
        edtInput.setText( "" );

        String textEdtLog = edtLog.getText().toString();
        textEdtLog += " " + textEdtInput;
        edtLog.setText( textEdtLog );
    }

}
