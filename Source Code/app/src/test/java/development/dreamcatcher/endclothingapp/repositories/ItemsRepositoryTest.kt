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
    private var fakeItemEntitiesList = ArrayList<ItemEntity>()

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
        val id = "932183"
        val name = "Casual shirt"
        val price = "£300"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake Item Entity (DB object)
        fakeItemEntity = ItemEntity(id, name, price, null, thumbnailUrl)

        // Prepare fake Item Entities List
        fakeItemEntitiesList.add(fakeItemEntity!!)
    }

    @Test
    fun fetchAllItemsByItemsRepository() {

        // Prepare LiveData structure
        val itemEntityLiveData = MutableLiveData<List<ItemEntity>>()
        itemEntityLiveData.setValue(fakeItemEntitiesList);

        // Prepare fake empty list for backend call
        val fakeList = ArrayList<ApiResponse.Item>()
        val fakeNetworkCallResult = Result.success(fakeList)

        // Set testing conditions
        Mockito.`when`(itemsDatabaseInteractor?.getAllItems()).thenReturn(itemEntityLiveData)
        Mockito.`when`(itemsNetworkInteractor?.getAllItems()).thenReturn(Observable.just(fakeNetworkCallResult))

        // Perform the action
        val storedItems = itemsRepository?.getAllItems()

        // Check results
        Assert.assertSame(itemEntityLiveData, storedItems);
    }

    @Test
    fun fetchItemByItemRepository() {

        // Prepare LiveData structure
        val itemEntityLiveData = MutableLiveData<ItemEntity>()
        itemEntityLiveData.setValue(fakeItemEntity);

        // Prepare fake item id
        val fakeItemId = "fake/item/id"

        // Set testing conditions
        Mockito.`when`(itemsDatabaseInteractor?.getSingleSavedItemById(fakeItemId))
            .thenReturn(itemEntityLiveData)

        // Perform the action
        val storedItem = itemsRepository?.getSingleSavedItemById(fakeItemId)

        // Check results
        Assert.assertSame(itemEntityLiveData, storedItem);
    }
}