package ru.fefu.helloworld

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ru.fefu.helloworld.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickableTextViews()
        setupLogoutButton()
    }

    private fun setupClickableTextViews() {
        binding.saveButton.apply {
            setOnClickListener {
                Toast.makeText(requireContext(), "Сохранено", Toast.LENGTH_SHORT).show()
            }
        }

        binding.changePassword.apply {
            setOnClickListener {
                parentFragmentManager.commit {
                    replace<ChangePasswordFragment>(
                        containerViewId = R.id.container,
                        tag = "ChangePasswordFragment"
                    )
                    addToBackStack(null)
                }
            }
        }
    }

    private fun setupLogoutButton() {
        binding.logOut.setOnClickListener {
            val intent = Intent(requireActivity(), LogInActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            requireActivity().finish()
        }
    }
}