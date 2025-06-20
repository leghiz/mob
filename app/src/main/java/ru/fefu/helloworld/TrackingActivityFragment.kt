package ru.fefu.helloworld

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fefu.helloworld.databinding.TrackingActivityFragmentBinding
import java.util.Timer
import java.util.TimerTask

class TrackingActivityFragment : Fragment() {

    private var _binding: TrackingActivityFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var timer: Timer
    private var startTime = 0L
    private var distance = 0.0
    private var isTracking = true

    companion object {
        private const val ARG_ACTIVITY_NAME = "activity_name"

        fun newInstance(activityName: String): TrackingActivityFragment {
            val args = Bundle().apply {
                putString(ARG_ACTIVITY_NAME, activityName)
            }
            val fragment = TrackingActivityFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TrackingActivityFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activityName = arguments?.getString(ARG_ACTIVITY_NAME)
        binding.activityType.text = activityName

        startTracking()

        binding.finish.setOnClickListener {
            stopTracking()
            parentFragmentManager.popBackStack()
        }
    }

    private fun startTracking() {
        startTime = SystemClock.elapsedRealtime()
        timer = Timer()

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    updateViews()
                }
            }
        }, 0, 1000)
    }

    private fun updateViews() {
        val currentTime = SystemClock.elapsedRealtime() - startTime
        val hours = (currentTime / 3600000).toInt()
        val minutes = (currentTime % 3600000 / 60000).toInt()
        val seconds = (currentTime % 60000 / 1000).toInt()
        binding.timer.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        val activityName = binding.activityType.text.toString()
        distance += when (activityName) {
            "Бег" -> 0.003
            "Велосипед" -> 0.010
            "Шаг" -> 0.0015
            else -> 0.002
        }
        binding.distance.text = "%.2f км".format(distance)
    }

    private fun stopTracking() {
        timer.cancel()
        isTracking = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::timer.isInitialized) {
            timer.cancel()
        }
        _binding = null
    }
}