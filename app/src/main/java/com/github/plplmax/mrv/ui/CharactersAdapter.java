package com.github.plplmax.mrv.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.github.plplmax.mrv.R;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.Image;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {
    private final CharactersFragment.OnCharacterClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<Character> characters;

    public CharactersAdapter(Context context,
                             CharactersFragment.OnCharacterClickListener onClickListener,
                             List<Character> characters) {
        this.onClickListener = onClickListener;
        this.characters = characters;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CharactersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.character_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull CharactersAdapter.ViewHolder holder, int position) {
        Character character = characters.get(position);
        Image image = character.getThumbnail();
        String url = image.toString();

        RequestBuilder<Drawable> requestBuilder = Glide.with(holder.characterView.getContext())
                .load(url);

        if (image.isAvailable()) requestBuilder.centerCrop();

        requestBuilder.placeholder(R.mipmap.ic_launcher)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.characterView);
        holder.characterView.setOnClickListener(v -> {
            onClickListener.onCharacterClick(character);
        });
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView characterView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            characterView = itemView.findViewById(R.id.character_image);
        }
    }
}
