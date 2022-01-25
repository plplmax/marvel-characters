package com.github.plplmax.mrv.ui.characters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.github.plplmax.mrv.R;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.ui.core.State;

public class CharactersAdapter extends ListAdapter<Character, RecyclerView.ViewHolder> {
    private static final byte DATA_VIEW_TYPE = 1;
    private static final byte FOOTER_VIEW_TYPE = 2;

    private static final DiffUtil.ItemCallback<Character> DIFF_CALLBACK;

    static {
        DIFF_CALLBACK = new DiffUtil.ItemCallback<Character>() {
            @Override
            public boolean areItemsTheSame(@NonNull Character oldItem,
                                           @NonNull Character newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Character oldItem,
                                              @NonNull Character newItem) {
                return oldItem.equals(newItem);
            }
        };
    }

    private final CharactersFragment.OnCharacterClickListener onClickListener;
    private final LayoutInflater inflater;

    private State state = State.LOADING;

    public CharactersAdapter(Context context,
                             CharactersFragment.OnCharacterClickListener onClickListener) {
        super(DIFF_CALLBACK);

        this.onClickListener = onClickListener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == DATA_VIEW_TYPE) {
            View view = inflater.inflate(R.layout.character_item, parent, false);
            return new CharacterViewHolder(view);
        }

        View view = inflater.inflate(R.layout.loading_item, parent, false);
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            CharacterViewHolder viewHolder = (CharacterViewHolder) holder;
            viewHolder.bind(position);
        } else {
            FooterViewHolder holderFooter = (FooterViewHolder) holder;
            holderFooter.bind(state);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (super.getItemCount() > position) return DATA_VIEW_TYPE;
        else return FOOTER_VIEW_TYPE;
    }

    public int getSpanSize(int position) {
        return getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasFooter() ? 1 : 0);
    }

    private boolean hasFooter() {
        return super.getItemCount() != 0 && (state == State.LOADING);
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder {
        final ImageView characterView;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            characterView = itemView.findViewById(R.id.character_image);
        }

        public void bind(int position) {
            Character character = getCurrentList().get(position);
            String url = character.getThumbnail().toString();

            Glide.with(characterView.getContext())
                    .load(url)
                    .centerInside()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(characterView);

            characterView.setOnClickListener(v -> onClickListener.onCharacterClick(character));
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        final ProgressBar progressBar;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }

        public void bind(State state) {
            progressBar.setVisibility(state == State.LOADING ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setState(State state) {
        this.state = state;
        notifyItemChanged(super.getItemCount());
    }
}
