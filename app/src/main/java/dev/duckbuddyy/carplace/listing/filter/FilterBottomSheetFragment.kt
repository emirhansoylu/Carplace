package dev.duckbuddyy.carplace.listing.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.duckbuddyy.carplace.collectLatestWhenStarted
import dev.duckbuddyy.carplace.databinding.FragmentFilterBottomSheetBinding
import dev.duckbuddyy.carplace.model.enums.ListSortDirection
import dev.duckbuddyy.carplace.model.enums.SortType

@AndroidEntryPoint
class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: FilterBottomSheetViewModel by viewModels()

    private var _binding: FragmentFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val setFragmentResultCollector: suspend (Bundle) -> Unit = {
        setFragmentResult(this@FilterBottomSheetFragment.javaClass.simpleName, it)
        findNavController().popBackStack()
    }

    private val uiStateCollector: suspend (FilterBottomSheetState) -> Unit = { state ->
        updateSortDirectionToggle(state.sortDirection)
        updateSortTypeToggle(state.sortType)
        binding.apply {
            etFilterMaxDate.setText(state.maxDate)
            etFilterMinDate.setText(state.minDate)
            etFilterMaxYear.setText(state.maxYear.toString())
            etFilterMinYear.setText(state.minYear.toString())
        }
    }

    private fun updateSortDirectionToggle(sortDirection: ListSortDirection?) = binding.apply {
        mbtgFilterSortDirection.clearChecked()
        when (sortDirection) {
            ListSortDirection.Ascending -> mbtgFilterSortDirection.check(btnFilterAscending.id)
            ListSortDirection.Descending -> mbtgFilterSortDirection.check(btnFilterDescending.id)
            null -> Unit
        }
    }

    private fun updateSortTypeToggle(sortType: SortType?) = binding.apply {
        mbtgFilterSortType.clearChecked()
        when (sortType) {
            SortType.Date -> mbtgFilterSortType.check(btnFilterSortDate.id)
            SortType.Price -> mbtgFilterSortType.check(btnFilterSortPrice.id)
            SortType.Year -> mbtgFilterSortType.check(btnFilterSortYear.id)
            null -> Unit
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBottomSheetBinding.inflate(layoutInflater, container, false)

        binding.apply {
            btnFilterApply.setOnClickListener { viewModel.applyFilters() }
            btnFilterAscending.setOnClickListener { viewModel.onSortDirectionChanged(ListSortDirection.Ascending) }
            btnFilterDescending.setOnClickListener { viewModel.onSortDirectionChanged(ListSortDirection.Descending) }
            btnFilterSortPrice.setOnClickListener { viewModel.onSortTypeChanged(SortType.Price) }
            btnFilterSortDate.setOnClickListener { viewModel.onSortTypeChanged(SortType.Date) }
            btnFilterSortYear.setOnClickListener { viewModel.onSortTypeChanged(SortType.Year) }
            etFilterMaxDate.addTextChangedListener { viewModel.onMaxDateChanged(it) }
            etFilterMinDate.addTextChangedListener { viewModel.onMinDateChanged(it) }
            etFilterMaxYear.addTextChangedListener { viewModel.onMaxYearChanged(it) }
            etFilterMinYear.addTextChangedListener { viewModel.onMinYearChanged(it) }
        }

        viewModel.apply {
            uiStateFlow.collectLatestWhenStarted(viewLifecycleOwner, uiStateCollector)
            setFragmentResultFlow.collectLatestWhenStarted(viewLifecycleOwner, setFragmentResultCollector)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}