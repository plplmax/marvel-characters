package com.github.plplmax.mrv.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.plplmax.mrv.Application;
import com.github.plplmax.mrv.R;
import com.github.plplmax.mrv.domain.models.Character;

import java.util.List;

import javax.inject.Inject;

public class CharactersFragment extends Fragment {
    @Inject
    MainViewModelFactory factory;

    @Inject
    Context applicationContext;

    private static final byte SPAN_SIZE_DEFAULT = 1;
    private static final byte SPAN_SIZE_FOOTER = 2;

    private MainViewModel viewModel;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private CharactersAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    private OnCharacterClickListener characterClickListener;

    private final Handler handler = new Handler(Looper.getMainLooper());

    interface OnCharacterClickListener {
        void onCharacterClick(Character character);
    }

    public CharactersFragment() {
        // Required empty public constructor
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
        setupViews(view);
        setupRecyclerView();

        observeSuccess();
        observeLoading();
        observeFail();
    }

    @Override
    public void onDestroy() {
        characterClickListener = null;

        super.onDestroy();
    }

    private void setupViews(@NonNull View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    private void setupRecyclerView() {
        setupLayoutManager();
        setupAdapter();
        setupOnScrollListener();
    }

    private void setupLayoutManager() {
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position >= adapter.getItemCount() ? SPAN_SIZE_FOOTER : SPAN_SIZE_DEFAULT;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void setupAdapter() {
        adapter = new CharactersAdapter(getContext(),
                character -> characterClickListener.onCharacterClick(character));
        recyclerView.setAdapter(adapter);
    }

    private void setupOnScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!adapter.isStateLoading() &&
                        !viewModel.areAllCharactersLoaded() &&
                        gridLayoutManager.findLastVisibleItemPosition() >= gridLayoutManager.getItemCount() - 10) {
                    handler.post(() -> loadNextCharacters(gridLayoutManager.getItemCount()));
                }
            }
        });
    }

    private void observeSuccess() {
        viewModel.success.observe(getViewLifecycleOwner(), characters -> {
            adapter.setState(CharactersAdapter.State.DONE);
            updateCharacters(characters);
        });
    }

    private void observeLoading() {
        viewModel.isLoading.observe(getViewLifecycleOwner(),
                aBoolean -> progressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE));
    }

    private void observeFail() {
        viewModel.fail.observe(getViewLifecycleOwner(), e -> {
            showMessage(e.getMessage());
            adapter.setState(CharactersAdapter.State.ERROR);
        });
    }

    private void showMessage(String message) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show();
    }

    private void updateCharacters(List<Character> characters) {
        adapter.submitList(characters);
    }

    private void loadNextCharacters(int offset) {
        adapter.setState(CharactersAdapter.State.LOADING);
        viewModel.fetchCharacters(offset);
    }
}