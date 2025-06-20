package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fefu.helloworld.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = arguments?.getParcelable<ActivityListItem.ActivityItem>("activity")!!

        with(binding) {
            activityTypeText.text = activity.activityType
            usernameText.text = activity.username
            distanceText.text = activity.distance
            timeText.text = activity.time
            timeAgoText.text = activity.timeAgo
            startTimeText.text = activity.startTime
            endTimeText.text = activity.endTime
            commentText.setText(activity.comment ?: "")

            back.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    companion object {
        fun newInstance(activity: ActivityListItem.ActivityItem): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("activity", activity)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}