package ru.fefu.helloworld
import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle

class HelloWorld : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hello_world)
    }
}