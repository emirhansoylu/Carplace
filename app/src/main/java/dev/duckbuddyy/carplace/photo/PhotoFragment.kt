package dev.duckbuddyy.carplace.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.duckbuddyy.carplace.collectLatestWhenStarted
import dev.duckbuddyy.carplace.databinding.FragmentPhotoBinding

@AndroidEntryPoint
class PhotoFragment : Fragment() {
    private val viewModel: PhotoViewModel by viewModels()

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    private val uiStateCollector: suspend (PhotoState) -> Unit = { state ->
        if (state is PhotoState.Success) {
            //TODO
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