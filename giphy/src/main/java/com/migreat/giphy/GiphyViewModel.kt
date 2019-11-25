package com.migreat.giphy

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class GiphyEvent {
    object Load : GiphyEvent()
}

sealed class GiphyState {
    object InProgress : GiphyState()
    data class Error(val error: Throwable) : GiphyState()
    object Success : GiphyState()
}

class GiphyViewModel : ViewModel() {

    var state: MutableLiveData<GiphyState> = MutableLiveData()

    init {
        state.value = GiphyState.InProgress
    }

    fun send(event: GiphyEvent) {
        when (event) {
            GiphyEvent.Load -> loadContent()
        }
    }

    private fun loadContent() {
        Log.d("GiphyViewModel", "loadContent")
    }

}
