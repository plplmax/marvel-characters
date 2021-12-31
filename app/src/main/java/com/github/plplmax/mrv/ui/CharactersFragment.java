package com.github.plplmax.mrv.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.github.plplmax.mrv.Application;
import com.github.plplmax.mrv.R;
import com.github.plplmax.mrv.domain.models.Character;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CharactersFragment extends Fragment {
    @Inject
    MainViewModelFactory factory;
    @Inject
    Context applicationContext;
    private MainViewModel viewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CharactersAdapter adapter;
    private OnCharacterClickListener characterClickListener;
    private final List<Character> characters = new ArrayList<>();

    interface OnCharacterClickListener {
        void onCharacterClick(Character character);
    }

    public CharactersFragment() {
        super(R.layout.fragment_characters);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCharacterClickListener) {
            characterClickListener = (OnCharacterClickListener) context;
        } else throw new IllegalArgumentException("OnCharacterClickListener must be implemented");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) requireActivity().getApplicationContext()).appComponent
                .inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity(), factory).get(MainViewModel.class);
        return inflater.inflate(R.layout.fragment_characters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        adapter = new CharactersAdapter(getContext(),
                character -> characterClickListener.onCharacterClick(character),
                characters);
        recyclerView.setAdapter(adapter);

        viewModel.success.observe(getViewLifecycleOwner(), characters -> {
            if (characters.isEmpty()) {
                showMessage("The server returned an empty list");
            } else {
                updateCharacters(characters);
            }
        });
        viewModel.isLoading.observe(getViewLifecycleOwner(), aBoolean -> {
            recyclerView.setVisibility(aBoolean ? View.GONE : View.VISIBLE);
            progressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
        });
        viewModel.fail.observe(getViewLifecycleOwner(), e -> {
            showMessage(e.getMessage());
        });
    }

    private void showMessage(String message) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateCharacters(List<Character> characters) {
        this.characters.clear();
        this.characters.addAll(characters);
        adapter.notifyDataSetChanged();
    }
}