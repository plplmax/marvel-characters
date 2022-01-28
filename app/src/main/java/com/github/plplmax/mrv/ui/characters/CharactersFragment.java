package com.github.plplmax.mrv.ui.characters;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy;

import com.github.plplmax.mrv.Application;
import com.github.plplmax.mrv.R;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.ui.core.State;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

public class CharactersFragment extends Fragment {
    @Inject
    CharactersViewModelFactory factory;

    @Inject
    Context applicationContext;

    private static final String PREVIOUS_ITEM_COUNT_KEY = "previous_item_count_key";

    private CharactersViewModel viewModel;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private CharactersAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    private OnCharacterClickListener characterClickListener;

    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface OnCharacterClickListener {
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

        Log.e("TEST", "onCreate: ");

        ((Application) requireActivity().getApplicationContext()).appComponent
                .inject(this);

        viewModel = new ViewModelProvider(requireActivity(), factory).get(CharactersViewModel.class);

        if (savedInstanceState != null) {
            Log.e("TEST", "key is exist");
            int previousItemCount = savedInstanceState.getInt(PREVIOUS_ITEM_COUNT_KEY);
            viewModel.lastCharactersLoadedCount = previousItemCount;
            Log.e("TEST", "onViewCreated: findLastVisible = " + previousItemCount);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("TEST", "onCreateView invoked");
        return inflater.inflate(R.layout.fragment_characters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (viewModel.areCharactersEmpty()) {
            if (viewModel.lastCharactersLoadedCount != 0)
                viewModel.fetchCharactersWithLimit(viewModel.lastCharactersLoadedCount);
            else viewModel.fetchCharactersWithOffset(0);
        }

        setupViews(view);
        setupRecyclerView();

        initState();
    }

    @Override
    public void onDestroyView() {
        Log.e("TEST", "onDestroyView: ");
        recyclerView = null;
        progressBar = null;
        adapter = null;
        gridLayoutManager = null;

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e("TEST", "onDestroy invoked");
        characterClickListener = null;

        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.e("TEST", "onSaveInstanceState invoked in body with " + viewModel.lastCharactersLoadedCount);
        outState.putInt(PREVIOUS_ITEM_COUNT_KEY, viewModel.lastCharactersLoadedCount);

        super.onSaveInstanceState(outState);
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
                return adapter.getSpanSize(position);
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void setupAdapter() {
        adapter = new CharactersAdapter(requireContext(),
                character -> characterClickListener.onCharacterClick(character));
        adapter.setStateRestorationPolicy(StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        recyclerView.setAdapter(adapter);
    }

    private void setupOnScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("TEST", "onScrolledInvoked: ");
                if (viewModel.isOnScrolledActive() &&
                        !viewModel.areAllCharactersLoaded() &&
                        gridLayoutManager.findLastVisibleItemPosition() >= adapter.getItemCount() - 10) {
                    viewModel.deactivateOnScrolled();
                    handler.post(() -> viewModel.fetchCharactersWithOffset(adapter.getItemCount()));
                    Log.d("TEST", "onScrolled invoked()");
                    Log.d("TEST", "findLastVisibleItemPosition: " + gridLayoutManager.findLastVisibleItemPosition() + " >= getItemCount - 10: " + (adapter.getItemCount() - 10) + "");
                }
            }
        });
    }

    private void initState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (!viewModel.areCharactersEmpty()) {
                adapter.submitList(viewModel.characters);
                adapter.setState(state);
            }

            if (state == State.ERROR) {
                showMessage(viewModel.failMessage);
                viewModel.deactivateOnScrolled();
            }
            if (state == State.DONE) viewModel.activateOnScrolled();

            progressBar.setVisibility(viewModel.areCharactersEmpty()
                    && state == State.LOADING ? View.VISIBLE : View.GONE);

        });
    }

    private void showMessage(String message) {
//        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show();
        Snackbar.make(requireContext(), progressBar, message, BaseTransientBottomBar.LENGTH_INDEFINITE)
        .setAction("Retry", v -> viewModel.activateOnScrolled())
        .show();
    }
}