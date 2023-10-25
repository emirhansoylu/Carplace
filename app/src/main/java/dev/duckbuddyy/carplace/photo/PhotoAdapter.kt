package dev.duckbuddyy.carplace.photo

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.jsibbold.zoomage.AutoResetMode
import dev.duckbuddyy.carplace.util.load

class PhotoAdapter(
    private val imageUrls: List<String>,
) : PagerAdapter() {
    override fun getCount(): Int = imageUrls.size

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = com.jsibbold.zoomage.ZoomageView(container.context).apply {
            val imageUrl = imageUrls[position]
            load(imageUrl)
            animateOnReset = true
            autoResetMode = AutoResetMode.UNDER
            restrictBounds = true
            isTranslatable = true
            isZoomable = true
        }

        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}
