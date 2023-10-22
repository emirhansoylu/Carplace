package dev.duckbuddyy.carplace.photo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val arguments = PhotoFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _uiStateFlow = MutableStateFlow<PhotoState>(PhotoState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    init {
        initializePhotos()
    }

    private fun initializePhotos() = viewModelScope.launch {
        val photos = arguments.photos
        val imagePosition = arguments.imagePosition
        if (photos.isNotEmpty()) {
            _uiStateFlow.emit(PhotoState.Success(photos, imagePosition))
        } else {
            _uiStateFlow.emit(PhotoState.Error)
        }
    }
}