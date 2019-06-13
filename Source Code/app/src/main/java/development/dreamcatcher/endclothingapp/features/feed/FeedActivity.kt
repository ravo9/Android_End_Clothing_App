package development.dreamcatcher.endclothingapp.features.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import development.dreamcatcher.endclothingapp.R
import development.dreamcatcher.endclothingapp.data.database.ItemEntity
import development.dreamcatcher.endclothingapp.features.detailedArticle.DetailedItemFragment
import development.dreamcatcher.endclothingapp.injection.EndClothingApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.loading_badge.*
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

        // Initialize RecyclerView (items/ products list)
        setupRecyclerView()

        // Fetch items (products) from the back-end and load them into the view
        subscribeForItems()

        // Catch and handle potential network issues
        subscribeForNetworkError()

        // Setup refresh button
        /*btn_refresh.setOnClickListener{
            refreshItemsSubscription()
        }*/
    }

    private fun setupRecyclerView() {
        itemsGridAdapter = ItemsGridAdapter(this)
        gridView_products.adapter = itemsGridAdapter
    }

    private fun subscribeForItems() {
        viewModel.getAllItems()?.observe(this, Observer<List<ItemEntity>> {

            if (!it.isNullOrEmpty()) {

                // Hide the loading view
                showLoadingView(false)

                // Display fetched items
                itemsGridAdapter.setItems(it)
            }
        })
    }

    private fun subscribeForNetworkError() {
        viewModel.getNetworkError()?.observe(this, Observer<Boolean> {

            // In case of Network Error...
            // If no items have been cached
            if (itemsGridAdapter.count == 0) {

                // Display "Try Again" button
                tryagain_button.visibility = View.VISIBLE

                // Setup onClick listener that reset items data subscription
                tryagain_button.setOnClickListener {
                    refreshItemsSubscription()
                }
            }

            // Display error message to the user
            Toast.makeText(this, R.string.network_problem_check_internet_connection,
                Toast.LENGTH_LONG) .show()
        })
    }

    private fun refreshItemsSubscription() {
        viewModel.getAllItems()?.removeObservers(this)
        subscribeForItems()
    }

    private fun displayDetailedView(itemId: String) {

        val fragment = DetailedItemFragment()
        val bundle = Bundle()
        bundle.putString("itemId", itemId)
        fragment.arguments = bundle

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showLoadingView(loadingState: Boolean) {
        if (loadingState) {
            loading_container.visibility = View.VISIBLE
            appbar_container.visibility = View.GONE
        } else {
            loading_container.visibility = View.GONE
            appbar_container.visibility = View.VISIBLE
        }
    }
}