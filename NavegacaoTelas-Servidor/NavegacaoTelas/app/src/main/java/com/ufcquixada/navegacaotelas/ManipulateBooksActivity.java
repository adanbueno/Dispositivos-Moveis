package com.ufcquixada.navegacaotelas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ManipulateBooksActivity extends AppCompatActivity {

    //
    public static int RESULT_CONFIRM = 1;
    public static int RESULT_CANCEL = 0;

    // Screen input elements
    private EditText editText_title;
    private EditText editText_author;
    private EditText editText_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulate_books);

        editText_title  = findViewById( R.id.editText_title );
        editText_author = findViewById( R.id.editText_author );
        editText_value  = findViewById( R.id.editText_value );

        if( getIntent().getExtras() != null ) {
            String title = (String) getIntent().getExtras().get("title");
            String author = (String) getIntent().getExtras().get("author");
            int value = (int) getIntent().getExtras().get("value");
            editText_title.setText(title);
            editText_author.setText(author);
            editText_value.setText(String.valueOf(value));
        }
    }

    public void onClick_Confirm( View view ) {
        Intent intent = new Intent();

        String title = editText_title.getText().toString();
        String author = editText_author.getText().toString();
        String valueStr = editText_value.getText().toString();

        if(validation(title)|| validation(author) || validation(valueStr)) {
            Toast.makeText(this, "Campos faltantes", Toast.LENGTH_LONG).show();
            return;
        }

        int value = Integer.parseInt(valueStr);
        
        intent.putExtra("title", title);
        intent.putExtra("author", author);
        intent.putExtra("value", value);

        setResult(RESULT_CONFIRM, intent);
        finish();
    }

    public  void on_Click_Cancel( View view ) {
        setResult(RESULT_CANCEL);
        finish();
    }

    private boolean validation(String input) {
        if(input == null || input.trim().isEmpty()) 
            return true;
        
        return false;
    }
}