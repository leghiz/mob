package ru.fefu.helloworld

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.new_activity)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, StartActivityFragment())
            .commit()

        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
    }
}