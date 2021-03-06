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
        val id = "932183"
        val name = "Casual shirt"
        val price = "£300"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake Item (API object)
        fakeItem = ApiResponse.Item(id, name, price, null, thumbnailUrl)

        // Prepare fake Item Entity (DB object)
        fakeItemEntity = ItemEntity(id, name, price, null, thumbnailUrl)
    }

    @Test
    fun saveItemByDatabaseInteractor() {

        // Perform the action
        val resultStatus = itemsDatabaseInteractor!!.addNewItem(fakeItem).value

        // Check results
        Assert.assertTrue(resultStatus!!)
    }

    @Test
    fun fetchItemByDatabaseInteractor() {

        // Prepare LiveData structure
        val itemEntityLiveData = MutableLiveData<ItemEntity>()
        itemEntityLiveData.setValue(fakeItemEntity);

        // Set testing conditions
        Mockito.`when`(itemsDatabase?.getItemsDao()).thenReturn(itemsDao)
        Mockito.`when`(itemsDao?.getSingleSavedItemById(anyString())).thenReturn(itemEntityLiveData)

        // Perform the action
        val storedItem = itemsDatabaseInteractor?.getSingleSavedItemById("fake-item-id")

        // Check results
        Assert.assertSame(itemEntityLiveData, storedItem);
    }
}