package com.unicornheight.popularmovie2.di.components;

import com.unicornheight.popularmovie2.di.modules.MoviesModule;
import com.unicornheight.popularmovie2.di.scope.PerActivity;
import com.unicornheight.popularmovie2.modules.home.MainActivity;

import dagger.Component;

/**
 * Created by deboajagbe on 5/10/17.
 */

@PerActivity
@Component(modules = MoviesModule.class, dependencies = ApplicationComponent.class)

public interface MovieComponent {
    void inject(MainActivity activity);
}
