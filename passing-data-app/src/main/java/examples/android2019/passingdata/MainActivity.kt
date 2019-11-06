package examples.android2019.passingdata

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import examples.android2019.passingdataapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openSecondAction: Button = findViewById(R.id.action_open_second)
        openSecondAction.setOnClickListener {
            SecondActivity.openSecondActivity(this, "fake name", 666)
        }
    }
}
