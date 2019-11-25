package com.migreat.giphy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.migreat.giphy.model.Gif

sealed class GiphyEvent {
    object Load : GiphyEvent()
}

sealed class GiphyState {
    object InProgress : GiphyState()
    data class Error(val error: Throwable) : GiphyState()
    data class Success(val gifs: List<Gif>) : GiphyState()
}

class GiphyViewModel : ViewModel() {

    private val giphyService = GiphyService()

    var state: MutableLiveData<GiphyState> = MutableLiveData()

    fun send(event: GiphyEvent) {
        when (event) {
            GiphyEvent.Load -> loadContent()
        }
    }

    private fun loadContent() {
        state.value = GiphyState.InProgress

        try {
            giphyService.loadTrending(object : GifResultReceiver {
                override fun receive(result: GifResult) {
                    when (result) {
                        is GifResult.Error -> state.value = GiphyState.Error(error = result.error)
                        is GifResult.Success -> state.value = GiphyState.Success(gifs = result.gifs)
                    }
                }

            })
        } catch (exception: Throwable) {
            state.value = GiphyState.Error(exception)
        }
    }
}
