package com.ufcquixada.meutroco.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ufcquixada.meutroco.MainActivity;
import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.models.LoggedUser;

public class Settings extends Fragment {

    LoggedUser loggedUser;
    TextView nameUser;

    public Settings() {
        // Required empty public constructor
    }

    public static Settings newInstance() {
        return new Settings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        nameUser = view.findViewById(R.id.textViewNameUser);
        nameUser.setText(loggedUser.getInstance().getUser().getName());


        LinearLayout shareFriend = view.findViewById(R.id.shareFriend);
        shareFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Go to Screen Share Friend", Toast.LENGTH_LONG).show();
            }
        });


        LinearLayout rate = view.findViewById(R.id.rate);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Go to Screen Support Rate", Toast.LENGTH_LONG).show();
            }
        });

        LinearLayout support = view.findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Go to Screen Support", Toast.LENGTH_LONG).show();
            }
        });

        LinearLayout aboutApp = view.findViewById(R.id.aboutApp);
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Go to Screen About APP", Toast.LENGTH_LONG).show();
            }
        });


        LinearLayout logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                loggedUser.getInstance().setLogout();
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}