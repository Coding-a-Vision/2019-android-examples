package examples.android2019.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import examples.android2019.recyclerviewapp.R

class SimpleCategoryAdapter(private val categoryList: List<Category>) :
    RecyclerView.Adapter<SimpleCategoryAdapter.CategoryViewHolder>() {

    override fun getItemCount(): Int = categoryList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem, parent, false)
        return CategoryViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        category.let {
            holder.categoryName.text = it.name
            holder.categoryName.setOnClickListener {
                Toast.makeText(
                    holder.itemView.context,
                    "clicked category: ${holder.categoryName.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val categoryName = itemView.findViewById<View>(R.id.category_name) as TextView

    }
}
