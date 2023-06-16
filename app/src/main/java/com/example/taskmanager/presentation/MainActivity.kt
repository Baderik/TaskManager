package com.example.taskmanager.presentation

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.taskmanager.R
import com.example.taskmanager.presentation.Interfaces.Navigations.FragmentNavigation
import com.example.taskmanager.presentation.MainFragment.MainFragment

class MainActivity : AppCompatActivity(), FragmentNavigation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        navigateForward(MainFragment.newInstance())
    }


    override fun navigateForward(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun navigateBack() {
        onBackPressed()
    }
}



