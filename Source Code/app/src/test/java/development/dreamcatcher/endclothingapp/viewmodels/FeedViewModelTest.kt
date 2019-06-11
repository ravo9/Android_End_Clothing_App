package development.dreamcatcher.endclothingapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.endclothingapp.data.database.ItemEntity
import development.dreamcatcher.endclothingapp.data.repositories.ItemsRepository
import development.dreamcatcher.endclothingapp.features.feed.FeedViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class FeedViewModelTest {

    private var viewModel: FeedViewModel? = null
    private var fakeItemEntity: ItemEntity? = null
    private var fakeArticleEntitiesList = ArrayList<ItemEntity>()

    @Mock
    private val itemsRepository: ItemsRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        viewModel = FeedViewModel(itemsRepository!!)

        // Prepare fake data
        val contentId = "fake/Item/Id"
        val title = "Fake Item Title"
        val summary = "Sport"
        val contentUrl = "http://google.com"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake Item Entity (DB object)
        fakeItemEntity = ItemEntity(contentId, title, summary, contentUrl, thumbnailUrl)

        // Prepare fake Articles Entities List
        fakeArticleEntitiesList.add(fakeItemEntity!!)
    }

    @Test
    fun fetchAllArticlesByFeedViewModel() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<List<ItemEntity>>()
        articleEntityLiveData.setValue(fakeArticleEntitiesList);

        // Set testing conditions
        Mockito.`when`(itemsRepository?.getAllItems()).thenReturn(articleEntityLiveData)

        // Perform the action
        val storedArticles = viewModel?.getAllItems()

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticles);
    }
}