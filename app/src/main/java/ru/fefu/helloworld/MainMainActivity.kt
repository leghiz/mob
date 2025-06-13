package ru.fefu.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.helloworld.databinding.ActivityMainFragmentBinding
import androidx.fragment.app.Fragment


class MainMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userOrOtherFragment = UserOrOtherFragment()
        val profileFragment = ProfileFragment()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(binding.container.id, userOrOtherFragment, "usOroth")
                commit()
            }
        }

        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.NavAct -> {
                    switchFragment(userOrOtherFragment, "usOroth")
                    true
                }
                R.id.NavProf -> {
                    switchFragment(profileFragment, "profile")
                    true
                }
                else -> false
            }
        }
    }

    private fun switchFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()

        supportFragmentManager.fragments.forEach { transaction.hide(it) }

        val existingFragment = supportFragmentManager.findFragmentByTag(tag)
        if (existingFragment == null) {
            transaction.add(binding.container.id, fragment, tag)
        } else {
            transaction.show(existingFragment)
        }

        transaction.commit()
    }

}