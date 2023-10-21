package dev.duckbuddyy.carplace.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.duckbuddyy.carplace.databinding.FragmentDetailBinding
import dev.duckbuddyy.carplace.domain.collectLatestWhenStarted
import dev.duckbuddyy.carplace.domain.load

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val uiStateCollector: suspend (DetailState) -> Unit = { state ->
        binding.apply {
            layoutDetailLoading.root.isVisible = state == DetailState.Loading
            layoutDetailError.root.isVisible = state == DetailState.Error
            clDetail.isVisible = state is DetailState.Success
            if (state is DetailState.Success) {
                ivDetail.load(state.detail.photos.first())
                layoutDetail.tvDetailName.text = state.detail.title
                layoutDetail.tvDetailDescription.text = state.detail.text
                layoutDetail.tvDetailPrice.text = state.detail.priceFormatted ?: state.detail.price.toString()
            }
        }
    }

    private val launchIntentCollector: suspend (Intent) -> Unit = {
        startActivity(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply { 
            
        }
        binding.layoutDetail.btnCall.setOnClickListener { viewModel.onCallClicked() }
        viewModel.apply {
            uiStateFlow.collectLatestWhenStarted(viewLifecycleOwner, uiStateCollector)
            launchIntentFlow.collectLatestWhenStarted(viewLifecycleOwner, launchIntentCollector)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}