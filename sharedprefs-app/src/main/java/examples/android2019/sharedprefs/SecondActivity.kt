package examples.android2019.sharedprefs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import examples.android2019.passingdataapp.R
import kotlin.random.Random

const val SHARED_PREFS_SECOND_NAME = "SecondActivity_prefs"

const val SHARED_PREFS_SECOND_DATA_TAG = "custom_object"

data class CustomObject(
    val text: String,
    val number: Int,
    val list: List<String> = listOf("one, two")
)

class SecondActivity : AppCompatActivity() {

    companion object {
        fun openSecondActivity(startingActivity: Activity) {
            val intent = Intent(startingActivity, SecondActivity::class.java)
            startingActivity.startActivity(intent)
        }
    }

    private lateinit var sharedPrefs: SharedPreferences
    private val gson = setupSerializer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        sharedPrefs = getSharedPreferences(SHARED_PREFS_SECOND_NAME, Context.MODE_PRIVATE)

        setupViews()
    }

    private fun setupSerializer(): Gson =
        GsonBuilder()
            .create()

    private fun setupViews() {
        val showDataView: TextView = findViewById(R.id.text_object)
        val showJsonView: TextView = findViewById(R.id.text_json)
        val saveDataAction: Button = findViewById(R.id.action_save_data)
        val deleteDataAction: Button = findViewById(R.id.action_delete_data)

        refreshDataActions(
            sharedPrefs,
            showDataView,
            showJsonView,
            saveDataAction,
            deleteDataAction
        )

        saveDataAction.setOnClickListener {
            val newCustomObject = CustomObject("some text", Random.nextInt())
            val stringified = gson.toJson(newCustomObject)
            Log.d("TEST", "SAVED stringified: $stringified, custom object: $newCustomObject")

            sharedPrefs.edit().putString(SHARED_PREFS_SECOND_DATA_TAG, stringified).apply()
            refreshDataActions(
                sharedPrefs, showDataView,
                showJsonView, saveDataAction, deleteDataAction
            )
        }
        deleteDataAction.setOnClickListener {
            sharedPrefs.edit().remove(SHARED_PREFS_SECOND_DATA_TAG).apply()
            refreshDataActions(
                sharedPrefs, showDataView,
                showJsonView, saveDataAction, deleteDataAction
            )
        }
    }

    private fun refreshDataActions(
        prefs: SharedPreferences,
        dataView: TextView,
        jsonView: TextView,
        saveDataAction: Button,
        deleteDataAction: Button
    ) {
        if (prefs.contains(SHARED_PREFS_SECOND_DATA_TAG)) {
            val stringified = prefs.getString(SHARED_PREFS_SECOND_DATA_TAG, "")
            val customObject = gson.fromJson(stringified, CustomObject::class.java)

            Log.d("TEST", "READ stringified: $stringified, custom object: $customObject")

            jsonView.text = "JSON = text: $stringified"
            jsonView.visibility = View.VISIBLE
            dataView.text =
                "Custom Object = text: ${customObject.text}, number: ${customObject.number}, list: ${customObject.list}"
            dataView.visibility = View.VISIBLE
            deleteDataAction.visibility = View.VISIBLE
            saveDataAction.visibility = View.GONE
        } else {
            jsonView.visibility = View.GONE
            dataView.visibility = View.GONE
            deleteDataAction.visibility = View.GONE
            saveDataAction.visibility = View.VISIBLE
        }
    }
}
