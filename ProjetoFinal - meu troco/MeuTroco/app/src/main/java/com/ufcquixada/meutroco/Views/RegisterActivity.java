package com.ufcquixada.meutroco.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.serverCommunication.PostUser;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextName, editTextCpf, editTextEmail, editTextPassword;
    PostUser postUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName     = (EditText) findViewById(R.id.editTextTextName);
        editTextCpf      = (EditText) findViewById(R.id.editTextTextCpf);
        editTextEmail    = (EditText) findViewById(R.id.editTextTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
    }

    private boolean validation(String input) {
        if(input == null || input.trim().isEmpty()) {
            return true;
        }

        return false;
    }

    public void onClick_Cadastrar(View view) {
        String name = editTextName.getText().toString();
        String cpf = editTextCpf.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(validation(email)|| validation(password) || validation(cpf) || validation(name)) {
            Toast.makeText(this, "Campos faltantes", Toast.LENGTH_LONG).show();
            return;
        }

        postUser = new PostUser(this, name, cpf, email, password);
        postUser.start();
    }

    public void onClick_Back(View view) {
        finish();
    }

    public void finishRegister() {
        finish();
    }
}