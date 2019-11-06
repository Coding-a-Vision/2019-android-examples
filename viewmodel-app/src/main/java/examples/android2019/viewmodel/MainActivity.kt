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

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel =
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.text_main)
        mainViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val changeTextAction: Button = findViewById(R.id.action_change_text)
        changeTextAction.setOnClickListener {
            mainViewModel.changeText(Random.nextInt().toString())
        }
    }
}
