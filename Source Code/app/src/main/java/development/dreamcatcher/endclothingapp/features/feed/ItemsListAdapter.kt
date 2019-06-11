package development.dreamcatcher.endclothingapp.features.feed

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import development.dreamcatcher.endclothingapp.R
import development.dreamcatcher.endclothingapp.data.database.ItemEntity
import kotlinx.android.synthetic.main.items_column_triplet.view.*
import kotlinx.android.synthetic.main.articles_list_row.view.*
import java.lang.Exception
import kotlinx.android.synthetic.main.items_viewpager_triplet.view.*

// Main adapter used for managing items list within the main Recycler (List) View
class ItemsListAdapter (val clickListener: (String) -> Unit) : RecyclerView.Adapter<ItemsListAdapter.ViewHolder>() {

    private var itemsList: List<List<ItemEntity>> = ArrayList()
    private var context: Context? = null

    companion object {
        internal val VIEW_TYPE_COLUMN = 0
        internal val VIEW_TYPE_VIEWPAGER = 1
    }

    fun setItems(items: List<ItemEntity>) {
        this.itemsList = convertItemsListIntoTripletsList(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun getItemViewType(position: Int): Int {
        // Return 0 or 1 per each position to indicate viewtype.
        return position % 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        var viewHolder: ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {

            VIEW_TYPE_COLUMN -> {
                val columnView = inflater
                    .inflate(R.layout.items_column_triplet, parent, false)
                viewHolder = ColumnViewHolder(columnView)
            }

            VIEW_TYPE_VIEWPAGER -> {
                val viewPagerView = inflater
                    .inflate(R.layout.items_viewpager_triplet, parent, false)
                viewHolder = ViewPagerViewHolder(viewPagerView)
            }
        }

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (holder.itemViewType) {

            VIEW_TYPE_COLUMN -> {
                val columnViewHolder = holder as ColumnViewHolder
                configureColumnTriplet(columnViewHolder, position)
            }

            VIEW_TYPE_VIEWPAGER -> {
                val viewPagerViewHolder = holder as ViewPagerViewHolder
                configureViewPagerTriplet(viewPagerViewHolder, position)
            }
        }

        /*// Set onClickListener
        holder.itemView.setOnClickListener{
            val itemId = itemsList[position].id
            clickListener(itemId)
        }*/
    }

    private fun configureColumnTriplet(holder: ColumnViewHolder, position: Int) {
        for ((index, item) in holder.itemsViews.withIndex()) {
            try {
                // Prepare fetched data
                val name = itemsList[position][index].name
                val price = itemsList[position][index].price
                val image = itemsList[position][index].image

                // Set data within the holder
                item.textView_name.text = name
                item.textView_price.text = price

                // Load thumbnail
                Picasso.with(context).load(image).into(item.thumbnail)

                // Set onClickListener
                /*item.setOnClickListener{
                    val itemId = itemsList[position][index].id
                    clickListener(itemId)
                }*/

            } catch (e: Exception) {
                Log.e("Exception", e.message)
            }
        }
    }

    private fun configureViewPagerTriplet(holder: ViewPagerViewHolder, position: Int) {

        // Prepare fetched data
        val itemsTriplet = itemsList[position]

        // Set adapter to the viewpager
        val adapter = ViewPagerAdapter(clickListener, itemsTriplet, context!!)
        holder.viewPager.setAdapter(adapter)
    }

    abstract class ViewHolder (view: View) : RecyclerView.ViewHolder(view)

    inner class ColumnViewHolder (view: View) : ViewHolder(view) {
        val itemsViews = listOf(view.article01, view.article02, view.article03)
    }

    inner class ViewPagerViewHolder (view: View) : ViewHolder(view) {
        val viewPager = view.viewpager_triplet_container
    }

    // Converter splitting items together into 3-items clusters
    private fun convertItemsListIntoTripletsList(itemsList: List<ItemEntity>)
            : List<List<ItemEntity>> {
        val tripletsList = ArrayList<List<ItemEntity>>()
        itemsList.chunked(3).forEach {
            tripletsList.add(it)
        }
        return tripletsList
    }
}