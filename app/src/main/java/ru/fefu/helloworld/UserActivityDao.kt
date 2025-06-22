package ru.fefu.helloworld

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserActivityDao {
    @Insert
    suspend fun insert(activity: UserActivity)

    @Query("SELECT * FROM activities ORDER BY startTime DESC")
    fun getAllActivities(): LiveData<List<UserActivity>>

    @Query("DELETE FROM activities WHERE id = :id")
    fun delete(id: Int)
}