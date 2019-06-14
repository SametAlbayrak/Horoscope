package com.steelsoftware.horoscope.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.steelsoftware.horoscope.ui.main.categories.CategoriesFragment
import com.steelsoftware.horoscope.ui.main.home.HomeFragment
import com.steelsoftware.horoscope.ui.main.search.SearchFragment
import com.steelsoftware.horoscope.ui.main.settings.SettingsFragment


class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> CategoriesFragment.newInstance()
            1 -> HomeFragment.newInstance()
            2 -> SearchFragment.newInstance()
            3 -> SettingsFragment.newInstance()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }


}