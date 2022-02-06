package com.ufcquixada.meutroco.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.component.TransferAdpter;
import com.ufcquixada.meutroco.models.Transfer;
import com.ufcquixada.meutroco.serverCommunication.GetExtract;

public class ExtractActivity extends AppCompatActivity {

    GetExtract getExtract;
    Transfer[] transfers;
    TransferAdpter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract);

        listView = findViewById(R.id.listTransfer);

        getExtract = new GetExtract(this, listView);
        getExtract.start();

    }

    public void onClick_Back(View view) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void loadTransfer(Transfer[] transfers) {
        this.transfers = transfers;
        adapter = new TransferAdpter(this, transfers);
        listView.setAdapter(adapter);
    }
}