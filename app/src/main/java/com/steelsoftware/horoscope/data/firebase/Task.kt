package com.steelsoftware.horoscope.data.firebase

import java.util.Date

/**
 * Created by Anatoliy Lukyanov on 05/03/2019.
 *
 */
data class Task(
        var id: String = "",
        var content: String = "",
        var title: String = "",
        var created: Date = Date()
)