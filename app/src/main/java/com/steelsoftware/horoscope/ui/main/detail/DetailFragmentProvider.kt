package com.steelsoftware.horoscope.ui.main.detail

import androidx.lifecycle.ViewModel
import com.steelsoftware.horoscope.utilities.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class DetailFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun provideDetailFragment(): DetailFragment


    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel

}