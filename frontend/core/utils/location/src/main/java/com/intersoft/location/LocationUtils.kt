package com.intersoft.location

object LocationUtils {
    fun coordinatesFromString(text: String): Pair<Double,Double>{
        val first: Double = text.substringBefore(',').toDouble()
        val second: Double = text.substringAfter(',').toDouble()

        return Pair(first, second)
    }
}