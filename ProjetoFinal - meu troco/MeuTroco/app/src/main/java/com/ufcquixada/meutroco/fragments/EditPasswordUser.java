package com.ufcquixada.meutroco.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.models.LoggedUser;
import com.ufcquixada.meutroco.models.User;
import com.ufcquixada.meutroco.serverCommunication.PutUserInfor;
import com.ufcquixada.meutroco.serverCommunication.PutUserPassword;

public class EditPasswordUser extends Fragment implements View.OnClickListener {

    public static int RESPONSE_OK = 1;
    public static int RESPONSE_NOT = 0;
    PutUserPassword putUserInfor;
    LoggedUser loggedUser;
    TextView textoldPassword;
    TextView textnewPassword;
    TextView textconfirmPassword;
    Button btnSave;

    public EditPasswordUser() {
        // Required empty public constructor
    }

    public static EditPasswordUser newInstance() {
        return new EditPasswordUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editpassword, container, false);

        textoldPassword = view.findViewById(R.id.editTextTextOldPassword);
        textnewPassword = view.findViewById(R.id.editTextTextNewPassword);
        textconfirmPassword = view.findViewById(R.id.editTextTextConfirmPassword);

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
        String oldPassword = textoldPassword.getText().toString();
        String newPassword = textnewPassword.getText().toString();
        String confirmPassword = textconfirmPassword.getText().toString();

        putUserInfor = new PutUserPassword(this, oldPassword, newPassword, confirmPassword, loggedUser.getInstance().getToken());
        putUserInfor.start();
    }
}