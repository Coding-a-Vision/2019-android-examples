package com.migreat.network

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import com.migreat.network.model.Gif
import kotlinx.android.synthetic.main.activity_giphy.*

class GiphyActivity : AppCompatActivity() {

    private lateinit var viewModel: GiphyViewModel

    private lateinit var baseLayout: CoordinatorLayout

    private lateinit var progress: ProgressBar

    private lateinit var gifsRV: RecyclerView

    private lateinit var gifsAdapter: ListAdapter<Gif, GifsViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_giphy)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this)[GiphyViewModel::class.java]
        setupViews()
    }

    private fun setupViews() {
        baseLayout = findViewById(R.id.base_layout)
        progress = findViewById(R.id.progress)

        gifsRV = findViewById(R.id.gifs_list)
        gifsRV.layoutManager = GridLayoutManager(this, 2)
        gifsAdapter = GifsAdapter()
        gifsRV.adapter = gifsAdapter
    }

    override fun onStart() {
        super.onStart()

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is GiphyState.InProgress -> showProgress()
                is GiphyState.Error -> {
                    hideProgress()
                    showError(state.error)
                }
                is GiphyState.Success -> {
                    hideProgress()
                    showGifs(state.gifs)
                }
            }
        })

        viewModel.send(GiphyEvent.Load)
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

    private fun showError(error: Throwable) {
        Log.d("GiphyActivity", "showError")

        Snackbar.make(baseLayout, "error: $error", LENGTH_INDEFINITE)
            .setAction("Retry", View.OnClickListener {
                viewModel.send(GiphyEvent.Load)
            })
            .show()
    }

    private fun showGifs(gifs: List<Gif>) {
        Log.d("GiphyActivity", "showGifs: $gifs")

        gifsAdapter.submitList(gifs)
    }

}
