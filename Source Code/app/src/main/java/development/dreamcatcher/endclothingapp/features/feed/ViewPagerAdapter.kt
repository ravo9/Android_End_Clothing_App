package development.dreamcatcher.endclothingapp.features.feed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import development.dreamcatcher.endclothingapp.R
import development.dreamcatcher.endclothingapp.data.database.ItemEntity
import kotlinx.android.synthetic.main.items_viewpager_item.view.*
import kotlinx.android.synthetic.main.items_viewpager_item.view.textView_name

// Internal adapter providing functionality of viewpagers nested within main items list (recycler view)
class ViewPagerAdapter(val clickListener: (String) -> Unit, list: List<ItemEntity>?, private val context: Context) : PagerAdapter() {

    private val inflater: LayoutInflater
    private val itemsList = ArrayList<ItemEntity>()

    init {
        if (list != null && list.isNotEmpty()) {
            itemsList.addAll(list)
        }
        this.inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val rootView = inflater.inflate(R.layout.items_viewpager_item, container, false)
        val holder = ViewHolder(rootView)

        // Prepare fetched data
        val name = itemsList[position].name
        val image = itemsList[position].image

        // Set data within the holder
        holder.name.text = name

        // Load thumbnail
        Picasso.with(context).load(image).into(holder.thumbnail)

        // Set onClickListener
        /*rootView.setOnClickListener{
            val itemId = itemsList[position].id
            clickListener(itemId)
        }*/

        // Insert items view into the ViewPager
        container.addView(rootView);

        return rootView
    }

    override fun getCount(): Int {
        return itemsList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    internal inner class ViewHolder(view: View) {
        val name = view.textView_name
        val thumbnail = view.thumbnail
    }
}