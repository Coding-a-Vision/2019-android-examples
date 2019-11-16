package examples.android2019.viewmodel

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import examples.android2019.viewmodelapp.R
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.text_main)
        viewModel.text.observe(this, Observer {
            textView.text = it
        })

        val changeTextAction: Button = findViewById(R.id.action_change_text)
        changeTextAction.setOnClickListener {
            viewModel.changeText(Random.nextInt().toString())
        }

        val openSecondAction: Button = findViewById(R.id.action_open_second)
        openSecondAction.setOnClickListener {
            SecondActivity.openSecondActivity(this)
        }
    }
}
