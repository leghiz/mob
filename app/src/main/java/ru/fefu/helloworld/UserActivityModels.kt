package ru.fefu.helloworld

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

enum class ActivityTypeName {
    Шаг,
    Бег,
    Велосипед,
}

data class Coordinate(
    val latitude: Double,
    val longitude: Double
)

@Entity(tableName = "activities")
data class UserActivity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val activityType: ActivityTypeName,
    val startTime: DateTime,
    val endTime: DateTime,
    val coordinates: List<Coordinate>
)