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
        val id = "932183"
        val name = "Casual shirt"
        val price = "£300"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake Item Entity (DB object)
        fakeItemEntity = ItemEntity(id, name, price, null, thumbnailUrl)
    }

    @Test
    fun fetchItemByFeedViewModel() {

        // Prepare LiveData structure
        val itemEntityLiveData = MutableLiveData<ItemEntity>()
        itemEntityLiveData.setValue(fakeItemEntity);

        // Prepare fake item id
        val fakeItemId = "fake/item/id"

        // Set testing conditions
        Mockito.`when`(itemsRepository?.getSingleSavedItemById(fakeItemId)).thenReturn(itemEntityLiveData)

        // Perform the action
        val storedItems = viewModel?.getSingleSavedItemById(fakeItemId)

        // Check results
        Assert.assertSame(itemEntityLiveData, storedItems);
    }
}