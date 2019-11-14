package examples.android2019.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import examples.android2019.viewmodelapp.R

class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO this viewmodel will NOT be shared between activity and fragment!
//        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        //TODO this viewmodel will be shared between activity and fragment!
        mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)

        mainViewModel.addText("Fragment.onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        val textLog = root.findViewById<TextView>(R.id.text_log)
        mainViewModel.text.observe(this, Observer { text ->
            textLog.text = text
        })

        mainViewModel.addText("Fragment.onCreateView()")

        return root
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.addText("Fragment.onStart()")
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.addText("Fragment.onResume()")
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.addText("Fragment.onPause()")
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.addText("Fragment.onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainViewModel.addText("Fragment.onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.addText("Fragment.onDestroy()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mainViewModel.addText("Fragment.onSaveInstanceState()")
    }

}
