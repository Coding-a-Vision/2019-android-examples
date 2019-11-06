package examples.android2019.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val text = MutableLiveData<String>()

    init {
        text.value = "This is the main activity"
    }

    fun changeText(newText: String) {
        Log.i("TEST", "setting text to: $newText")
        text.value = newText
    }
}
