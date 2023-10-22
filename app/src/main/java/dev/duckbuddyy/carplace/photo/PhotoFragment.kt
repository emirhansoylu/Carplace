package dev.duckbuddyy.carplace.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
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

                tvPhoto.text = "${state.imagePosition} / ${images.size}"

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiStateFlow.collectLatestWhenStarted(viewLifecycleOwner, uiStateCollector)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}