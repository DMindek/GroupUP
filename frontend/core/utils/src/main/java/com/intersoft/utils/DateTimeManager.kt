package com.intersoft.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DateTimeManager {

    fun formatMilisDatetoString(dateInMilis: Long): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return sdf.format(Date(dateInMilis))
    }
}