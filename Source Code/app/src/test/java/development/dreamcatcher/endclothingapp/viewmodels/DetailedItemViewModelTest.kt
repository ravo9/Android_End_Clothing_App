package development.dreamcatcher.endclothingapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.endclothingapp.data.database.ItemEntity
import development.dreamcatcher.endclothingapp.data.repositories.ItemsRepository
import development.dreamcatcher.endclothingapp.features.detailedArticle.DetailedItemViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailedItemViewModelTest {

    private var viewModel: DetailedItemViewModel? = null
    private var fakeItemEntity: ItemEntity? = null

    @Mock
    private val itemsRepository: ItemsRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        viewModel = DetailedItemViewModel(itemsRepository!!)

        // Prepare fake data
        val contentId = "fake/Item/Id"
        val title = "Fake Item Title"
        val summary = "Sport"
        val contentUrl = "http://google.com"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake Item Entity (DB object)
        fakeItemEntity = ItemEntity(contentId, title, summary, contentUrl, thumbnailUrl)
    }

    @Test
    fun fetchArticleByFeedViewModel() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<ItemEntity>()
        articleEntityLiveData.setValue(fakeItemEntity);

        // Prepare fake article id
        val fakeArticleId = "fake/article/id"

        // Set testing conditions
        Mockito.`when`(itemsRepository?.getSingleSavedItemById(fakeArticleId)).thenReturn(articleEntityLiveData)

        // Perform the action
        val storedArticles = viewModel?.getSingleSavedArticleById(fakeArticleId)

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticles);
    }
}