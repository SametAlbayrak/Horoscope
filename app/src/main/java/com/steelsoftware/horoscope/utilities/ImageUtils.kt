package com.steelsoftware.horoscope.utilities

import android.content.Context
import com.steelsoftware.horoscope.R
import java.util.ArrayList


private var menuColors = ArrayList<Int>()

fun getMenuIndexColor(context: Context, position: Int): Int {
    if (menuColors.isEmpty()) {
        val colors = context.resources.getIntArray(R.array.color_pallette)
        for (color in colors) {
            menuColors.add(color)
        }
    }
    return menuColors.get(position % menuColors.size)
}