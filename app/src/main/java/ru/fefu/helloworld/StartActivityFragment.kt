package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import ru.fefu.helloworld.databinding.StartActivityFragmentBinding
import kotlin.random.Random

class StartActivityFragment : Fragment() {
    private lateinit var binding: StartActivityFragmentBinding
    private val activityTypes = mutableListOf(
        ActivityType("Велосипед"),
        ActivityType("Бег"),
        ActivityType("Шаг")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StartActivityFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupButton()
    }

    private fun setupRecyclerView() {
        binding.recycleView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val adapter = ActivityTypeAdapter(activityTypes) { position ->
            activityTypes.forEachIndexed { index, activityType ->
                activityType.isSelected = index == position
            }
            binding.recycleView.adapter?.notifyDataSetChanged()
        }

        binding.recycleView.adapter = adapter
    }

    private fun setupButton() {
        binding.start.setOnClickListener {
            val selectedActivityName = activityTypes
                .firstOrNull { it.isSelected }
                ?.name
                ?: run {
                    return@setOnClickListener
                }

            saveActivityToDatabase(selectedActivityName)

            parentFragmentManager.commit {
                replace(R.id.container, TrackingActivityFragment.newInstance(selectedActivityName))
                addToBackStack(null)
            }
        }
    }

    private fun saveActivityToDatabase(activityName: String) {
        val activityType = when (activityName) {
            "Велосипед" -> ActivityTypeName.Велосипед
            "Бег" -> ActivityTypeName.Бег
            "Шаг" -> ActivityTypeName.Шаг
            else -> ActivityTypeName.Шаг
        }

        val startTime = DateTime.now()
        val endTime = startTime.plusMinutes(Random.nextInt(10, 120))
        val coordinates = generateRandomCoordinates()

        val activity = UserActivity(
            activityType = activityType,
            startTime = startTime,
            endTime = endTime,
            coordinates = coordinates
        )

        GlobalScope.launch(Dispatchers.IO) {
            App.database.userActivityDao().insert(activity)
        }
    }

    private fun generateRandomCoordinates(): List<Coordinate> {
        val random = Random.Default
        val count = Random.nextInt(10, 100)
        return List(count) {
            Coordinate(
                latitude = 55.75 + random.nextDouble(-0.1, 0.1),
                longitude = 37.62 + random.nextDouble(-0.1, 0.1)
            )
        }
    }
}