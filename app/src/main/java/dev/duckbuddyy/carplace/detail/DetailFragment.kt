package dev.duckbuddyy.carplace.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.duckbuddyy.carplace.databinding.FragmentDetailBinding
import dev.duckbuddyy.carplace.photo.PhotoFragment
import dev.duckbuddyy.carplace.photo.PhotoFragment.Companion.KEY_POSITION
import dev.duckbuddyy.carplace.util.collectLatestWhenStarted

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
                DetailImageAdapter(
                    imageUrls = state.detail.getSizedPhotos(),
                    onItemClicked = { viewModel.onImageClicked(imagePosition = it) }
                ).also { vpDetail.adapter = it }

                PropertyAdapter(state.detail.extendedProperties).also {
                    layoutDetail.rvDetailProperty.adapter = it
                    layoutDetail.rvDetailProperty.suppressLayout(true)
                }

                tvDetailTitle.text = state.detail.title
                layoutDetail.tvUserName.text = state.detail.userInfo.nameSurname
                layoutDetail.tvLocation.text = state.detail.location.toString()
                layoutDetail.tvModelName.text = state.detail.modelName
                layoutDetail.tvDetailDescription.text = state.detail.escapedText
                layoutDetail.tvDetailPrice.text = state.detail.actualPrice
            }
        }
    }

    private val navigationCollector: suspend (NavDirections) -> Unit = {
        findNavController().navigate(it)
    }

    private val launchIntentCollector: suspend (Intent) -> Unit = {
        startActivity(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            layoutDetail.btnCall.setOnClickListener { viewModel.onCallClicked() }
        }

        viewModel.apply {
            uiStateFlow.collectLatestWhenStarted(viewLifecycleOwner, uiStateCollector)
            launchIntentFlow.collectLatestWhenStarted(viewLifecycleOwner, launchIntentCollector)
            navigationFlow.collectLatestWhenStarted(viewLifecycleOwner, navigationCollector)
        }

        setFragmentResultListener(PhotoFragment::class.java.simpleName) { _, bundle ->
            val viewPagerPosition = bundle.getInt(KEY_POSITION, -1)
            if (viewPagerPosition != -1) {
                binding.vpDetail.currentItem = viewPagerPosition
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}