package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fefu.helloworld.databinding.OtherFragmentBinding


class OtherFragment : Fragment() {
    private lateinit var binding: OtherFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OtherFragmentBinding.inflate(layoutInflater)
        return binding.root
    }
}