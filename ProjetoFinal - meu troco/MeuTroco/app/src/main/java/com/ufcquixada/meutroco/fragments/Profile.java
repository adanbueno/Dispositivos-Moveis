package com.ufcquixada.meutroco.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ufcquixada.meutroco.MainActivity;
import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.Views.EditInfor;
import com.ufcquixada.meutroco.models.LoggedUser;
import com.ufcquixada.meutroco.models.User;

public class Profile extends Fragment {

    LoggedUser loggedUser;
    TextView nameUser;
    TextView emailUser;
    TextView cpfUser;
    Button buttonEditInfo;
    Button changePassword;

    public Profile() {
        // Required empty public constructor
    }

    public static Profile newInstance() {
        return new Profile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        User user = loggedUser.getInstance().getUser();

        nameUser = view.findViewById(R.id.textViewNameUser);
        nameUser.setText(user.getName());

        emailUser = view.findViewById(R.id.textViewEmail);
        emailUser.setText(user.getEmail());

        cpfUser = view.findViewById(R.id.textViewCpf);
        cpfUser.setText(user.getCpf());

        buttonEditInfo = view.findViewById(R.id.btnEditInfo);
        buttonEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditInfor.class);
                intent.putExtra("type", 0);
                startActivityForResult(intent, 0);;
            }
        });

        changePassword = view.findViewById(R.id.btnChangePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditInfor.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1);;
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode==0) && (resultCode == EditInforUser.RESPONSE_OK)){
            User user = loggedUser.getInstance().getUser();
            nameUser.setText(user.getName());
            emailUser.setText(user.getEmail());
        }
    }
}