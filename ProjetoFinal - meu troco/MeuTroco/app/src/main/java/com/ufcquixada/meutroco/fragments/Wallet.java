package com.ufcquixada.meutroco.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.Views.ExtractActivity;
import com.ufcquixada.meutroco.Views.Payment;
import com.ufcquixada.meutroco.models.LoggedUser;
import com.ufcquixada.meutroco.serverCommunication.GetUser;

public class Wallet extends Fragment {

    GetUser getUser;
    TextView balance;
    LoggedUser loggedUser;

    LinearLayout payment;
    LinearLayout transfer;
    LinearLayout extract;

    public Wallet() {
        // Required empty public constructor
    }

    public static Wallet newInstance() {
        return new Wallet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        balance = view.findViewById(R.id.textViewBalance);

        getUser = new GetUser(this, balance);
        getUser.start();
        balance.setText("R$ " + loggedUser.getInstance().getUser().getBalance());

        payment = view.findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Payment.class);
                startActivityForResult(intent, 1111);
            }
        });

        transfer = view.findViewById(R.id.transfer);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Go to Screen Transfer", Toast.LENGTH_LONG).show();
            }
        });

        extract = view.findViewById(R.id.extract);
        extract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExtractActivity.class);
                startActivityForResult(intent, 3333);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getUser = new GetUser(this, balance);
        getUser.start();
    }

    public void loadUser() {
        balance.setText("R$ " + loggedUser.getInstance().getUser().getBalance());
    }
}