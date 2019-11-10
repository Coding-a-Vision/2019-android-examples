package examples.android2019.recyclerview

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import examples.android2019.recyclerviewapp.R
import kotlin.random.Random

class DynamicListActivity : AppCompatActivity() {

    private val categoryList = listOf(
        Category("first"),
        Category("2nd category"),
        Category("third"),
        Category("3th cat")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic)

        val listView: RecyclerView = findViewById(R.id.categories_list)
        listView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = DynamicCategoryAdapter()
        listView.adapter = adapter
        adapter.submitList(categoryList)

        val addToListAction: Button = findViewById(R.id.add_to_list_action)
        addToListAction.setOnClickListener {
            val randomCategory = Category(Random.nextInt().toString())
            adapter.submitList(adapter.currentList.plus(randomCategory))
        }
    }
}
