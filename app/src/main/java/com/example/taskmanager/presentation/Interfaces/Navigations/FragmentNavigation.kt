package com.example.taskmanager.presentation.Interfaces.Navigations

import androidx.fragment.app.Fragment

interface FragmentNavigation {
    fun navigateForward(fragment: Fragment)

    fun navigateBack()
}