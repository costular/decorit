package com.costular.decorit.presentation.photodetail

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.FloatRange
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.costular.decorit.databinding.ActivityPhotoDetailBinding
import com.costular.decorit.presentation.photodetail.immersive.SystemUiHelper
import com.costular.decorit.util.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.flow.onStates
import io.uniflow.android.flow.onTakeEvents
import kotlinx.android.synthetic.main.activity_photo_detail.*
import me.saket.flick.ContentSizeProvider2
import me.saket.flick.FlickCallbacks
import me.saket.flick.FlickGestureListener
import me.saket.flick.InterceptResult
import java.lang.Math.abs

@AndroidEntryPoint
class PhotoDetailActivity : AppCompatActivity() {

    private val viewModel: PhotoDetailViewModel by viewModels()
    private val args: PhotoDetailActivityArgs by navArgs()
    private val binding by viewBinding(ActivityPhotoDetailBinding::inflate)

    private lateinit var systemUiHelper: SystemUiHelper
    private lateinit var activityBackgroundDrawable: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        super.onCreate(savedInstanceState)
        delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
        overridePendingTransition(0, 0)
        setContentView(binding.root)
        listen()

        animateDimmingOnEntry()
        viewModel.loadPhoto(args.photo)

        binding.imageLayoutDismissable.gestureListener = flickGestureListener()

        systemUiHelper =
            SystemUiHelper(
                this,
                SystemUiHelper.LEVEL_IMMERSIVE,
                0,
                null
            ).also { it.hide() }
        binding.imageDetail.setOnClickListener { systemUiHelper.toggle() }
    }

    fun listen() {
        listenActions()
        listenEvents()
        listenState()
    }

    private fun listenActions() {
        binding.fabClose.setOnClickListener { animateExit { finish() } }
    }

    private fun listenEvents() {
        onTakeEvents(viewModel) { event ->
            when (event) {
                is PhotoDetailEvents.LoadPhoto -> loadPhoto(event.url)
            }
        }
    }

    private fun listenState() {
        onStates(viewModel) { state ->
            when (state) {
                is PhotoDetailState -> handleState(state)
            }
        }
    }

    private fun handleState(state: PhotoDetailState) {
        binding.progressLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.photo?.let { photo ->
            binding.textAuthorName.text = photo.photographer.name
            binding.textSource.text = photo.sourceId

            Glide.with(binding.imageAuthorAvatar)
                .load(photo.photographer.avatar)
                .circleCrop()
                .into(binding.imageAuthorAvatar)
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    override fun onBackPressed() {
        animateExit {
            super.onBackPressed()
        }
    }

    private fun finishInMillis(millis: Long) {
        binding.root.postDelayed({ finish() }, millis)
    }

    private fun loadPhoto(url: String) {
        Glide.with(imageDetail)
            .load(url)
            .thumbnail(0.1f)
            .listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Failed
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewModel.photoLoadedSuccessfully()
                    return false
                }

            })
            .into(imageDetail)
    }

    private fun flickGestureListener(): FlickGestureListener {
        val callbacks = FlickCallbacks(
            onMove = { moveRatio -> updateBackgroundDimmingAlpha(abs(moveRatio)) },
            onFlickDismiss = { flickAnimDuration -> finishInMillis(flickAnimDuration) }
        )

        val contentSizeProvider = ContentSizeProvider2 {
            // A non-zero height is important so that the user can dismiss even
            // the image is unavailable and the progress indicator is visible.
            maxOf(dip(240), binding.imageDetail.zoomedImageHeight.toInt())
        }

        val gestureListener = FlickGestureListener(this, contentSizeProvider, callbacks, false)

        // Block flick gestures if the image can pan further.
        gestureListener.gestureInterceptor = { scrollY ->
            val isScrollingUpwards = scrollY < 0
            val directionInt = if (isScrollingUpwards) -1 else +1
            val canPanFurther = binding.imageDetail.canScrollVertically(directionInt)

            when {
                canPanFurther -> InterceptResult.INTERCEPTED
                else -> InterceptResult.IGNORED
            }
        }

        return gestureListener
    }

    private fun animateDimmingOnEntry() {
        activityBackgroundDrawable = binding.root.background.mutate()
        binding.root.background = activityBackgroundDrawable

        ObjectAnimator.ofFloat(1F, 0f).apply {
            duration = 200
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener { animation ->
                updateBackgroundDimmingAlpha(animation.animatedValue as Float)
            }
            start()
        }
    }

    private fun animateExit(onEndAction: () -> Unit) {
        val animDuration: Long = 200
        binding.imageLayoutDismissable.animate()
            .alpha(0f)
            .translationY(binding.imageLayoutDismissable.height / 20F)
            .rotation(-2F)
            .setDuration(animDuration)
            .setInterpolator(FastOutSlowInInterpolator())
            .withEndAction(onEndAction)
            .start()

        ObjectAnimator.ofFloat(0F, 1F).apply {
            duration = animDuration
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener { animation ->
                updateBackgroundDimmingAlpha(animation.animatedValue as Float)
            }
            start()
        }
    }

    private fun updateBackgroundDimmingAlpha(
        @FloatRange(
            from = 0.0,
            to = 1.0
        ) transparencyFactor: Float
    ) {
        // Increase dimming exponentially so that the background is
        // fully transparent while the image has been moved by half.
        val dimming = 1f - 1f.coerceAtMost(transparencyFactor * 2)
        activityBackgroundDrawable.alpha = (dimming * 255).toInt()
    }
}

private fun Context.dip(units: Int): Int {
    return TypedValue.applyDimension(
        COMPLEX_UNIT_DIP,
        units.toFloat(),
        resources.displayMetrics
    ).toInt()
}
