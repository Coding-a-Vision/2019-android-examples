package examples.android2019.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

sealed class PageEvent {

    data class ChangeText(val text: String) : PageEvent()
}

sealed class PageState {

    data class TextChanged(val text: String) : PageState()

}

class MainViewModel : ViewModel() {

    private val pageState = MutableLiveData<PageState>()

    init {
        pageState.value = PageState.TextChanged("This is main activity")
    }

    fun observe(owner: LifecycleOwner, observer: (PageState) -> Unit) {
        pageState.observe(owner, Observer { it?.let(observer::invoke) })
    }

    fun send(event: PageEvent) {
        when (event) {
            is PageEvent.ChangeText -> pageState.value = PageState.TextChanged(event.text)
        }.exhaustive
    }
}
