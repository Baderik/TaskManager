package com.example.taskmanager.presentation.DetailFragment

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.Data.Enity.Task
import com.example.taskmanager.R
import com.example.taskmanager.databinding.BottomSheetAddTaskBinding
import com.example.taskmanager.databinding.FragmentDetailBinding
import com.example.taskmanager.databinding.FragmentListBinding
import com.example.taskmanager.presentation.Interfaces.Listeners.TaskListener
import com.example.taskmanager.presentation.ListFragment.TaskAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.UUID

class DetailFragment:Fragment(), TaskListener {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val detailFragmentViewModel:DetailFragmentViewModel by lazy {
        ViewModelProvider(this)[DetailFragmentViewModel::class.java]
    }
    private lateinit var subtaskCreationDialog: BottomSheetDialog
    private lateinit var taskId: UUID
    private lateinit var task: Task

    private val adapter = SubtaskAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        taskId = requireArguments().getSerializable(TASK_ID) as UUID
        detailFragmentViewModel.onCreate(taskId)

        subtaskCreationDialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
        subtaskCreationDialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        makeSubtaskCreationDialog()
        binding.addSubtaskButton.setOnClickListener{
            subtaskCreationDialog.show()
        }

        binding.recyclerSubtask.adapter = adapter

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailFragmentViewModel.task.observe(viewLifecycleOwner){
            task = it
            updateUI(task)
        }

        detailFragmentViewModel.subtasks.observe(viewLifecycleOwner){
            adapter.setTasks(it as List<Task>)
        }

        binding.apply {
            titleEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    task.title = s.toString()
                }
            })

            descriptionEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    task.description = s.toString()
                }
            })
        }

    }

    private fun updateUI(task: Task) {
        binding.apply {
            titleEditText.setText(task.title)
            descriptionEditText.setText(task.description)
        }
    }

    private fun makeSubtaskCreationDialog() {
        val dialogBinding = BottomSheetAddTaskBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        dialogBinding.saveTaskButton.isEnabled = false

        dialogBinding.titleEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                dialogBinding.saveTaskButton.isEnabled = s?.isNotBlank() == true
            }
        })

        dialogBinding.saveTaskButton.setOnClickListener {
            val task = Task(
                title = dialogBinding.titleEditText.text.toString(),
                description = dialogBinding.descriptionEditText.text.toString(),
                isSelected = false,
                isSuccess = false,
                mainTaskId = taskId
            )
            detailFragmentViewModel.addSubtaskInDatabase(task)
            subtaskCreationDialog.dismiss()
        }
        dialogBinding.titleEditText.requestFocus()

        subtaskCreationDialog.setOnDismissListener {
            dialogBinding.apply {
                titleEditText.text.clear()
                descriptionEditText.text.clear()
            }
        }

        subtaskCreationDialog.setContentView(dialogBinding.root)
    }

    override fun onPause() {
        super.onPause()
        detailFragmentViewModel.isSuccessButton(task)
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


    override fun onSuccessPress(task: Task) {
        detailFragmentViewModel.isSuccessButton(task)
    }

    override fun onDeletePress(task: Task) {
        detailFragmentViewModel.isDeleteButton(task)
    }
}