package examples.android2019.network

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import examples.android2019.network.ui.MainAdapter
import examples.android2019.network.util.exhaustive

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var gifListView: RecyclerView

    private lateinit var pullToRefreshAction: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_main)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setupViews()
        setupObserver()
    }

    private fun setupViews() {
        gifListView = findViewById(R.id.gif_list)
        gifListView.adapter = MainAdapter()
        gifListView.layoutManager = GridLayoutManager(this, 2)

        pullToRefreshAction = findViewById(R.id.pullToRefresh)
        pullToRefreshAction.setOnRefreshListener { mainViewModel.send(PageEvent.Load) }
    }

    private fun setupObserver() {
        mainViewModel.observe(this) { pageState ->
            when (pageState) {
                is PageState.LoadingPage -> pullToRefreshAction.isRefreshing = true
                is PageState.PageLoaded -> {
                    pullToRefreshAction.isRefreshing = true
                    (gifListView.adapter as MainAdapter).submitList(pageState.page)
                }
                is PageState.Error -> {
                    pullToRefreshAction.isRefreshing = true
                    Toast.makeText(this, "error loading gifs!", Toast.LENGTH_SHORT).show()
                }
            }.exhaustive
        }
    }

}
