package examples.android2019.viewmodel

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import examples.android2019.viewmodelapp.R
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)

        setupViews()
        setupObserver()
    }

    private fun setupViews() {
        textView = findViewById(R.id.text_main)

        val changeTextAction: Button = findViewById(R.id.action_change_text)
        changeTextAction.setOnClickListener {
            viewModel.send(PageEvent.ChangeText(Random.nextInt().toString()))
        }
    }

    private fun setupObserver() {
        viewModel.observe(this) { pageState ->
            when (pageState) {
                is PageState.TextChanged -> textView.text = pageState.text
            }.exhaustive
        }
    }
}
