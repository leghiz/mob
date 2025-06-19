package ru.fefu.helloworld

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ActivityListItem {
    data class DateHeader(val date: String) : ActivityListItem()

    @Parcelize
    data class ActivityItem(
        val id: Int,
        val distance: String,
        val time: String,
        val activityType: String,
        val username: String,
        val timeAgo: String,
        val startTime: String,
        val endTime: String,
        val comment: String? = null
    ) : ActivityListItem(), Parcelable
}