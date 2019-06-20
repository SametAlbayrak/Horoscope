package com.steelsoftware.horoscope.ui.main.profile

import androidx.lifecycle.ViewModel
import com.steelsoftware.horoscope.utilities.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class ProfileFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun provideProfileFragment(): ProfileFragment


    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

}