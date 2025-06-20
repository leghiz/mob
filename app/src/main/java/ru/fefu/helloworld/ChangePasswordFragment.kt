package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.fefu.helloworld.databinding.ChangePasswordFragmentBinding

class ChangePasswordFragment : Fragment() {
    private var _binding: ChangePasswordFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChangePasswordFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.changePasswordButton.setOnClickListener {
            validateAndChangePassword()
        }
    }

    private fun validateAndChangePassword() {
        val oldPassword = binding.oldPasswordText.text.toString()
        val newPassword = binding.newPasswordText.text.toString()
        val confirmPassword = binding.confirmPasswordText.text.toString()

        binding.oldPasswordInput.error = null
        binding.newPasswordInput.error = null
        binding.confirmPasswordInput.error = null

        when {
            oldPassword.isEmpty() -> binding.oldPasswordInput.error = "Введите текущий пароль"
            newPassword.isEmpty() -> binding.newPasswordInput.error = "Введите новый пароль"
            confirmPassword.isEmpty() -> binding.confirmPasswordInput.error = "Подтвердите пароль"
            newPassword != confirmPassword -> {
                binding.newPasswordInput.error = "Пароли не совпадают"
                binding.confirmPasswordInput.error = "Пароли не совпадают"
            }
            newPassword.length < 8 -> binding.newPasswordInput.error = "Пароль слишком короткий"
            else -> {
                showSuccessMessage()
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun showSuccessMessage() {
        Toast.makeText(requireContext(), "Пароль изменён", Toast.LENGTH_SHORT).show()
    }
}