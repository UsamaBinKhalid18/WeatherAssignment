package com.example.weatherassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.weatherassignment.adapter.MonthReportAdapter
import com.example.weatherassignment.adapter.YearReportAdapter
import com.example.weatherassignment.databinding.FragmentReportBinding
import com.example.weatherassignment.util.Provider
import com.example.weatherassignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentReportBinding.inflate(inflater)
        viewModel.reportGenerated.observe(viewLifecycleOwner) {
            when (it) {
                1 -> {
                    val adapter = YearReportAdapter(
                        viewModel.yearResult,
                        Provider.provideMonthArrayAdapter(requireContext())
                    )
                    binding.recyclerView.adapter = adapter
                    binding.tvReport.text = "Year Report"
                }
                2 -> {
                    val adapter = MonthReportAdapter(
                        viewModel.monthList.distinct(),
                        viewModel.monthResult,
                        Provider.provideMonthArrayAdapter(requireContext())
                    )
                    binding.recyclerView.adapter = adapter
                    binding.tvReport.text = "Monthly Report"
                }
            }
        }


        return binding.root
    }

}