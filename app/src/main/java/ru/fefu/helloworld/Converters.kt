package ru.fefu.helloworld

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.joda.time.DateTime

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): DateTime? {
        return value?.let { DateTime(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: DateTime?): Long? {
        return date?.millis
    }

    @TypeConverter
    fun fromCoordinates(coordinates: List<Coordinate>?): String? {
        return coordinates?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toCoordinates(data: String?): List<Coordinate>? {
        return data?.let {
            val type = object : TypeToken<List<Coordinate>>() {}.type
            Gson().fromJson(it, type)
        }
    }
}