package  com.quraanali.imageviewerexample.features.demo.styled

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.quraanali.imageviewer.QuraanaliImageViewer
import com.quraanali.imageviewerexample.R
import com.quraanali.imageviewerexample.common.extensions.showShortToast
import com.quraanali.imageviewerexample.common.models.Demo
import com.quraanali.imageviewerexample.common.models.Poster
import com.quraanali.imageviewerexample.common.ui.base.BaseActivity
import com.quraanali.imageviewerexample.common.ui.views.PosterOverlayView
import com.quraanali.imageviewerexample.features.demo.styled.options.StylingOptions
import com.quraanali.imageviewerexample.features.demo.styled.options.StylingOptions.Property.*
import kotlinx.android.synthetic.main.activity_demo_styling.*

class StylingDemoActivity : BaseActivity() {

    private var options = StylingOptions()
    private var overlayView: PosterOverlayView? = null
    private var viewer: QuraanaliImageViewer<Poster>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_styling)

        stylingPostersGridView.apply {
            imageLoader = ::loadPosterImage
            onPosterClick = ::openViewer
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.styling_options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        options.showDialog(this)
        return super.onOptionsItemSelected(item)
    }

    private fun openViewer(startPosition: Int, imageView: ImageView) {
        val posters = Demo.posters.toMutableList()

        val builder = QuraanaliImageViewer.Builder<Poster>(this, posters, ::loadPosterImage)
            .withStartPosition(startPosition)
            .withImageChangeListener { position ->
                if (options.isPropertyEnabled(SHOW_TRANSITION)) {
                    viewer?.updateTransitionImage(stylingPostersGridView.imageViews[position])
                }

                overlayView?.update(posters[position])
            }
            .withDismissListener { showShortToast(R.string.message_on_dismiss) }

        builder.withHiddenStatusBar(options.isPropertyEnabled(HIDE_STATUS_BAR))

        if (options.isPropertyEnabled(IMAGES_MARGIN)) {
            builder.withImagesMargin(R.dimen.image_margin)
        }

        if (options.isPropertyEnabled(CONTAINER_PADDING)) {
            builder.withContainerPadding(R.dimen.image_margin)
        }

        if (options.isPropertyEnabled(SHOW_TRANSITION)) {
            builder.withTransitionFrom(imageView)
        }

        builder.allowSwipeToDismiss(options.isPropertyEnabled(SWIPE_TO_DISMISS))
        builder.allowZooming(options.isPropertyEnabled(ZOOMING))

        if (options.isPropertyEnabled(SHOW_OVERLAY)) {
            setupOverlayView(posters, startPosition)
            builder.withOverlayView(overlayView)
        }

        if (options.isPropertyEnabled(RANDOM_BACKGROUND)) {
            builder.withBackgroundColor(getRandomColor())
        }

        viewer = builder.show()
    }

    private fun setupOverlayView(posters: MutableList<Poster>, startPosition: Int) {
        overlayView = PosterOverlayView(this).apply {
            update(posters[startPosition])

            onDeleteClick = {
                val currentPosition = viewer?.currentPosition() ?: 0

                posters.removeAt(currentPosition)
                viewer?.updateImages(posters)

                posters.getOrNull(currentPosition)
                    ?.let { poster -> update(poster) }
            }
        }
    }

    private fun getRandomColor(): Int {
        val random = java.util.Random()
        return android.graphics.Color.argb(
            255,
            random.nextInt(156),
            random.nextInt(156),
            random.nextInt(156)
        )
    }
}