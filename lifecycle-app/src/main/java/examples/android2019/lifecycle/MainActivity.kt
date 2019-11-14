package examples.android2019.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import examples.android2019.viewmodelapp.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel =
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.main_fragment, MainFragment())
                .commit()
            mainViewModel.addText("first pass of Activity.onCreate()")
        } else {
            mainViewModel.addText("not a first pass of Activity.onCreate(), have saved state!")
        }
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.addText("Activity.onStart()")
    }

    override fun onRestart() {
        super.onRestart()
        mainViewModel.addText("Activity.onRestart()")
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.addText("Activity.onResume()")
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.addText("Activity.onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.addText("Activity.onDestroy()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mainViewModel.addText("Activity.onSaveInstanceState()")
    }

}
