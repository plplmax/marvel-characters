package com.github.plplmax.mrv.ui.detailscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.github.plplmax.mrv.R;
import com.github.plplmax.mrv.domain.models.Character;

public class CharacterDetailFragment extends Fragment {
    private static final String SELECTED_CHARACTER = "selectedCharacter";
    private CharacterDetailViewModel viewModel;
    private ImageView characterImage;
    private TextView characterTitle;
    private TextView characterDescription;

    public CharacterDetailFragment() {
        // Required empty public constructor
    }

    @NonNull
    public static CharacterDetailFragment newInstance(Character selectedCharacter) {
        CharacterDetailFragment fragment = new CharacterDetailFragment();
        Bundle args = new Bundle();

        args.putSerializable(SELECTED_CHARACTER, selectedCharacter);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Character selectedCharacter =
                    (Character) getArguments().getSerializable(SELECTED_CHARACTER);
            CharacterDetailViewModelFactory factory =
                    new CharacterDetailViewModelFactory(selectedCharacter);
            viewModel = new ViewModelProvider(this, factory).get(CharacterDetailViewModel.class);
        } else throw new IllegalArgumentException("Bundle must provide a Character");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_character_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        characterImage = view.findViewById(R.id.character_image);
        characterTitle = view.findViewById(R.id.character_title);
        characterDescription = view.findViewById(R.id.character_description);

        viewModel.character.observe(getViewLifecycleOwner(), character -> {
            String url = character.getThumbnail().toString();

            Glide.with(this)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(characterImage);

            characterTitle.setText(character.getName());
            characterDescription.setText(character.getDescription());
        });
    }
}