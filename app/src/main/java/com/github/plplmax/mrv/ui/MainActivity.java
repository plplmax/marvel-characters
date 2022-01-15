package com.github.plplmax.mrv.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.github.plplmax.mrv.Application;
import com.github.plplmax.mrv.R;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.ui.detailscreen.CharacterDetailFragment;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity
        implements CharactersFragment.OnCharacterClickListener {
    @Inject
    MainViewModelFactory factory;
    private MainViewModel viewModel;

    @Override
    public void onCharacterClick(Character character) {
        openCharacterDetailFragment(character);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Application) getApplicationContext()).appComponent.inject(this);
        viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
        if (savedInstanceState == null) openCharactersFragment();
    }

    private void openCharactersFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, CharactersFragment.class, null)
                .commit();
    }

    private void openCharacterDetailFragment(Character character) {
        CharacterDetailFragment fragment = CharacterDetailFragment.newInstance(character);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right, R.animator.slide_out_right, R.animator.slide_out_left)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}