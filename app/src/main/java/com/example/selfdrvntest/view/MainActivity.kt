package com.example.selfdrvntest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.selfdrvntest.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val fragmentList: List<*> = supportFragmentManager.fragments
        var handled = false
        for (f in fragmentList) {
            if (f is AddTaskFragment) {
               finish()
            }
        }
        if (!handled) {
            super.onBackPressed()
        }
    }

}