package com.intersoft.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


object DateTimeManager {
    fun formatMillisDateToString(dateInMilis: Long): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return sdf.format(Date(dateInMilis))
    }

    fun formatMillisToDateTime(dateInMilis: Long): String{
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

        //we subtract 1 hour in millis "3600000" because it formats the time based on our locale which is +1 offset, and that does not make sense in this context
        return  simpleDateFormat.format(dateInMilis - 3600000)
    }
    fun calculateMillisFromHoursAndMinutes(hours: Int, minutes: Int): Long {
        return  (hours * 3600000).toLong() + (minutes * 60000).toLong()
    }

    fun calculateHoursFromMillis(timeInMillis: Long): Long {
        return TimeUnit.MILLISECONDS.toHours(timeInMillis)
    }
    fun calculateMinutesFromMillis(timeInMillis: Long): Long {
        return TimeUnit.MILLISECONDS.toMinutes(timeInMillis)
    }



    fun datePassesMidnight( selectedStartTimeHours: Int,durationHours: Int): Boolean{
        if(selectedStartTimeHours + durationHours >=24)
            return true
        return false
    }
    fun calculateEndTime(
        durationHours: Int,
        durationMinutes: Int,
        selectedStartTime: MutableList<Int>
    ): String {

        if(!durationIsSet(durationHours,durationMinutes))
            return ""

        if(selectedStartTime.isEmpty())
            return ""

        var currentHoursSum = selectedStartTime[0] + durationHours
        var currentMinutesSum = selectedStartTime[1] + durationMinutes
        var endTimeHours : String
        var endTimeMinutes : String

        if (currentMinutesSum >= 60) {
            currentHoursSum += (currentMinutesSum / 60)
            currentMinutesSum -= (currentMinutesSum / 60) * 60
        }
        if (currentHoursSum >= 24) {
            currentHoursSum -= 24
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

    fun formatStartTime(selectedStartTime: MutableList<Int>): String{
        val currentHours = selectedStartTime[0]
        val currentMinutes = selectedStartTime[1]
        var hours = "$currentHours"
        var minutes = "$currentMinutes"

        if(currentHours < 10){
            hours = "0$currentHours"
        }
        if(currentMinutes < 10){
            minutes = "0$currentMinutes"
        }

        return "${hours}:${minutes}"
    }
    fun startTimeIsSet(selectedStartTimeText: String): Boolean {
        return selectedStartTimeText.isNotBlank()
    }

    fun durationIsSet(hours: Int, minutes: Int): Boolean {
        return (hours >= 0 && minutes >= 0)
    }
}