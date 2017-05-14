package com.unicornheight.popularmovie2.di.components;

import android.content.Context;

import com.unicornheight.popularmovie2.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by deboajagbe on 5/10/17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Retrofit exposeRetrofit();

    Context exposeContext();
}
