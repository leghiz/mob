package ru.fefu.helloworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActivitiesAdapter(
    private var items: List<ActivityListItem>,
    private val onClick: (ActivityListItem.ActivityItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int) = when (items[position]) {
        is ActivityListItem.DateHeader -> 0
        is ActivityListItem.ActivityItem -> 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        0 -> DateHeaderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_date_header, parent, false)
        )
        else -> ActivityViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_activity, parent, false),
            onClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ActivityListItem.DateHeader -> (holder as DateHeaderViewHolder).bind(item)
            is ActivityListItem.ActivityItem -> (holder as ActivityViewHolder).bind(item)
        }
    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: List<ActivityListItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    class DateHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.findViewById<TextView>(R.id.dateTextView)
        fun bind(item: ActivityListItem.DateHeader) {
            textView.text = item.date
        }
    }

    class ActivityViewHolder(
        view: View,
        private val onClick: (ActivityListItem.ActivityItem) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val distanceView = view.findViewById<TextView>(R.id.distanceText)
        private val timeView = view.findViewById<TextView>(R.id.timeActivityTextView)
        private val activityTypeView = view.findViewById<TextView>(R.id.activityTypeTextView)
        private val usernameView = view.findViewById<TextView>(R.id.usernameTextView)
        private val timeAgoView = view.findViewById<TextView>(R.id.timeAgoTextView)

        fun bind(item: ActivityListItem.ActivityItem) {
            distanceView.text = item.distance
            timeView.text = item.time
            activityTypeView.text = item.activityType
            usernameView.text = "@${item.username}"
            timeAgoView.text = item.timeAgo
            itemView.setOnClickListener { onClick(item) }
        }
    }
}

