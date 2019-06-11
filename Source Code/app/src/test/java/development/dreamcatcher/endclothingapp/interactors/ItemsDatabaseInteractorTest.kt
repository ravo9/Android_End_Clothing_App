package development.dreamcatcher.endclothingapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.endclothingapp.data.database.ItemEntity
import development.dreamcatcher.endclothingapp.data.database.ItemsDao
import development.dreamcatcher.endclothingapp.data.database.ItemsDatabase
import development.dreamcatcher.endclothingapp.data.database.ItemsDatabaseInteractor
import development.dreamcatcher.endclothingapp.data.network.ApiResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ItemsDatabaseInteractorTest {

    private var itemsDatabaseInteractor: ItemsDatabaseInteractor? = null
    private var fakeItem: ApiResponse.Item? = null
    private var fakeItemEntity: ItemEntity? = null

    @Mock
    private val itemsDatabase: ItemsDatabase? = null

    @Mock
    private val itemsDao: ItemsDao? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        itemsDatabaseInteractor = ItemsDatabaseInteractor(itemsDatabase!!)

        // Prepare fake data
        val contentId = "fake/Item/Id"
        val title = "Fake Item Title"
        val summary = "Sport"
        val contentUrl = "http://google.com"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake sub-objects
        val mainImageThumbnailSubObject = ApiResponse.MainImageThumbnail(thumbnailUrl)
        val imagesObjectSubObject = ApiResponse.Images(mainImageThumbnailSubObject)

        // Prepare fake Item (API object)
        fakeItem = ApiResponse.Item(contentId, title, summary, contentUrl, imagesObjectSubObject)

        // Prepare fake Item Entity (DB object)
        fakeItemEntity = ItemEntity(contentId, title, summary, contentUrl, thumbnailUrl)
    }

    @Test
    fun saveArticleByDatabaseInteractor() {

        // Perform the action
        val resultStatus = itemsDatabaseInteractor!!.addNewArticle(fakeItem).value

        // Check results
        Assert.assertTrue(resultStatus!!)
    }

    @Test
    fun fetchArticleByDatabaseInteractor() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<ItemEntity>()
        articleEntityLiveData.setValue(fakeItemEntity);

        // Set testing conditions
        Mockito.`when`(itemsDatabase?.getArticlesDao()).thenReturn(itemsDao)
        Mockito.`when`(itemsDao?.getSingleSavedArticleById(anyString())).thenReturn(articleEntityLiveData)

        // Perform the action
        val storedArticle = itemsDatabaseInteractor?.getSingleSavedItemById("fake-article-id")

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticle);
    }
}