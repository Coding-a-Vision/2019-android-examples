package com.migreat.giphy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import com.migreat.giphy.model.Gif
import kotlinx.android.synthetic.main.activity_giphy.*

private const val BUNDLE_TAG_GIF_ID: String = "BUNDLE_TAG_GIF_ID"

class GifDetailActivity : AppCompatActivity() {

    companion object {
        fun openGifDetailActivity(startingActivity: Activity, gifId: String) {
            val intent = Intent(startingActivity, GifDetailActivity::class.java)
                .putExtra(BUNDLE_TAG_GIF_ID, gifId)

            startingActivity.startActivity(intent)
        }
    }

    private lateinit var viewModel: GifDetailViewModel

    private lateinit var baseLayout: RelativeLayout

    private lateinit var progress: ProgressBar

    private lateinit var gifView: ImageView

    private lateinit var gifId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifdetail)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this)[GifDetailViewModel::class.java]
        setupViews()

        gifId = intent.getStringExtra(BUNDLE_TAG_GIF_ID)
    }

    private fun setupViews() {
        baseLayout = findViewById(R.id.base_layout)
        progress = findViewById(R.id.progress)
        gifView = findViewById(R.id.gif_detail_view)
    }

    override fun onStart() {
        super.onStart()

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is GifDetailState.InProgress -> showProgress()
                is GifDetailState.Error -> {
                    hideProgress()
                    showError(state.error)
                }
                is GifDetailState.Success -> {
                    hideProgress()
                    showGif(state.gifDetail)
                }
            }
        })

        viewModel.send(GifDetailEvent.Load(gifId))
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

    private fun showError(error: Throwable) {
        Log.d("GifDetailActivity", "showError: $error")

        Snackbar.make(baseLayout, "error: $error", LENGTH_INDEFINITE)
            .setAction("Retry") { viewModel.send(GifDetailEvent.Load(gifId)) }
            .show()
    }

    private fun showGif(gif: Gif) {
        Log.d("GifDetailActivity", "showGif: $gif")

        Glide
            .with(this)
            .load(gif.original.url)
            .fitCenter()
            .into(gifView)
    }

}
