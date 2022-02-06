package br.ufc.quixada.dadm.entreg02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Adicionar extends AppCompatActivity {

    public static int RESULT_ADD = 1;
    public static int RESULT_CANCEL = 2;
    public static int RESULT_EDIT = 3;
    EditText textName;
    EditText textGenero;

    boolean edit;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar2);

        textName = findViewById(R.id.textName);
        textGenero = findViewById(R.id.textGenero);

        if(getIntent().getExtras() != null){
            String nome = (String) getIntent().getExtras().get("nome");
            String genero = (String) getIntent().getExtras().get("genero");

            textName.setText(nome);
            textGenero.setText(genero);

            edit = true;
        }

    }

    public void adicionar(View view){

        Intent i = new Intent();

        String nome = textName.getText().toString();
        String genero = textGenero.getText().toString();

        i.putExtra("nome", nome);
        i.putExtra("genero", genero);
       // i.putExtra("idLivro", id);

        setResult(RESULT_ADD, i);
        finish();
    }

    public void cancel(View view){
        setResult(RESULT_CANCEL);
        finish();
    }

}