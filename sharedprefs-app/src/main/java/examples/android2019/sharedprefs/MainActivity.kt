package examples.android2019.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import examples.android2019.passingdataapp.R

const val SHARED_PREFS_MAIN_NAME = "MainActivity_prefs"

const val SHARED_PREFS_MAIN_DATA_TAG = "main_text"

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPrefs = getSharedPreferences(SHARED_PREFS_MAIN_NAME, Context.MODE_PRIVATE)

        setupViews()
    }

    private fun setupViews() {
        val showDataView: TextView = findViewById(R.id.text_main)
        val saveDataAction: Button = findViewById(R.id.action_save_data)
        val deleteDataAction: Button = findViewById(R.id.action_delete_data)

        refreshDataActions(sharedPrefs, showDataView, saveDataAction, deleteDataAction)

        saveDataAction.setOnClickListener {
            sharedPrefs.edit().putString(SHARED_PREFS_MAIN_DATA_TAG, "test!").apply()
            refreshDataActions(sharedPrefs, showDataView, saveDataAction, deleteDataAction)
        }
        deleteDataAction.setOnClickListener {
            sharedPrefs.edit().remove(SHARED_PREFS_MAIN_DATA_TAG).apply()
            refreshDataActions(sharedPrefs, showDataView, saveDataAction, deleteDataAction)
        }

        val openSecondAction: Button = findViewById(R.id.action_open_second)
        openSecondAction.setOnClickListener {
            SecondActivity.openSecondActivity(this)
        }
    }

    private fun refreshDataActions(
        prefs: SharedPreferences,
        dataView: TextView,
        saveDataAction: Button,
        deleteDataAction: Button
    ) {
        if (prefs.contains(SHARED_PREFS_MAIN_DATA_TAG)) {
            dataView.text = prefs.getString(SHARED_PREFS_MAIN_DATA_TAG, "")
            dataView.visibility = View.VISIBLE
            deleteDataAction.visibility = View.VISIBLE
            saveDataAction.visibility = View.GONE
        } else {
            dataView.visibility = View.GONE
            deleteDataAction.visibility = View.GONE
            saveDataAction.visibility = View.VISIBLE
        }
    }
}
