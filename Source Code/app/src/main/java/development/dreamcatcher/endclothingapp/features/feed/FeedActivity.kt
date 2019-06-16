package development.dreamcatcher.endclothingapp.features.feed

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import development.dreamcatcher.endclothingapp.R
import development.dreamcatcher.endclothingapp.data.database.ItemEntity
import development.dreamcatcher.endclothingapp.injection.EndClothingApp
import development.dreamcatcher.endclothingapp.utils.Converters
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.top_interface_bar.*
import javax.inject.Inject
import android.view.ViewTreeObserver




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

    private fun setupRecyclerView() {
        itemsGridAdapter = ItemsGridAdapter(this)
        gridView_products.adapter = itemsGridAdapter
    }

    private fun subscribeForItems() {
        viewModel.getAllItems()?.observe(this, Observer<List<ItemEntity>> {

            if (!it.isNullOrEmpty()) {

                // Display fetched items
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
        ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options)
            .also { adapter ->

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                // Apply the adapter to the spinner
                spinner_sort.adapter = adapter

                // Set the spinner prompt
                spinner_sort_prompt.text = getString(R.string.sort)

                // Set the dropdown layout width (cannot be set in xml in this way)
                val dropdownLayoutWidth = Converters.convertPxToDp(resources.displayMetrics.widthPixels / 3, this)
                spinner_sort.dropDownWidth = dropdownLayoutWidth


                // Set the dropdown layout offset (cannot be set in xml in this way)

                spinner_sort.getViewTreeObserver().addOnGlobalLayoutListener(
                    object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            // Layout has happened here.

                            val horizontalOffset = -(spinner_sort.x - spinner_sort_container.x).toInt()
                            spinner_sort.dropDownHorizontalOffset = 500
                            spinner_sort.refreshDrawableState()

                            // Don't forget to remove your listener when you are done with it.
                            spinner_sort.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                        }
                    })


        }
    }

    private fun setupViewSpinner() {

        // Prepare spinner options
        val options = arrayOf("Product", "Outfit", "Large", "Small")

        // Initialize spinner adapter
        ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options)
            .also { adapter ->

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

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