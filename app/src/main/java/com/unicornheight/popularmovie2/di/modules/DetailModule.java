package com.unicornheight.popularmovie2.di.modules;

import com.unicornheight.popularmovie2.api.MovieApiService;
import com.unicornheight.popularmovie2.di.scope.PerActivity;
import com.unicornheight.popularmovie2.mvp.view.DetailView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by deboajagbe on 5/13/17.
 */

@Module
public class DetailModule {

        private DetailView mView;

        public DetailModule(DetailView view) {
            mView = view;
        }

        @PerActivity
        @Provides
        MovieApiService provideApiService(Retrofit retrofit) {
            return retrofit.create(MovieApiService.class);
        }

        @PerActivity
        @Provides
        DetailView provideView() {
            return mView;
        }
}
