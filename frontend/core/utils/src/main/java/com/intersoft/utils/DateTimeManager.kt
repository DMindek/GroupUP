package com.intersoft.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DateTimeManager {
    fun formatMilisDatetoString(dateInMilis: Long): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return sdf.format(Date(dateInMilis))
    }

    fun calculateEndTime(
        durationHours: Int,
        durationMinutes: Int,
        selectedStartTime: MutableList<Int>
    ): String {
        var currentHoursSum = selectedStartTime[0] + durationHours
        var currentMinutesSum = selectedStartTime[1] + durationMinutes
        var endTimeHours : String
        var endTimeMinutes : String

        if (currentMinutesSum >= 60) {
            currentHoursSum += (currentMinutesSum / 60)
            currentMinutesSum -= (currentMinutesSum / 60) * 60
        }
        if (currentHoursSum >= 24) {
            currentHoursSum = 0
            currentMinutesSum = 0
        }

        endTimeHours = currentHoursSum.toString()
        endTimeMinutes = currentMinutesSum.toString()

        if (currentHoursSum < 10) {
            endTimeHours = "0$currentHoursSum"
        }
        if (currentMinutesSum < 10) {
            endTimeMinutes = "0$currentMinutesSum"
        }

        return "${endTimeHours}:${endTimeMinutes}"
    }

    fun startTimeIsSet(selectedStartTimeText: String): Boolean {
        return selectedStartTimeText.isNotBlank()
    }

    fun durationIsSet(hours: Int, minutes: Int): Boolean {
        return (hours >= 0 && minutes >= 0)
    }
}