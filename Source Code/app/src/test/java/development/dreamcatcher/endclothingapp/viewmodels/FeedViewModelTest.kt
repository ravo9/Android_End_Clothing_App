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
    private var fakeItemEntitiesList = ArrayList<ItemEntity>()

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
    fun fetchAllItemsByFeedViewModel() {

        // Prepare LiveData structure
        val itemEntityLiveData = MutableLiveData<List<ItemEntity>>()
        itemEntityLiveData.setValue(fakeItemEntitiesList);

        // Set testing conditions
        Mockito.`when`(itemsRepository?.getAllItems()).thenReturn(itemEntityLiveData)

        // Perform the action
        val storedItems = viewModel?.getAllItems()

        // Check results
        Assert.assertSame(itemEntityLiveData, storedItems);
    }
}