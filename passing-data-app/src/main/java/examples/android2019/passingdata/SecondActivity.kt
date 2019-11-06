package examples.android2019.passingdata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import examples.android2019.passingdataapp.R

private const val BUNDLE_TAG_NAME: String = "BUNDLE_TAG_NAME"

private const val BUNDLE_TAG_NUMBER: String = "BUNDLE_TAG_NUMBER"

class SecondActivity : AppCompatActivity() {

    companion object {
        fun openSecondActivity(startingActivity: Activity, name: String, number: Int) {
            val intent = Intent(startingActivity, SecondActivity::class.java)
                .putExtra(BUNDLE_TAG_NAME, name)
                .putExtra(BUNDLE_TAG_NUMBER, number)

            startingActivity.startActivity(intent)
        }
    }

    var passedName: String? = null

    var passedNumber: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        passedName = intent.getStringExtra(BUNDLE_TAG_NAME)
        passedNumber = intent.getIntExtra(BUNDLE_TAG_NUMBER, -1)

        //this will crash if the arguments are null!!!
        val secondFragment = SecondFragment.createInstance(passedName!!, passedNumber!!)
        supportFragmentManager.beginTransaction().replace(R.id.second_fragment, secondFragment)
            .commit()
    }
}
