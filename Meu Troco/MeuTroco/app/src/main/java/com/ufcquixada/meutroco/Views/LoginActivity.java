package com.ufcquixada.meutroco.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.models.LoggedUser;
import com.ufcquixada.meutroco.models.User;
import com.ufcquixada.meutroco.serverCommunication.PostLogin;

public class LoginActivity extends AppCompatActivity {

    LoggedUser loggedUser;
    EditText editTextEmail, editTextPassword;
    PostLogin postLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText)findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = (EditText)findViewById(R.id.editTextTextPassword);
    }

    private boolean validation(String input) {
        if(input == null || input.trim().isEmpty()) {
            return true;
        }

        return false;
    }

    public void onClick_Entrar(View view) {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(validation(email)|| validation(password)) {
            Toast.makeText(this, "Campos faltantes", Toast.LENGTH_LONG).show();
            return;
        }

        postLogin = new PostLogin(this, email, password);
        postLogin.start();
    }

    public void onClick_Cadastrar(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void error(String message) {
        Toast.makeText(this, message,  Toast.LENGTH_SHORT).show();
    }

    public void saveUser(User user, String token) {
        Intent intent = new Intent(this, Home.class);

        loggedUser.getInstance().setLogin(user, token);

        startActivity(intent);
        finish();
    }
}