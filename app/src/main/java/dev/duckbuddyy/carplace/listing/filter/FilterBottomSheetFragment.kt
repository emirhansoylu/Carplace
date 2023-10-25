package dev.duckbuddyy.carplace.listing.filter

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.duckbuddyy.carplace.util.collectLatestWhenStarted
import dev.duckbuddyy.carplace.databinding.FragmentFilterBottomSheetBinding
import dev.duckbuddyy.carplace.model.enums.ListSortDirection
import dev.duckbuddyy.carplace.model.enums.ListSortDirection.Ascending
import dev.duckbuddyy.carplace.model.enums.ListSortDirection.Descending
import dev.duckbuddyy.carplace.model.enums.SortType
import dev.duckbuddyy.carplace.model.enums.SortType.Year
import dev.duckbuddyy.carplace.model.enums.SortType.Date
import dev.duckbuddyy.carplace.model.enums.SortType.Price
import dev.duckbuddyy.carplace.model.filter.ListingFilter
import dev.duckbuddyy.carplace.util.setTextAndSelection

@AndroidEntryPoint
class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: FilterBottomSheetViewModel by viewModels()

    private var _binding: FragmentFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val popBackStackCollector: suspend (ListingFilter) -> Unit = { filter ->
        Bundle().apply {
            putParcelable(ListingFilter::class.java.simpleName, filter)
        }.also {
            setFragmentResult(FilterBottomSheetFragment::class.java.simpleName, it)
        }
        findNavController().popBackStack()
    }

    private val uiStateCollector: suspend (FilterBottomSheetState) -> Unit = { state ->
        updateSortDirectionToggle(state.filter.sortDirection)
        updateSortTypeToggle(state.filter.sortType)
        binding.apply {
            etFilterMaxDate.setTextAndSelection(state.filter.maxDate)
            etFilterMinDate.setTextAndSelection(state.filter.minDate)
            etFilterMaxYear.setTextAndSelection(state.filter.maxYear?.toString())
            etFilterMinYear.setTextAndSelection(state.filter.minYear?.toString())
        }
    }

    private fun updateSortDirectionToggle(sortDirection: ListSortDirection?) = binding.apply {
        mbtgFilterSortDirection.clearChecked()
        when (sortDirection) {
            Ascending -> mbtgFilterSortDirection.check(btnFilterAscending.id)
            Descending -> mbtgFilterSortDirection.check(btnFilterDescending.id)
            null -> Unit
        }
    }

    private fun updateSortTypeToggle(sortType: SortType?) = binding.apply {
        mbtgFilterSortType.clearChecked()
        when (sortType) {
            Date -> mbtgFilterSortType.check(btnFilterSortDate.id)
            Price -> mbtgFilterSortType.check(btnFilterSortPrice.id)
            Year -> mbtgFilterSortType.check(btnFilterSortYear.id)
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
            btnFilterAscending.setOnClickListener { viewModel.onSortDirectionChanged(Ascending) }
            btnFilterDescending.setOnClickListener { viewModel.onSortDirectionChanged(Descending) }
            btnFilterSortPrice.setOnClickListener { viewModel.onSortTypeChanged(Price) }
            btnFilterSortDate.setOnClickListener { viewModel.onSortTypeChanged(Date) }
            btnFilterSortYear.setOnClickListener { viewModel.onSortTypeChanged(Year) }
            etFilterMaxDate.addTextChangedListener { viewModel.onMaxDateChanged(it) }
            etFilterMinDate.addTextChangedListener { viewModel.onMinDateChanged(it) }
            etFilterMaxYear.addTextChangedListener { viewModel.onMaxYearChanged(it) }
            etFilterMinYear.addTextChangedListener { viewModel.onMinYearChanged(it) }
        }

        viewModel.apply {
            uiStateFlow.collectLatestWhenStarted(viewLifecycleOwner, uiStateCollector)
            popBackStackFlow.collectLatestWhenStarted(viewLifecycleOwner, popBackStackCollector)
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomSheet = findViewById<FrameLayout>(R.id.design_bottom_sheet)
                val layoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
                BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
                bottomSheet.layoutParams = layoutParams
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}