package development.dreamcatcher.endclothingapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import development.dreamcatcher.endclothingapp.data.network.ApiClient
import development.dreamcatcher.endclothingapp.data.network.ApiResponse
import development.dreamcatcher.endclothingapp.data.network.ItemsNetworkInteractor
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ItemsNetworkInteractorTest {

    private var itemsNetworkInteractor: ItemsNetworkInteractor? = null
    private val fakeApiResponse: ApiResponse = ApiResponse()

    @Mock
    private val apiClient: ApiClient? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        itemsNetworkInteractor = ItemsNetworkInteractor(apiClient!!)

        // Prepare fake data
        val id = "932183"
        val name = "Casual shirt"
        val price = "£300"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake Item (API object)
        val fakeItem = ApiResponse.Item(id, name, price, null, thumbnailUrl)

        val fakeItemsList = ArrayList<ApiResponse.Item>()
        fakeItemsList.add(fakeItem)

        // Prepare fake ApiResponse
        fakeApiResponse.products = fakeItemsList
    }
}
