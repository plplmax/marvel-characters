package com.github.plplmax.mrv;

import com.github.plplmax.mrv.di.AppComponent;
import com.github.plplmax.mrv.di.AppModule;
import com.github.plplmax.mrv.di.DaggerAppComponent;

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
