package development.dreamcatcher.endclothingapp.features.detailedArticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import development.dreamcatcher.endclothingapp.R
import development.dreamcatcher.endclothingapp.injection.EndClothingApp
import kotlinx.android.synthetic.main.detailed_article_view.*
import javax.inject.Inject

// Detailed view for displaying chosen article
class DetailedItemFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailedItemViewModel

    init {
        EndClothingApp.feedComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailedItemViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detailed_article_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Fetch detailed data from Data Repository
        subscribeForArticle()

        // Setup Cross Button
        btn_cross.setOnClickListener{
           activity?.onBackPressed()
        }
    }

    private fun subscribeForArticle() {
        val articleId = this.arguments?.getString("articleId")
        /*if (!articleId.isNullOrEmpty()) {
            viewModel.getSingleSavedArticleById(articleId)?.observe(this, Observer<ItemEntity> {
                setupWebView(it.contentUrl)
            })
        }*/
    }

    // Setup website view
    private fun setupWebView(url: String) {
        website_view.settings.javaScriptEnabled = true
        website_view.webViewClient = WebViewClient()
        website_view.loadUrl(url)
        showLoadingView(false)
    }

    private fun showLoadingView(loadingState: Boolean) {
        if (loadingState) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}