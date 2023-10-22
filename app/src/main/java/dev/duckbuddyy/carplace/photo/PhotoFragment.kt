package dev.duckbuddyy.carplace.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.duckbuddyy.carplace.collectLatestWhenStarted
import dev.duckbuddyy.carplace.databinding.FragmentPhotoBinding
import dev.duckbuddyy.carplace.model.enums.PhotoSize
import dev.duckbuddyy.carplace.setOnPageChangedListener

@AndroidEntryPoint
class PhotoFragment : Fragment() {
    private val viewModel: PhotoViewModel by viewModels()

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    private val uiStateCollector: suspend (PhotoState) -> Unit = { state ->
        binding.apply {
            if (state is PhotoState.Success) {
                val images = state.images.map { it.replace("{0}", PhotoSize.XLarge.resolution) }

                PhotoAdapter(
                    imageUrls = images,
                ).also { vpPhoto.adapter = it }

                tvPhoto.text = "${state.imagePosition + 1} / ${images.size}"

                vpPhoto.currentItem = state.imagePosition
                vpPhoto.setOnPageChangedListener { position ->
                    tvPhoto.text = "${position + 1} / ${images.size}"
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoBinding.inflate(layoutInflater, container, false)

        viewModel.uiStateFlow.collectLatestWhenStarted(viewLifecycleOwner, uiStateCollector)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setFragmentResult(KEY_RESULT, bundleOf(KEY_POSITION to binding.vpPhoto.currentItem))
        _binding = null
    }

    companion object {
        const val KEY_RESULT = "key_result"
        const val KEY_POSITION = "key_image_position"
    }
}