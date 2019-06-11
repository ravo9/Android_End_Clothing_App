package development.dreamcatcher.endclothingapp.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.endclothingapp.data.database.ItemEntity
import development.dreamcatcher.endclothingapp.data.database.ItemsDatabaseInteractor
import development.dreamcatcher.endclothingapp.data.network.ApiResponse
import development.dreamcatcher.endclothingapp.data.network.ItemsNetworkInteractor
import development.dreamcatcher.endclothingapp.data.repositories.ItemsRepository
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class ItemsRepositoryTest {

    private var itemsRepository: ItemsRepository? = null
    private var fakeItemEntity: ItemEntity? = null
    private var fakeArticleEntitiesList = ArrayList<ItemEntity>()

    @Mock
    private val itemsDatabaseInteractor: ItemsDatabaseInteractor? = null

    @Mock
    private val itemsNetworkInteractor: ItemsNetworkInteractor? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        itemsRepository = ItemsRepository(itemsNetworkInteractor!!, itemsDatabaseInteractor!!)

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
    fun fetchAllArticlesByArticlesRepository() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<List<ItemEntity>>()
        articleEntityLiveData.setValue(fakeArticleEntitiesList);

        // Prepare fake empty list for backend call
        val fakeList = ArrayList<ApiResponse.Item>()
        val fakeNetworkCallResult = Result.success(fakeList)

        // Set testing conditions
        Mockito.`when`(itemsDatabaseInteractor?.getAllItems()).thenReturn(articleEntityLiveData)
        Mockito.`when`(itemsNetworkInteractor?.getAllItems()).thenReturn(Observable.just(fakeNetworkCallResult))

        // Perform the action
        val storedArticles = itemsRepository?.getAllItems()

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticles);
    }

    @Test
    fun fetchArticleByArticlesRepository() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<ItemEntity>()
        articleEntityLiveData.setValue(fakeItemEntity);

        // Prepare fake article id
        val fakeArticleId = "fake/article/id"

        // Set testing conditions
        Mockito.`when`(itemsDatabaseInteractor?.getSingleSavedItemById(fakeArticleId))
            .thenReturn(articleEntityLiveData)

        // Perform the action
        val storedArticle = itemsRepository?.getSingleSavedItemById(fakeArticleId)

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticle);
    }
}