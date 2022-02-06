package br.ufc.quixada.dadm.entreg02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static int REQUEST_ADD = 1;
    public static int REQUEST_EDIT = 2;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView myList;
    ArrayList<Livro> listLivros;
    ArrayAdapter adapter;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listLivros = new ArrayList<Livro>();
        myList = findViewById(R.id.myList);

        iniciarFirebase();
        eventoDatabase();
    }

    public void iniciarFirebase(){
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    public void eventoDatabase(){
        databaseReference.child("Livro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listLivros.clear();
                for(DataSnapshot objSanpshot : dataSnapshot.getChildren()){
                    Livro l = objSanpshot.getValue(Livro.class);
                    l.atualizarContador();
                    listLivros.add(l);
                }

                adapter = new ArrayAdapter<Livro>(MainActivity.this, android.R.layout.simple_list_item_1, listLivros);
                myList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addLivros(View view){
        Intent i = new Intent(MainActivity.this, Adicionar.class);
        startActivityForResult(i, REQUEST_ADD);

    }

    public void editarLivros(View view){
        //Livro livro = listLivros.get(0);

        Intent i = new Intent(MainActivity.this, Adicionar.class);

        EditText idUsuario = findViewById(R.id.editarPorId);
        id = Integer.parseInt(idUsuario.getText().toString());

        Livro livro = listLivros.get(id);

        i.putExtra("idLivro", livro.getId());
        i.putExtra("nome", livro.getNome());
        i.putExtra("genero", livro.getGenero());

        startActivityForResult(i, REQUEST_EDIT);
    }

    public void removerLivro(View view){
        EditText idUsuario = findViewById(R.id.remover);
        id = Integer.parseInt(idUsuario.getText().toString());

        Livro livro = listLivros.get(id);

        databaseReference.child("Livro").child(String.valueOf(livro.getId())).removeValue();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_ADD && resultCode == Adicionar.RESULT_ADD){
            String nome = (String) data.getExtras().get("nome");
            String genero = (String) data.getExtras().get("genero");
            Livro livro = new Livro(nome, genero);

            databaseReference.child("Livro").child(String.valueOf(livro.getId())).setValue(livro);

            listLivros.add(livro);
            adapter.notifyDataSetChanged();

        } else if (requestCode == REQUEST_EDIT && resultCode == Adicionar.RESULT_ADD){
            String nome = (String) data.getExtras().get("nome");
            String genero = (String) data.getExtras().get("genero");

            listLivros.get(id).setNome(nome);
            listLivros.get(id).setGenero(genero);
            listLivros.get(id).setId(id);

            Livro livro = listLivros.get(id);

            databaseReference.child("Livro").child(String.valueOf(livro.getId())).setValue(livro);

            adapter.notifyDataSetChanged();

        } else if(resultCode == Adicionar.RESULT_CANCEL){
            Toast.makeText(this, "Cancelado",
                    Toast.LENGTH_SHORT).show();
        }
    }
}