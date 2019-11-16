package examples.android2019.network

import examples.android2019.network.base.BaseViewModel
import examples.android2019.network.domain.Gif
import examples.android2019.network.util.exhaustive

sealed class PageEvent {

    object Load : PageEvent()
}

sealed class PageState {

    object LoadingPage : PageState()

    data class PageLoaded(val page: List<Gif>) : PageState()

    data class Error(val error: Throwable) : PageState()
}

class MainViewModel : BaseViewModel<PageState, PageEvent>() {

    init {
        pageState.value = PageState.LoadingPage
    }

    override fun send(event: PageEvent) {
        when (event) {
            PageEvent.Load -> TODO()
        }.exhaustive
    }

}
