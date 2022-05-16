package  com.quraanali.imageviewerexample.common.ui.base

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import  com.quraanali.imageviewerexample.R
import  com.quraanali.imageviewerexample.common.extensions.getDrawableCompat
import  com.quraanali.imageviewerexample.common.extensions.loadImage
import  com.quraanali.imageviewerexample.common.models.Poster

abstract class BaseActivity : AppCompatActivity() {

    protected fun loadPosterImage(imageView: ImageView, poster: Poster?) {
        loadImage(imageView, poster?.url)
    }

    protected fun loadImage(imageView: ImageView, url: String?) {
        imageView.apply {
            background = getDrawableCompat(R.drawable.shape_placeholder)
            loadImage(url)
        }
    }
}