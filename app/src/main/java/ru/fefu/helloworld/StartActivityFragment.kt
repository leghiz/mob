package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.helloworld.databinding.StartActivityFragmentBinding

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

            parentFragmentManager.commit {
                replace(R.id.container, TrackingActivityFragment.newInstance(selectedActivityName))
                addToBackStack(null)
            }
        }
    }
}