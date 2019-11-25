package com.migreat.giphy

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migreat.giphy.model.Gif
import kotlinx.coroutines.launch

sealed class GifDetailEvent {
    data class Load(val gifId: String) : GifDetailEvent()
}

sealed class GifDetailState {
    object InProgress : GifDetailState()
    data class Error(val error: Throwable) : GifDetailState()
    data class Success(val gifDetail: Gif) : GifDetailState()
}

class GifDetailViewModel : ViewModel() {

    private val giphyService = GiphyService()

    var state: MutableLiveData<GifDetailState> = MutableLiveData()

    fun send(event: GifDetailEvent) {
        when (event) {
            is GifDetailEvent.Load -> loadContent(event.gifId)
        }
    }

    private fun loadContent(gifId: String) {
        state.value = GifDetailState.InProgress

        viewModelScope.launch {
            try {
                val gifDetail = giphyService.gifDetail(gifId)
                Log.d("GifDetailViewModel", "result: $gifDetail")
                state.value = GifDetailState.Success(gifDetail = gifDetail)
            } catch (exception: Throwable) {
                state.value = GifDetailState.Error(exception)
            }
        }
    }
}
