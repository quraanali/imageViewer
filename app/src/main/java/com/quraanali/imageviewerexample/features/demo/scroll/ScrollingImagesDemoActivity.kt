package com.quraanali.imageviewerexample.features.demo.scroll

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.quraanali.imageviewer.QuraanaliImageViewer
import com.quraanali.imageviewerexample.R
import  com.quraanali.imageviewerexample.common.extensions.getDrawableCompat
import  com.quraanali.imageviewerexample.common.extensions.loadImage
import  com.quraanali.imageviewerexample.common.models.Demo
import kotlinx.android.synthetic.main.activity_demo_scrolling_images.*

class ScrollingImagesDemoActivity : AppCompatActivity() {

    private val horizontalImageViews by lazy {
        listOf(
            scrollingHorizontalFirstImage,
            scrollingHorizontalSecondImage,
            scrollingHorizontalThirdImage,
            scrollingHorizontalFourthImage
        )
    }

    private val verticalImageViews by lazy {
        listOf(
            scrollingVerticalFirstImage,
            scrollingVerticalSecondImage,
            scrollingVerticalThirdImage,
            scrollingVerticalFourthImage
        )
    }

    private lateinit var viewer: QuraanaliImageViewer<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_scrolling_images)

        horizontalImageViews.forEachIndexed { index, imageView ->
            loadImage(imageView, Demo.horizontalImages.getOrNull(index))
            imageView.setOnClickListener {
                openViewer(index, imageView, Demo.horizontalImages, horizontalImageViews)
            }
        }

        verticalImageViews.forEachIndexed { index, imageView ->
            loadImage(imageView, Demo.verticalImages.getOrNull(index))
            imageView.setOnClickListener {
                openViewer(index, imageView, Demo.verticalImages, verticalImageViews)
            }
        }
    }

    private fun openViewer(
        startPosition: Int,
        target: ImageView,
        images: List<String>,
        imageViews: List<ImageView>
    ) {
        viewer = QuraanaliImageViewer.Builder<String>(this, images, ::loadImage)
            .withStartPosition(startPosition)
            .withTransitionFrom(target)
            .withImageChangeListener { viewer.updateTransitionImage(imageViews.getOrNull(it)) }
            .show()
    }

    private fun loadImage(imageView: ImageView, url: String?) {
        imageView.apply {
            background = getDrawableCompat(R.drawable.shape_placeholder)
            loadImage(url)
        }
    }
}