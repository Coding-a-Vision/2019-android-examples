package com.migreat.giphy

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_giphy.*

class GiphyActivity : AppCompatActivity() {

    private lateinit var viewModel: GiphyViewModel

    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_giphy)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this)[GiphyViewModel::class.java]
        setupViews()
    }

    private fun setupViews() {
        progress = findViewById(R.id.progress)
    }

    override fun onStart() {
        super.onStart()

        viewModel.state.observe(this, Observer {
            when (it) {
                is GiphyState.InProgress -> showProgress()
                is GiphyState.Error -> {
                    hideProgress()
                    TODO()
                }
                is GiphyState.Success -> {
                    hideProgress()
                    TODO()
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

}
