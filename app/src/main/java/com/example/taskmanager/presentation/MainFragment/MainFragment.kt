package com.example.taskmanager.presentation.MainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskmanager.databinding.FragmentMainBinding

class MainFragment:Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

    companion object{
        fun newInstance(): MainFragment{
            return MainFragment()
        }
    }
}