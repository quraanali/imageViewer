package com.quraanali.imageviewerexample.features.demo.grid

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.quraanali.imageviewer.QuraanaliImageViewer
import  com.quraanali.imageviewerexample.R
import  com.quraanali.imageviewerexample.common.extensions.getDrawableCompat
import  com.quraanali.imageviewerexample.common.extensions.loadImage
import  com.quraanali.imageviewerexample.common.models.Demo
import  com.quraanali.imageviewerexample.common.models.Poster
import kotlinx.android.synthetic.main.activity_demo_posters_grid.*

class PostersGridDemoActivity : AppCompatActivity() {

    private lateinit var viewer: QuraanaliImageViewer<Poster>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_posters_grid)

        postersGridView.apply {
            imageLoader = ::loadPosterImage
            onPosterClick = ::openViewer
        }
    }

    private fun openViewer(startPosition: Int, target: ImageView) {
        viewer = QuraanaliImageViewer.Builder<Poster>(this, Demo.posters, ::loadPosterImage)
            .withStartPosition(startPosition)
            .withTransitionFrom(target)
            .withImageChangeListener {
                viewer.updateTransitionImage(postersGridView.imageViews[it])
            }
            .show()
    }

    private fun loadPosterImage(imageView: ImageView, poster: Poster?) {
        imageView.apply {
            background = getDrawableCompat(R.drawable.shape_placeholder)
            loadImage(poster?.url)
        }
    }
}