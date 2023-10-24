package dev.duckbuddyy.carplace.listing.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.duckbuddyy.carplace.databinding.FragmentFilterBottomSheetBinding
import dev.duckbuddyy.carplace.model.enums.ListSortDirection
import dev.duckbuddyy.carplace.model.enums.SortType

@AndroidEntryPoint
class FilterBottomSheetFragment : Fragment() {

    private val viewModel: FilterBottomSheetViewModel by viewModels()

    private var _binding: FragmentFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBottomSheetBinding.inflate(layoutInflater, container, false)

        binding.apply {
            btnFilterAscending.setOnClickListener { viewModel.onListDirectionChanged(ListSortDirection.Ascending) }
            btnFilterDescending.setOnClickListener { viewModel.onListDirectionChanged(ListSortDirection.Descending) }
            btnFilterSortPrice.setOnClickListener { viewModel.onSortTypeChanged(SortType.Price) }
            btnFilterSortDate.setOnClickListener { viewModel.onSortTypeChanged(SortType.Date) }
            btnFilterSortYear.setOnClickListener { viewModel.onSortTypeChanged(SortType.Year) }
            etFilterMaxDate.addTextChangedListener { viewModel.onMaxDateChanged(it) }
            etFilterMinDate.addTextChangedListener { viewModel.onMinDateChanged(it) }
            etFilterMaxYear.addTextChangedListener { viewModel.onMaxYearChanged(it) }
            etFilterMinYear.addTextChangedListener { viewModel.onMinYearChanged(it) }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}