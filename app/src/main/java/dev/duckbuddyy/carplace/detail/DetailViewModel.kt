package dev.duckbuddyy.carplace.detail

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.duckbuddyy.carplace.model.detail.DetailResponse
import dev.duckbuddyy.carplace.util.CarplaceRepository
import dev.duckbuddyy.carplace.util.log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: CarplaceRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val arguments = DetailFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _uiStateFlow = MutableStateFlow<DetailState>(DetailState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _launchIntentFlow = MutableSharedFlow<Intent>()
    val launchIntentFlow = _launchIntentFlow.asSharedFlow()

    private val _navigationFlow = MutableSharedFlow<NavDirections>()
    val navigationFlow = _navigationFlow.asSharedFlow()

    private val detail: DetailResponse?
        get() = when (val state = uiStateFlow.value) {
            is DetailState.Success -> state.detail
            else -> null
        }

    init {
        getDetail()
    }

    private fun getDetail() = viewModelScope.launch {
        _uiStateFlow.emit(DetailState.Loading)
        repository.getDetail(
            id = arguments.id
        ).onSuccess {
            _uiStateFlow.emit(DetailState.Success(it))
        }.onFailure {
            it.log()
            _uiStateFlow.emit(DetailState.Error)
        }
    }

    fun onCallClicked() = viewModelScope.launch {
        val phoneNumber = detail?.userInfo?.phone ?: return@launch

        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }

        _launchIntentFlow.emit(dialIntent)
    }

    fun onImageClicked(imagePosition: Int) = viewModelScope.launch {
        if (detail == null) {
            return@launch
        }

        _navigationFlow.emit(
            DetailFragmentDirections.actionDetailFragmentToPhotoFragment(
                photos = detail!!.photos.toTypedArray(),
                imagePosition = imagePosition
            )
        )
    }
}