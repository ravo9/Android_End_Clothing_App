package development.dreamcatcher.endclothingapp.features.feed

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import development.dreamcatcher.endclothingapp.R
import development.dreamcatcher.endclothingapp.data.database.ItemEntity
import kotlinx.android.synthetic.main.grid_single_item.view.*
import java.lang.Exception

// Main adapter used for managing items grid within the main Feed View
class ItemsGridAdapter (val context: Context) : BaseAdapter() {

    private var itemsList: List<ItemEntity> = ArrayList()

    fun setItems(items: List<ItemEntity>) {
        this.itemsList = items
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return itemsList.size
    }

    override fun getItem(position: Int): Any {
        return itemsList[position]
    }

    override fun getItemId(position: Int): Long {
        return itemsList[position].id!!.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        // Inflate the view
        val item = this.itemsList[position]
        val inflater = LayoutInflater.from(parent?.context)
        val itemView = inflater.inflate(R.layout.grid_single_item, null)

        // Prepare data values
        val name = item.name
        val price = item.price
        var color = item.color
        if (color == null) color = "Default Color"

        // Set data within the view
        itemView.name.text = name
        itemView.price.text = price
        itemView.color.text = color

        // Load thumbnail
        val imageUrl = item.image
        val thumbnail = itemView.thumbnail

        try {
            Glide.with(context).load(imageUrl).into(thumbnail);
        }
        catch (e: Exception) {
            Log.e("Exception", e.message);
        }

        return itemView
    }
}