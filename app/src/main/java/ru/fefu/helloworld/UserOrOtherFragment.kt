package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.helloworld.databinding.UserOrOtherFragmentBinding

class UserOrOtherFragment : Fragment() {

    private lateinit var binding: UserOrOtherFragmentBinding
    private lateinit var adapter: ActivitiesAdapter

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

        adapter = ActivitiesAdapter(emptyList()) { activity ->
            val detailFragment = DetailFragment.newInstance(activity)
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@UserOrOtherFragment.adapter
        }

        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.NavAct -> {
                    loadMyActivities()
                    true
                }
                R.id.NavProf -> {
                    loadOtherActivities()
                    true
                }
                else -> false
            }
        }
        loadMyActivities()
    }

    private fun loadMyActivities() {
        val items = listOf(
            ActivityListItem.DateHeader("Вчера"),
            ActivityListItem.ActivityItem(1, "100 м", "10 минут", "Серфинг", "user", "14 часов назад","13:00","13:10"),
            ActivityListItem.ActivityItem(2, "14 км", "2 часа 15 минут", "Бег", "user", "12 часов назад","13:00","13:10"),
            ActivityListItem.DateHeader("Апрель 2025 года"),
            ActivityListItem.ActivityItem(3, "258 м", "20 минут", "Велосипед", "user", "03.04.2025","13:00","13:10")
        )
        adapter.updateItems(items)
    }

    private fun loadOtherActivities() {
        val items = listOf(
            ActivityListItem.DateHeader(""),
        )
        adapter.updateItems(items)
    }
}