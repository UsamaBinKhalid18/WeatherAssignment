package com.example.weatherassignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.weatherassignment.R
import com.example.weatherassignment.adapter.MonthListAdapterRV
import com.example.weatherassignment.adapter.YearListAdapterRV
import com.example.weatherassignment.databinding.FragmentListBinding
import com.example.weatherassignment.util.Provider
import com.example.weatherassignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var yearListAdapterRV: YearListAdapterRV
    private lateinit var monthListAdapterRV: MonthListAdapterRV

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater)
        val yearAdapter = Provider.provideYearArrayAdapter(requireContext())
        val monthAdapter = Provider.provideMonthArrayAdapter(requireContext())
        //viewModel = Provider.provideViewModel(this, requireContext())

        yearListAdapterRV = YearListAdapterRV(yearAdapter, viewModel::updateYearList)
        yearListAdapterRV.submitList(viewModel.yearList)

        monthListAdapterRV =
            MonthListAdapterRV(yearAdapter, monthAdapter, viewModel::updateMonthList)
        monthListAdapterRV.submitList(viewModel.monthList)
        binding.recyclerView.adapter =
            if (binding.tglReport.isChecked) yearListAdapterRV else monthListAdapterRV

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                resources.configuration.orientation
            )
        )
        onClickListeners()

        return binding.root
    }

    private fun onClickListeners() {
        binding.btAddItem.setOnClickListener {
            if (binding.tglReport.isChecked) {
                viewModel.updateYearList(2004)
                yearListAdapterRV.submitList(viewModel.yearList)
                yearListAdapterRV.notifyItemInserted(viewModel.yearList.size - 1)
                Log.d("TAG", "onClickListeners: ${viewModel.yearList}")
            } else {
                viewModel.updateMonthList(2004, 7)
                monthListAdapterRV.submitList(viewModel.monthList)
                monthListAdapterRV.notifyItemInserted(viewModel.monthList.size - 1)
                Log.d("TAG", "onClickListeners: ${viewModel.monthList}")
            }
        }

        binding.tglReport.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.recyclerView.adapter = yearListAdapterRV
            } else {
                binding.recyclerView.adapter = monthListAdapterRV
            }
        }

        binding.btGenerateReport.setOnClickListener {
            if (binding.tglReport.isChecked) {
                viewModel.generateYearResult()
            } else {
                viewModel.generateMonthResult()
            }
            parentFragmentManager.commit {
                replace(R.id.main_fragment_container, ReportFragment())
                addToBackStack("Home")
            }
        }

        binding.btDelete.setOnClickListener {
            if(binding.tglReport.isChecked){
                viewModel.deleteYearList()
                yearListAdapterRV.submitList(viewModel.yearList)
                yearListAdapterRV.notifyDataSetChanged()
            }else{
                viewModel.deleteMonthList()
                monthListAdapterRV.submitList(viewModel.monthList)
                monthListAdapterRV.notifyDataSetChanged()
            }
        }
    }

}