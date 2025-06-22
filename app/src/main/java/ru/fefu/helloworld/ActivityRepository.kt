package ru.fefu.helloworld

import androidx.lifecycle.LiveData

class ActivityRepository(private val userActivityDao: UserActivityDao) {
    val allActivities: LiveData<List<UserActivity>> = userActivityDao.getAllActivities()

    suspend fun insert(activity: UserActivity) {
        userActivityDao.insert(activity)
    }
}