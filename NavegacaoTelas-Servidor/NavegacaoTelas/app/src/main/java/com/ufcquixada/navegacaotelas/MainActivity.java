package com.ufcquixada.navegacaotelas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ufcquixada.navegacaotelas.servercommunication.DeleteBook;
import com.ufcquixada.navegacaotelas.servercommunication.GetBooks;
import com.ufcquixada.navegacaotelas.controllers.BookController;
import com.ufcquixada.navegacaotelas.model.Book;
import com.ufcquixada.navegacaotelas.components.ExpandableListAdapter;
import com.ufcquixada.navegacaotelas.servercommunication.PostBooks;
import com.ufcquixada.navegacaotelas.servercommunication.UpdateBooks;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//    ArrayAdapter adapter;
//    ListView listViewBooks;
    EditText editText_id;
    ExpandableListView expandableListView;
    ExpandableListAdapter adapter;
    ProgressBar progressBar;
    GetBooks getBooks;
    PostBooks postBooks;
    UpdateBooks updateBooks;

    //
    private BookController bookController;
    public static int REQUEST_ADD = 1;
    public static int REQUEST_EDIT = 2;
    private int id_select = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_id = findViewById( R.id.editText_id );
        editText_id.setText("");

        expandableListView = findViewById(R.id.list_book);

        progressBar = findViewById( R.id.progressBar );
        progressBar.setIndeterminate( true );
        progressBar.setVisibility( View.VISIBLE );

        getBooks = new GetBooks(this, expandableListView );
        getBooks.start();

        /*

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
        */
    }

    public void getListBook( List<Book> listBooks ) {
        bookController = new BookController( listBooks );

        progressBar.setVisibility( View.INVISIBLE );

        adapter = new ExpandableListAdapter(this, bookController);
        expandableListView.setAdapter( adapter );
    }

    public void postBook( Book book ) {
        bookController.addBook(book);
        progressBar.setVisibility( View.INVISIBLE );
        adapter.notifyDataSetChanged();
    }

    public void updateBook( Book book ) {
        bookController.updateBook(book);
        progressBar.setVisibility( View.INVISIBLE );
        adapter.notifyDataSetChanged();
    }

    public void deleteBook( int id ) {
        bookController.removeBook(id);
        progressBar.setVisibility( View.INVISIBLE );
        adapter.notifyDataSetChanged();
    }

    public void error() {
        Toast.makeText(this,
                "Error na requisição",
                Toast.LENGTH_SHORT).show();
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
            progressBar.setVisibility( View.VISIBLE );
            String title = (String) data.getExtras().get("title");
            String author = (String) data.getExtras().get("author");
            int value = (int) data.getExtras().get("value");

            Book book = new Book(id_select, title, author, value);;

            if( requestCode == REQUEST_ADD ) {
                postBooks = new PostBooks( this, expandableListView, book );
                postBooks.start();
            } else if( requestCode == REQUEST_EDIT ) {
                updateBooks = new UpdateBooks( this, expandableListView, book );
                updateBooks.start();
            }

        } else if( resultCode == ManipulateBooksActivity.RESULT_CANCEL ) {
            Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
        }

        editText_id.setText("");
        id_select = -1;
    }

    public void onClick_Add(View view) {
        add();
    }

    public void onClick_Edit(View view) {
        edit();
    }


    private void add() {
        Intent intent = new Intent(this, ManipulateBooksActivity.class);
        startActivityForResult(intent, REQUEST_ADD);
    }

    private void edit() {
        Intent intent = new Intent(this, ManipulateBooksActivity.class);

        String idStr = editText_id.getText().toString();

        if(idStr != "" && !idStr.trim().isEmpty() && !idStr.matches("^[0-9]*$")){
            Toast.makeText(this,
                    "Digite um id valido",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        id_select = Integer.parseInt(idStr.trim());

        Book book = bookController.getBook(id_select);

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
        String idStr = editText_id.getText().toString();

        if(idStr != "" && !idStr.trim().isEmpty() && !idStr.matches("^[0-9]*$")){
            Toast.makeText(this,
                    "Digite um id valido",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility( View.VISIBLE );

        id_select = Integer.parseInt(idStr);

        DeleteBook deleteBook = new DeleteBook( this, expandableListView, id_select );
        deleteBook.start();

        editText_id.setText("");
        id_select = -1;
    }
}