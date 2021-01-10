package com.ufcquixada.navegacaotelas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ufcquixada.navegacaotelas.controllers.BookController;
import com.ufcquixada.navegacaotelas.model.Book;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter adapter;
    ListView listViewBooks;
    EditText editText_id;

    //
    private BookController bookController;
    public static int REQUEST_ADD = 1;
    public static int REQUEST_EDIT = 2;
    private int selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookController = new BookController();

        selected = -1;
        editText_id = findViewById( R.id.editText_id );
        editText_id.setText("");

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                bookController.getListBooks() );

        listViewBooks = findViewById(R.id.list_book);
        listViewBooks.setAdapter(adapter);
        listViewBooks.setSelector(android.R.color.holo_blue_light);

        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected = bookController.getIdByPosition(i);
                String menssage = "Selecionado id: " + String.valueOf(selected);
                Toast.makeText(MainActivity.this, menssage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                add();
                return true;
            case R.id.menu_edit:
                edit();
                return true;
            case R.id.menu_remove:
                onClick_MenuRemove();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode == ManipulateBooksActivity.RESULT_CONFIRM && data.getExtras() != null ) {
            String title = (String) data.getExtras().get("title");
            String author = (String) data.getExtras().get("author");
            Float value = (Float) data.getExtras().get("value");

            if( requestCode == REQUEST_ADD ) {
                bookController.addBook(title.trim(), author.trim(), value);
            } else if( requestCode == REQUEST_EDIT ) {
                bookController.updateBook(selected, title.trim(), author.trim(), value);
            }
            adapter.notifyDataSetChanged();
        } else if( resultCode == ManipulateBooksActivity.RESULT_CANCEL ) {
            Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
        }

        editText_id.setText("");
        selected = -1;
    }

    public void onClick_Add(View view) {
        add();
    }

    public void onClick_Edit(View view) {
        edit();
    }

    //olhar para os de cima dps
    private void add() {
        Intent intent = new Intent(this, ManipulateBooksActivity.class);
        startActivityForResult(intent, REQUEST_ADD);
    }

    private void edit() {
        Intent intent = new Intent(this, ManipulateBooksActivity.class);

        String idStr = editText_id.getText().toString();

        if(idStr != "" && !idStr.trim().isEmpty() && idStr.matches("^[0-9]*$")){
            selected = Integer.parseInt(idStr);
        }

        if(selected < 0) {
            Toast.makeText(this,
                    "Selecione um item\n ou digite um id",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Book book = bookController.getBook(selected);

        if(book == null){
            Toast.makeText(this, "Identificador não existe", Toast.LENGTH_SHORT).show();
            return;
        }

        intent.putExtra("title", book.getTitle());
        intent.putExtra("author", book.getAuthor());
        intent.putExtra("value", book.getValue());

        startActivityForResult(intent, REQUEST_EDIT);
    }

    private void onClick_MenuRemove() {
        if(selected >= 0) {
            bookController.removeBook(selected);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Selecione um item", Toast.LENGTH_SHORT).show();
        }
    }
}