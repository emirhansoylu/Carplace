package dev.duckbuddyy.carplace.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import dagger.hilt.android.AndroidEntryPoint
import dev.duckbuddyy.carplace.databinding.FragmentListingBinding
import dev.duckbuddyy.carplace.domain.collectLatestWhenStarted
import dev.duckbuddyy.carplace.model.listing.ListingResponseItem

@AndroidEntryPoint
class ListingFragment : Fragment() {
    private val viewModel: ListingViewModel by viewModels()

    private var _binding: FragmentListingBinding? = null
    private val binding get() = _binding!!

    private val listingAdapter by lazy {
        ListingAdapter(onItemClicked = { navigateToDetailFragment(item = it) })
    }

    private val listingCollector: suspend (PagingData<ListingResponseItem>) -> Unit = {
        listingAdapter.submitData(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvListing.adapter = listingAdapter
        }
        viewModel.apply {
            listingFlow.collectLatestWhenStarted(viewLifecycleOwner, listingCollector)
        }
    }

    private fun navigateToDetailFragment(item: ListingResponseItem) = findNavController().navigate(
        ListingFragmentDirections.actionListingFragmentToDetailFragment(item.id)
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}