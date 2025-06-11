package ru.fefu.helloworld

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.helloworld.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registrationButt.setOnClickListener {
        }

        binding.back.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}