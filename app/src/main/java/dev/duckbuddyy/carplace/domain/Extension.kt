package dev.duckbuddyy.carplace.domain

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dev.duckbuddyy.carplace.network.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.collectLatestWhenStarted(
    viewLifecycleOwner: LifecycleOwner,
    crossinline collector: suspend (T) -> Unit
) = viewLifecycleOwner.lifecycleScope.launch {
    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        collectLatest {
            collector(it)
        }
    }
}

fun ImageView.load(imageUrl: String) {
    Glide
        .with(context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun Throwable.log() {
    if (BuildConfig.DEBUG) {
        Log.e("Carplace", message.orEmpty())
    }
}