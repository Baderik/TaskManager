package com.example.taskmanager.presentation.DetailFragment

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.Data.Enity.Task
import com.example.taskmanager.databinding.FragmentDetailBinding
import com.example.taskmanager.databinding.FragmentListBinding
import java.util.UUID

class DetailFragment:Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val detailFragmentViewModel:DetailFragmentViewModel by lazy {
        ViewModelProvider(this)[DetailFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val taskId = requireArguments().getSerializable(TASK_ID) as UUID
        detailFragmentViewModel.onCreate(taskId)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailFragmentViewModel.task.observe(viewLifecycleOwner){
            updateUI(it)
        }

    }

    private fun updateUI(task: Task) {
        binding.apply {
            titleEditText.setText(task.title)
            descriptionEditText.setText(task.description)
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

    companion object{

        private const val TASK_ID = "task_id"
        fun newInstance(taskId:UUID):DetailFragment{
            val arg = bundleOf(
                TASK_ID to taskId
            )
            return DetailFragment().apply {
                arguments = arg
            }
        }
    }
}