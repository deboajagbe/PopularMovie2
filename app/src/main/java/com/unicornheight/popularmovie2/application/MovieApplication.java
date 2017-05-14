package com.unicornheight.popularmovie2.application;

import android.app.Application;

import com.unicornheight.popularmovie2.di.components.ApplicationComponent;
import com.unicornheight.popularmovie2.di.components.DaggerApplicationComponent;
import com.unicornheight.popularmovie2.di.modules.ApplicationModule;

/**
 * Created by deboajagbe on 5/10/17.
 */

public class MovieApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponent();
    }

    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this, "http://api.themoviedb.org/3/"))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}