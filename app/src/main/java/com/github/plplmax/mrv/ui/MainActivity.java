package com.github.plplmax.mrv.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.github.plplmax.mrv.Application;
import com.github.plplmax.mrv.R;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject
    MainViewModelFactory factory;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Application) getApplicationContext()).appComponent.inject(this);
        viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
        openCharactersFragment();
    }

    private void openCharactersFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, CharactersFragment.class, null)
                .commit();
    }
}