package com.github.plplmax.marvelcharacters.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.github.plplmax.marvelcharacters.Application;
import com.github.plplmax.marvelcharacters.R;
import com.github.plplmax.marvelcharacters.domain.models.Character;
import com.github.plplmax.marvelcharacters.ui.characters.CharactersFragment;
import com.github.plplmax.marvelcharacters.ui.characters.CharactersViewModel;
import com.github.plplmax.marvelcharacters.ui.characters.CharactersViewModelFactory;
import com.github.plplmax.marvelcharacters.ui.detail.CharacterDetailFragment;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity
        implements CharactersFragment.OnCharacterClickListener {
    @Inject
    CharactersViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Application) getApplicationContext()).appComponent.inject(this);
        new ViewModelProvider(this, factory).get(CharactersViewModel.class);

        if (savedInstanceState == null) openCharactersFragment();
    }

    @Override
    public void onCharacterClick(Character character) {
        openCharacterDetailFragment(character);
    }

    private void openCharactersFragment() {
        CharactersFragment fragment = new CharactersFragment();
        openFragment(fragment, false);
    }

    private void openCharacterDetailFragment(Character character) {
        CharacterDetailFragment fragment = CharacterDetailFragment.newInstance(character);
        openFragment(fragment, true);
    }

    private void openFragment(Fragment fragment, boolean needAddToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left,
                        R.animator.slide_in_right,
                        R.animator.slide_out_right,
                        R.animator.slide_out_left)
                .replace(R.id.fragment_container, fragment);

        if (needAddToBackStack) transaction.addToBackStack(null);

        transaction.commit();
    }
}