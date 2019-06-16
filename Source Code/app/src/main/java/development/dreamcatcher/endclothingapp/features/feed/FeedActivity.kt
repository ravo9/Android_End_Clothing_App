package development.dreamcatcher.endclothingapp.features.feed

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import development.dreamcatcher.endclothingapp.R
import development.dreamcatcher.endclothingapp.data.database.ItemEntity
import development.dreamcatcher.endclothingapp.injection.EndClothingApp
import development.dreamcatcher.endclothingapp.utils.Converters
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.side_drawer_layout.*
import kotlinx.android.synthetic.main.top_interface_bar.*
import javax.inject.Inject

// Main items (products) feed) view
class FeedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FeedViewModel
    private lateinit var itemsGridAdapter: ItemsGridAdapter

    init {
        EndClothingApp.feedComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)

        // Initialize Side Drawer
        setupSideDrawer()

        // Initialize Spinners
        setupSortSpinner()
        setupViewSpinner()

        // Initialize RecyclerView (items/ products list)
        setupRecyclerView()

        // Fetch items (products) from the back-end and load them into the view
        subscribeForItems()

        // Catch and handle potential network issues
        subscribeForNetworkError()
    }

    private fun setupSideDrawer() {

        // Initialize Side Drawer layout
        val drawerListener = ActionBarDrawerToggle(this, main_layout_container, R.string.sort, R.string.filter)
        main_layout_container.addDrawerListener(drawerListener)
        drawerListener.syncState()

        // Set Filter button onClick listener
        textView_filter.setOnClickListener{
            main_layout_container.openDrawer(GravityCompat.END)
        }

        // Set (Side Drawer's) Close button onClick listener
        close_btn.setOnClickListener{
            main_layout_container.closeDrawer(GravityCompat.END)
        }
    }

    private fun setupRecyclerView() {
        itemsGridAdapter = ItemsGridAdapter(this)
        gridView_products.adapter = itemsGridAdapter
    }

    private fun subscribeForItems() {
        viewModel.getAllItems()?.observe(this, Observer<List<ItemEntity>> {

            // Display fetched items
            if (!it.isNullOrEmpty()) {
                itemsGridAdapter.setItems(it)
            }
        })
    }

    private fun subscribeForNetworkError() {
        viewModel.getNetworkError()?.observe(this, Observer<Boolean> {

            // Display the "Connection problem" dialogbox
            displayConnectionProblemDialogbox()
        })
    }

    private fun refreshItemsSubscription() {

        // Reset items subscription (if no items have been cached as far)
        if (itemsGridAdapter.count == 0) {
            viewModel.getAllItems()?.removeObservers(this)
            subscribeForItems()
        }
    }

    private fun displayConnectionProblemDialogbox() {
        val builder = AlertDialog.Builder(this)

        builder.setMessage(R.string.there_is_a_problem_connecting)
            .setTitle(R.string.connection_problem)

        builder.setPositiveButton(R.string.ok) { _, _ ->
            refreshItemsSubscription()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun setupSortSpinner() {

        // Prepare spinner options
        val options = arrayOf("Latest", "Price (High)", "Price (Low)")

        // Initialize spinner adapter
        ArrayAdapter<String>(this, R.layout.spinner_item, options)
            .also { adapter ->

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

                // Apply the adapter to the spinner
                spinner_sort.adapter = adapter

                // Set the spinner prompt
                //spinner_sort_prompt.text = getString(R.string.sort)

                // Set the dropdown layout width (cannot be set in xml in this way)
                val dropdownLayoutWidth = Converters.convertPxToDp(resources.displayMetrics.widthPixels / 3, this)
                spinner_sort.dropDownWidth = dropdownLayoutWidth
        }
    }

    private fun setupViewSpinner() {

        // Prepare spinner options
        val options = arrayOf("Product", "Outfit", "Large", "Small")

        // Initialize spinner adapter
        ArrayAdapter<String>(this, R.layout.spinner_item, options)
            .also { adapter ->

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

                // Apply the adapter to the spinner
                spinner_view.adapter = adapter

                // Set the spinner prompt
                spinner_view_prompt.text = getString(R.string.view)

                // Set the dropdown layout width (cannot be set in xml in this way)
                val dropdownLayoutWidth = Converters.convertPxToDp(resources.displayMetrics.widthPixels / 3, this)
                spinner_view.dropDownWidth = dropdownLayoutWidth
            }
    }
}