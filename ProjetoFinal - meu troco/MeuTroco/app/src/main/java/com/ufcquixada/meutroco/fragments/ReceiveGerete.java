package com.ufcquixada.meutroco.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.models.LoggedUser;

public class ReceiveGerete extends Fragment {

    LoggedUser loggedUser;
    EditText edtValue;
    Button buttonCofirm;


    public ReceiveGerete() {
        // Required empty public constructor
    }

    public static ReceiveGerete newInstance() {
        return new ReceiveGerete();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receivegerete, container, false);

        edtValue = view.findViewById(R.id.editTextNumberDecimal);

        buttonCofirm = view.findViewById(R.id.button_confirm);

        buttonCofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = Double.valueOf(edtValue.getText().toString());
                Toast.makeText(getActivity(), "Valor: " + value, Toast.LENGTH_LONG).show();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerReceive, ReceiveQrCode.newInstance(value)).commit();
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode==0) && (resultCode == EditInforUser.RESPONSE_OK)){

        }
    }

}
