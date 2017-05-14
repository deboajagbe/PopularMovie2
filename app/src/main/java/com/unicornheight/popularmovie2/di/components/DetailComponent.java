package com.unicornheight.popularmovie2.di.components;

import com.unicornheight.popularmovie2.di.modules.DetailModule;
import com.unicornheight.popularmovie2.di.scope.PerActivity;
import com.unicornheight.popularmovie2.modules.details.DetailActivity;

import dagger.Component;

/**
 * Created by deboajagbe on 5/13/17.
 */

@PerActivity
@Component(modules = DetailModule.class, dependencies = ApplicationComponent.class)

public interface DetailComponent {
    void inject(DetailActivity activity);
}
