package com.ufcquixada.meutroco.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.fragments.EditInforUser;
import com.ufcquixada.meutroco.fragments.EditPasswordUser;

public class EditInfor extends AppCompatActivity {
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_infor);

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 3);
        typeFragment();
    }

    private void typeFragment() {
        switch (type) {
            case 0:
                EditInforUser editInforUser = new EditInforUser();
                managerFragment(editInforUser, "EditInforUser");
                setResult(EditInforUser.RESPONSE_OK);
                break;
            case 1:
                EditPasswordUser editPasswordUser = new EditPasswordUser();
                managerFragment(editPasswordUser, "EditPasswordUser");
                setResult(EditInforUser.RESPONSE_OK);
                break;
            default:
                Toast.makeText(this, "Error type", Toast.LENGTH_LONG).show();
        }
    }

    private void managerFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.editInfor, fragment, tag);
        fragmentTransaction.commit();
    }

    public void onClick_Back(View view) {
        setResult(EditInforUser.RESPONSE_NOT);
        finish();
    }
}