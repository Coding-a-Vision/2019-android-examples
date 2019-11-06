package examples.android2019.passingdata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import examples.android2019.passingdataapp.R

private const val ARGUMENTS_TAG_NAME: String = "ARGUMENTS_TAG_NAME"

private const val ARGUMENTS_TAG_NUMBER: String = "ARGUMENTS_TAG_NUMBER"

class SecondFragment : Fragment() {

    companion object {
        fun createInstance(name: String, number: Int): SecondFragment {
            val arguments = Bundle()
            arguments.putString(ARGUMENTS_TAG_NAME, name)
            arguments.putInt(ARGUMENTS_TAG_NUMBER, number)

            val fragment = SecondFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_second, container, false)

        val textName: TextView = root.findViewById(R.id.text_name)
        val textNumber: TextView = root.findViewById(R.id.text_number)

        val passedName = arguments?.getString(ARGUMENTS_TAG_NAME)
        val passedNumber = arguments?.getInt(ARGUMENTS_TAG_NUMBER)

        textName.text = passedName
        textNumber.text = passedNumber.toString()

        return root
    }
}
