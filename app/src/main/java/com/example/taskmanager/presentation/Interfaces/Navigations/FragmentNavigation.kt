package com.example.taskmanager.presentation.Interfaces.Navigations

import androidx.fragment.app.Fragment

interface FragmentNavigation {
    fun start(fragment: Fragment)

    fun back()
}