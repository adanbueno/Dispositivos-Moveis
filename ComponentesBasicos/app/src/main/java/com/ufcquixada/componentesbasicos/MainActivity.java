package com.ufcquixada.componentesbasicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activitys, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Activity_Toggle_button:
                MainActivity.this.startActivity(new Intent(MainActivity.this,
                        Toggle_button.class));
                return true;

            case R.id.Activity_EditText:
                MainActivity.this.startActivity(new Intent(MainActivity.this,
                        Edittext.class));
                return true;

            case R.id.Activity_Autocomplete:
                MainActivity.this.startActivity(new Intent(MainActivity.this,
                        Autocomplete.class));
                return true;

            case R.id.Activity_Spinners:
                MainActivity.this.startActivity(new Intent(MainActivity.this,
                        Spinner_list.class));
                return true;

            case R.id.Activity_RadioButtons:
                MainActivity.this.startActivity(new Intent(MainActivity.this,
                        Radio_buttons.class));
                return true;

            case R.id.Activity_LongPress:
                MainActivity.this.startActivity(new Intent(MainActivity.this,
                        Long_press.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickMenuPopup(View v) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(MainActivity.this, findViewById(R.id.btn_popup));
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.menu_activitys, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });

        popup.show(); //showing popup menu
    }


}