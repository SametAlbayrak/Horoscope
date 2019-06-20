package com.steelsoftware.horoscope.ui.main.settings

import androidx.lifecycle.ViewModel
import com.steelsoftware.horoscope.utilities.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class SettingsFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun provideSettingsFragment(): SettingsFragment


    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

}