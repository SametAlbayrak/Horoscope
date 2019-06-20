package com.steelsoftware.horoscope.di

import androidx.lifecycle.ViewModel
import com.steelsoftware.horoscope.ui.main.MainActivity
import com.steelsoftware.horoscope.ui.main.categories.CategoryNewsActivity
import com.steelsoftware.horoscope.ui.main.categories.CategoryViewModel
import com.steelsoftware.horoscope.ui.main.home.HomeFragmentProvider
import com.steelsoftware.horoscope.ui.main.profile.ProfileFragmentProvider
import com.steelsoftware.horoscope.ui.main.search.SearchFragmentProvider
import com.steelsoftware.horoscope.ui.main.settings.SettingsFragmentProvider
import com.steelsoftware.horoscope.utilities.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by ansh on 22/02/18.
 */

@Module
internal abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [
        (HomeFragmentProvider::class),
        (SearchFragmentProvider::class),
        (SettingsFragmentProvider::class)
    ])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindCategoryNewsActivity(): CategoryNewsActivity

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun bindCategoryViewModel(viewModel: CategoryViewModel): ViewModel
}