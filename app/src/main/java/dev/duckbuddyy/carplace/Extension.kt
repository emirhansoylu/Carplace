package dev.duckbuddyy.carplace

import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
        .placeholder(R.drawable.progress_animation)
        .error(R.drawable.ic_error)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun EditText.setTextAndSelection(text: String?) {
    if(text == null){
        setSelection(0)
        setText(null)
        return
    }

    val selectionPosition = if (text.length < selectionStart) {
        text.length
    } else {
        selectionStart
    }

    setText(text)
    setSelection(selectionPosition)
}

fun ViewPager.setOnPageChangedListener(onPageChanged: (Int) -> Unit) {
    clearOnPageChangeListeners()
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) = Unit

        override fun onPageSelected(position: Int) = onPageChanged(position)

        override fun onPageScrollStateChanged(state: Int) = Unit
    })
}

fun Throwable.log() {
    if (BuildConfig.DEBUG) {
        Log.e("Carplace", message.orEmpty())
    }
}