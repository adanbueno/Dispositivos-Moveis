package com.ufcquixada.meutroco.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.fragments.Offers;
import com.ufcquixada.meutroco.fragments.Profile;
import com.ufcquixada.meutroco.fragments.Settings;
import com.ufcquixada.meutroco.fragments.Wallet;
import com.ufcquixada.meutroco.models.User;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.wallet);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.offers: {
                Fragment offersFragment = Offers.newInstance();
                openFragment(offersFragment);
                return true;
            }
            case R.id.wallet: {
                Fragment walletFragment = Wallet.newInstance();
                openFragment(walletFragment);
                return true;
            }
            case R.id.settings: {
                Fragment settingsFragment = Settings.newInstance();
                openFragment(settingsFragment);
                return true;
            }
            case R.id.profile: {
                Fragment profileFragment = Profile.newInstance();
                openFragment(profileFragment);
                return true;
            }
        }

        return false;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}