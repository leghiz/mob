package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fefu.helloworld.databinding.UserOrOtherFragmentBinding

class UserOrOtherFragment : Fragment() {

    private lateinit var binding: UserOrOtherFragmentBinding

    private val userFragment = ProfileFragment()
    private val otherFragment = OtherFragment()

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

        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.NavAct -> {
                    switchChildFragment(userFragment, "user")
                    true
                }
                R.id.NavProf -> {
                    switchChildFragment(otherFragment, "other")
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            switchChildFragment(userFragment, "user")
        }
    }

    private fun switchChildFragment(fragment: Fragment, tag: String) {
        val transaction = childFragmentManager.beginTransaction()

        childFragmentManager.fragments.forEach { transaction.hide(it) }

        val existing = childFragmentManager.findFragmentByTag(tag)
        if (existing == null) {
            transaction.add(binding.container.id, fragment, tag)
        } else {
            transaction.show(existing)
        }

        transaction.commit()
    }
}
