package dev.duckbuddyy.carplace.detail

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import dev.duckbuddyy.carplace.util.load

class DetailImageAdapter(
    private val imageUrls: List<String>,
    private val onItemClicked: (Int) -> Unit
) : PagerAdapter() {
    override fun getCount(): Int = imageUrls.size

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context).apply {
            val imageUrl = imageUrls[position]
            load(imageUrl)
            setOnClickListener { onItemClicked(position) }
        }

        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}
