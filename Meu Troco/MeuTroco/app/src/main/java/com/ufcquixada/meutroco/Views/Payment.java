package com.ufcquixada.meutroco.Views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.component.TransferAdpter;
import com.ufcquixada.meutroco.models.Transfer;
import com.ufcquixada.meutroco.serverCommunication.GetTransferUser;
import com.ufcquixada.meutroco.serverCommunication.PostTransferUser;

public class Payment extends AppCompatActivity {

    PostTransferUser postTransferUser;
    GetTransferUser getTransferUser;
    Transfer[] transfers;
    TransferAdpter adapter;
    LinearLayout pay;
    LinearLayout receive;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        pay = findViewById(R.id.pay);
        receive = findViewById(R.id.receive);
        listView = findViewById(R.id.listTransfer);
        getTransferUser = new GetTransferUser(this, listView);
        getTransferUser.start();
    }

    public void onClick_Back(View view) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onClick_Pay(View view) {
        Intent intent = new Intent(this, CameraTestActivity.class);
        startActivityForResult(intent, 1111);
    }

    public void onClick_Receive(View view) {
        Intent intent = new Intent(this, ReceiveActivity.class);
        startActivityForResult(intent, 1111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(CameraTestActivity.RESULT_OK == resultCode) {
            String qrCode = (String) data.getExtras().get("qrCode");
            postTransferUser = new PostTransferUser(this, qrCode);
            postTransferUser.start();
        }
    }

    public void finishTransfer(Transfer transfer) {
        Toast.makeText(this, transfer.toString(), Toast.LENGTH_LONG);
        getTransferUser = new GetTransferUser(this, listView);
        getTransferUser.start();
    }

    public void loadTransfer(Transfer[] transfers) {
        this.transfers = transfers;
        adapter = new TransferAdpter(this, transfers);
        listView.setAdapter(adapter);
    }
}