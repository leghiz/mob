package ru.fefu.helloworld

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.Duration
import ru.fefu.helloworld.databinding.UserOrOtherFragmentBinding
import java.util.Locale

class UserOrOtherFragment : Fragment() {

    private lateinit var binding: UserOrOtherFragmentBinding
    private lateinit var adapter: ActivitiesAdapter
    private var lastActivities: List<UserActivity> = emptyList()
    private var currentTab: Int = R.id.NavAct

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserOrOtherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ActivitiesAdapter(
            items = emptyList(),
            onClick = { activityItem ->
                navigateToDetail(activityItem)
            },
            onLongClick = { activityItem ->
                deleteActivity(activityItem.id)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@UserOrOtherFragment.adapter
        }

        App.database.userActivityDao().getAllActivities().observe(viewLifecycleOwner, Observer { activities ->
            lastActivities = activities
            if (currentTab == R.id.NavAct) {
                updateActivitiesView(activities)
            }
        })

        binding.startActivityButton.setOnClickListener {
            startActivity(Intent(requireContext(), NewActivity::class.java))
        }

        binding.navigation.selectedItemId = currentTab

        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.NavAct -> {
                    if (currentTab != R.id.NavAct) {
                        currentTab = R.id.NavAct
                        updateActivitiesView(lastActivities)
                    }
                    true
                }
                R.id.NavProf -> {
                    if (currentTab != R.id.NavProf) {
                        currentTab = R.id.NavProf
                        showOtherActivities()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateToDetail(activityItem: ActivityListItem.ActivityItem) {
        val detailFragment = DetailFragment.newInstance(activityItem)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, detailFragment)
            .addToBackStack("detail")
            .commit()
    }

    private fun updateActivitiesView(activities: List<UserActivity>) {
        if (activities.isNotEmpty()) {
            binding.emptyStateTextView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            displayActivities(activities)
        } else {
            binding.emptyStateTextView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }

    private fun showOtherActivities() {
        binding.emptyStateTextView.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        adapter.updateItems(listOf(
            ActivityListItem.DateHeader("Вчера"),
            ActivityListItem.ActivityItem(0, "5 км", "30 мин", "Бег", "other_user", "2 часа назад", "10:00", "10:00", null),
            ActivityListItem.ActivityItem(1, "12 км", "45 мин", "Велосипед", "other_user", "3 часа назад", "09:15", "10:00", null)
        ))
    }

    private fun displayActivities(activities: List<UserActivity>) {
        val items = mutableListOf<ActivityListItem>()
        val today = DateTime.now().withTimeAtStartOfDay()
        val yesterday = today.minusDays(1)

        val grouped = activities.groupBy { activity ->
            val activityDay = activity.endTime.withTimeAtStartOfDay()
            when {
                activityDay == today -> "Сегодня"
                activityDay == yesterday -> "Вчера"
                else -> activity.endTime.toString("d MMMM yyyy", Locale("ru"))
            }
        }

        val sortedDates = grouped.keys.sortedByDescending { date ->
            when (date) {
                "Сегодня" -> 2
                "Вчера" -> 1
                else -> 0
            }
        }

        for (date in sortedDates) {
            items.add(ActivityListItem.DateHeader(date))
            grouped[date]?.sortedByDescending { it.endTime }?.forEach { activity ->
                items.add(convertToItem(activity))
            }
        }

        adapter.updateItems(items)
    }

    private fun convertToItem(activity: UserActivity): ActivityListItem.ActivityItem {
        val duration = Duration(activity.startTime, activity.endTime)
        val hours = duration.standardHours
        val minutes = duration.standardMinutes % 60
        val timeText = if (hours > 0) "${hours} ч ${minutes} мин" else "${minutes} мин"

        val distanceMeters = activity.coordinates.size * 100
        val distance = if (distanceMeters < 1000)
            "$distanceMeters м"
        else
            "%.1f км".format(distanceMeters / 1000.0)

        val randomHoursAgo = (1..12).random()
        val timeAgo = "$randomHoursAgo ${hoursDeclension(randomHoursAgo.toLong())} назад"

        return ActivityListItem.ActivityItem(
            id = activity.id,
            distance = distance,
            time = timeText,
            activityType = activity.activityType.name,
            username = "user",
            timeAgo = timeAgo,
            startTime = activity.startTime.toString("HH:mm"),
            endTime = activity.endTime.toString("HH:mm"),
            comment = null
        )
    }

    private fun hoursDeclension(hours: Long): String {
        return when {
            hours % 10 == 1L && hours % 100 != 11L -> "час"
            hours % 10 in 2..4 && hours % 100 !in 12..14 -> "часа"
            else -> "часов"
        }
    }

    private fun deleteActivity(activityId: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            App.database.userActivityDao().delete(activityId)
        }
    }
}