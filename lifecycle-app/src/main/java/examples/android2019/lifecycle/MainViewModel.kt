package examples.android2019.lifecycle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class MainViewModel : ViewModel() {

    val text = MutableLiveData<String>()

    init {
        text.value = "Initialized viewmodel!"
    }

    fun addText(addedText: String) {
        Timber.d("viewmodel $this, adding text: $addedText" )
        text.value = text.value.plus("\n$addedText")
    }
}
