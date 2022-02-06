package com.ufcquixada.meutroco.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.models.LoggedUser;
import com.ufcquixada.meutroco.models.User;
import com.ufcquixada.meutroco.serverCommunication.PutUserInfor;

public class EditInforUser extends Fragment implements View.OnClickListener {

    public static int RESPONSE_OK = 1;
    public static int RESPONSE_NOT = 0;
    PutUserInfor putUserInfor;
    LoggedUser loggedUser;
    TextView nameUser;
    TextView emailUser;
    Button btnSave;

    public EditInforUser() {
        // Required empty public constructor
    }

    public static EditInforUser newInstance() {
        return new EditInforUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inforuser, container, false);

        User user = loggedUser.getInstance().getUser();

        nameUser = view.findViewById(R.id.editTextTextName);
        nameUser.setText(user.getName());

        emailUser = view.findViewById(R.id.editTextTextEmail);
        emailUser.setText(user.getEmail());

        btnSave = view.findViewById(R.id.button_save);
        btnSave.setOnClickListener(this);

        return view;
    }

    public void finishSave(User user) {
        loggedUser.getInstance().setUpdateUser(user);
        getActivity().finish();
    }

    @Override
    public void onClick(View view) {
        String name = nameUser.getText().toString();
        String email = emailUser.getText().toString();

        putUserInfor = new PutUserInfor(this, name, email, loggedUser.getInstance().getToken());
        putUserInfor.start();
    }
}