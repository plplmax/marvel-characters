package com.github.plplmax.marvelcharacters;

import com.github.plplmax.marvelcharacters.di.AppComponent;
import com.github.plplmax.marvelcharacters.di.AppModule;
import com.github.plplmax.marvelcharacters.di.DaggerAppComponent;

public class Application extends android.app.Application {
    public AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }
}
