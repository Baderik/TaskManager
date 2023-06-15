package com.example.taskmanager.presentation.ListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.databinding.FragmentMainBinding
import com.example.taskmanager.databinding.FragmentListBinding
import com.example.taskmanager.presentation.MainFragment.MainFragmentViewModel


class ListFragment:Fragment() {


    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    val listFragmentViewModel: ListFragmentViewModel by lazy {
        ViewModelProvider(this)[ListFragmentViewModel::class.java]
    }

    private val adapter = TaskAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(requireArguments().getInt(KEY_PAGE)){
            0 -> listFragmentViewModel.selectedTasks.observe(viewLifecycleOwner){
                adapter.setTasks(it)
            }
            1 -> listFragmentViewModel.tasks.observe(viewLifecycleOwner){
                adapter.setTasks(it)
            }
            2 -> listFragmentViewModel.successTasks.observe(viewLifecycleOwner){
                adapter.setTasks(it)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

    companion object{

        private const val KEY_PAGE = "key_page"
        fun newInstance(page: Int): ListFragment{
            val arg = bundleOf(
                 KEY_PAGE to page
            )
            return ListFragment().apply {
                arguments = arg
            }
        }
    }
}
