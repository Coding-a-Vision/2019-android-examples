package examples.android2019.recyclerview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import examples.android2019.recyclerviewapp.R

class MainActivity : AppCompatActivity() {

    private val categoryList = listOf(
        Category("first"),
        Category("2nd category"),
        Category("third"),
        Category("3th cat")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val verticalLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val horizontalLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        val listView: RecyclerView = findViewById(R.id.categories_list)
        listView.layoutManager = verticalLayoutManager
        listView.adapter = SimpleCategoryAdapter(categoryList)

        val changeOrientationAction: Button = findViewById(R.id.add_to_list_action)
        changeOrientationAction.setOnClickListener {
            if (listView.layoutManager == verticalLayoutManager) {
                listView.layoutManager = horizontalLayoutManager
            } else {
                listView.layoutManager = verticalLayoutManager
            }
        }

        val openDynamicAction: Button = findViewById(R.id.action_open_dynamic)
        openDynamicAction.setOnClickListener {
            startActivity(Intent(this, DynamicListActivity::class.java))
        }
    }
}
