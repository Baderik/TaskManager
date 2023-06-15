package com.example.taskmanager.presentation.MainFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.taskmanager.Data.Enity.Task
import com.example.taskmanager.R
import com.example.taskmanager.databinding.BottomSheetAddTaskBinding
import com.example.taskmanager.databinding.FragmentMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout

class MainFragment:Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    val mainFragmentViewModel: MainFragmentViewModel by lazy {
        ViewModelProvider(this)[MainFragmentViewModel::class.java]
    }

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var taskCreationDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        taskCreationDialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
        taskCreationDialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        makeTaskCreationDialog()

        binding.floatingActionButton.setOnClickListener{
            taskCreationDialog.show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.apply {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.tabLayout.getTabAt(position)!!.select()
                }
            })
        }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun makeTaskCreationDialog() {
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
                mainTaskId = null
            )

            mainFragmentViewModel.addTaskInDatabase(task)
            taskCreationDialog.dismiss()
        }
        dialogBinding.titleEditText.requestFocus()

        taskCreationDialog.setOnDismissListener {
            dialogBinding.apply {
                titleEditText.text.clear()
                descriptionEditText.text.clear()
            }
        }

        taskCreationDialog.setContentView(dialogBinding.root)
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


