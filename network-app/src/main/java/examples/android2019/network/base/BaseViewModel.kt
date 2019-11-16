package examples.android2019.network.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<STATE, EVENT> : ViewModel() {

    protected open val pageState: MutableLiveData<STATE> = MutableLiveData()

    fun observe(owner: LifecycleOwner, observer: (STATE) -> Unit) {
        pageState.observe(owner, Observer { it?.let(observer::invoke) })
    }

    fun state() = pageState.value

    abstract fun send(event: EVENT)
}
