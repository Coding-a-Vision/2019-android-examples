package examples.android2019.network

import androidx.lifecycle.viewModelScope
import examples.android2019.network.base.BaseViewModel
import examples.android2019.network.domain.Gif
import examples.android2019.network.network.GiphyClient
import examples.android2019.network.util.exhaustive
import kotlinx.coroutines.launch

sealed class PageEvent {

    object Load : PageEvent()
}

sealed class PageState {

    object LoadingPage : PageState()

    data class PageLoaded(val page: List<Gif>) : PageState()

    data class Error(val error: Throwable) : PageState()
}

class MainViewModel : BaseViewModel<PageState, PageEvent>() {

    var client: GiphyClient? = null

    init {
        pageState.value = PageState.LoadingPage
    }

    override fun send(event: PageEvent) {
        when (event) {
            PageEvent.Load -> loadTrendingGifs()
        }.exhaustive
    }

    private fun loadTrendingGifs() {
        pageState.postValue(PageState.LoadingPage)

        //TODO should do general error handling!
        //TODO should manage running requests
        client?.let {
            viewModelScope.launch {
                try {
                    val gifsCall = client?.getTrending()
                    val gifs = gifsCall?.body() as List<Gif>
                    pageState.postValue(PageState.PageLoaded(gifs))
                } catch (e: Exception) {
                    pageState.postValue(PageState.Error(e))
                }
            }
        }
    }

}
