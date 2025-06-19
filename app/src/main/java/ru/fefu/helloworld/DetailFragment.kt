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
            tvActivityType.text = activity.activityType
            tvUsername.text = activity.username
            tvDistance.text = activity.distance
            tvTime.text = activity.time
            tvTimeAgo.text = activity.timeAgo
            tvStartTime.text = activity.startTime
            tvEndTime.text = activity.endTime
            etComment.setText(activity.comment ?: "")

            btnBack.setOnClickListener {
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