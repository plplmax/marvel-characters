package com.github.plplmax.marvelcharacters.ui.core;

import android.content.Context;

import androidx.annotation.StringRes;

public interface ResourceProvider {
    String getString(@StringRes int id);

    class Base implements ResourceProvider {
        private final Context context;

        public Base(Context context) {
            this.context = context;
        }

        @Override
        public String getString(@StringRes int id) {
            return context.getString(id);
        }
    }
}
