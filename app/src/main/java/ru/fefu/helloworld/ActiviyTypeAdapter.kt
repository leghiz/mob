package ru.fefu.helloworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class ActivityTypeAdapter(
    private val activityTypes: List<ActivityType>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ActivityTypeAdapter.ActivityTypeViewHolder>() {

    inner class ActivityTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val typeTextView: TextView = itemView.findViewById(R.id.typeText)

        fun bind(activityType: ActivityType) {
            typeTextView.text = activityType.name

            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityTypeViewHolder {
        val layoutRes = if (viewType == SELECTED_VIEW_TYPE) {
            R.layout.list_types_selected
        } else {
            R.layout.list_types
        }

        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return ActivityTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityTypeViewHolder, position: Int) {
        holder.bind(activityTypes[position])
    }

    override fun getItemCount(): Int = activityTypes.size

    override fun getItemViewType(position: Int): Int {
        return if (activityTypes[position].isSelected) SELECTED_VIEW_TYPE else UNSELECTED_VIEW_TYPE
    }

    companion object {
        private const val SELECTED_VIEW_TYPE = 1
        private const val UNSELECTED_VIEW_TYPE = 2
    }
}