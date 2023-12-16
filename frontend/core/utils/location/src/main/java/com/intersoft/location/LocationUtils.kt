package com.intersoft.location

import java.lang.Exception

object LocationUtils {
    fun coordinatesFromString(text: String): Pair<Double,Double>?{
        return try {
            val first: Double = text.substringBefore(',').toDouble()
            val second: Double = text.substringAfter(',').toDouble()
            Pair(first, second)
        }catch (e: Exception){
            null
        }
    }
}