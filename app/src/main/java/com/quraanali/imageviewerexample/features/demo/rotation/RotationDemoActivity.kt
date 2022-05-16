package com.quraanali.imageviewerexample.features.demo.rotation

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.quraanali.imageviewer.QuraanaliImageViewer
import  com.quraanali.imageviewerexample.R
import  com.quraanali.imageviewerexample.common.extensions.getDrawableCompat
import  com.quraanali.imageviewerexample.common.extensions.loadImage
import  com.quraanali.imageviewerexample.common.models.Demo
import  com.quraanali.imageviewerexample.common.models.Poster
import kotlinx.android.synthetic.main.activity_demo_rotation.*

class RotationDemoActivity : AppCompatActivity() {

    companion object {
        private const val KEY_IS_DIALOG_SHOWN = "IS_DIALOG_SHOWN"
        private const val KEY_CURRENT_POSITION = "CURRENT_POSITION"
    }

    private var isDialogShown = false
    private var currentPosition: Int = 0

    private lateinit var viewer: QuraanaliImageViewer<Poster>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_rotation)

        rotationDemoImage.setOnClickListener { openViewer(0) }
        loadPosterImage(rotationDemoImage, Demo.posters[0])
    }

    override fun onPause() {
        super.onPause()
        viewer.dismiss()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null) {
            isDialogShown = savedInstanceState.getBoolean(KEY_IS_DIALOG_SHOWN)
            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION)
        }

        if (isDialogShown) {
            openViewer(currentPosition)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(KEY_IS_DIALOG_SHOWN, isDialogShown)
        outState.putInt(KEY_CURRENT_POSITION, currentPosition)
        super.onSaveInstanceState(outState)
    }

    private fun openViewer(startPosition: Int) {
        viewer = QuraanaliImageViewer.Builder<Poster>(this, Demo.posters, ::loadPosterImage)
            .withTransitionFrom(getTransitionTarget(startPosition))
            .withStartPosition(startPosition)
            .withImageChangeListener {
                currentPosition = it
                viewer.updateTransitionImage(getTransitionTarget(it))
            }
            .withDismissListener { isDialogShown = false }
            .show(!isDialogShown)

        currentPosition = startPosition
        isDialogShown = true
    }

    private fun loadPosterImage(imageView: ImageView, poster: Poster?) {
        imageView.apply {
            background = getDrawableCompat(R.drawable.shape_placeholder)
            loadImage(poster?.url)
        }
    }

    private fun getTransitionTarget(position: Int) =
        if (position == 0) rotationDemoImage else null
}