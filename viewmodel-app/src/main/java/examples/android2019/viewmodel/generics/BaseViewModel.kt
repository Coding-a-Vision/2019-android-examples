package examples.android2019.viewmodel.generics

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<STATE, EVENT> : ViewModel() {

    protected open val state: MutableLiveData<STATE> = MutableLiveData()

    fun observe(owner: LifecycleOwner, observer: (STATE) -> Unit) {
        state.observe(owner, Observer { it?.let(observer::invoke) })
    }

    fun state() = state.value

    abstract fun send(event: EVENT)
}
