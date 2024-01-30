package com.intersoft.event


import com.intersoft.ui.IIterableObject
import com.intersoft.utils.DateTimeManager

data class EventModel(
    var name: String,
    var description: String,
    var dateInMillis: Long,
    var durationInMillis: Long,
    var startTimeInMillis: Long,
    var maxParticipants: Int,
    var location: String,
    val locationName : String,
    var ownerId: Int,
    var id: Int = 0,
    var participants: List<UserModel>? = null
): IIterableObject {
    override fun getIdentifier(): Int {
        return id
    }

    override fun getMainText(): String {
        return name
    }

    override fun getSecondaryText(): String {
        return DateTimeManager.formatMillisDateToString(startTimeInMillis)
    }

    fun processIntoIInterableObject(): IIterableObject {
        return object : IIterableObject {
            override fun getIdentifier(): Int {
                return id
            }

            override fun getMainText(): String {
                return name
            }

            override fun getSecondaryText(): String {
                return DateTimeManager.formatMillisDateToString(startTimeInMillis)
            }
        }
    }
}

