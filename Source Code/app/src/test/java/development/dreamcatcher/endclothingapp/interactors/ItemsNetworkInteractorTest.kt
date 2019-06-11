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
        val contentId = "fake/Item/Id"
        val title = "Fake Item Title"
        val summary = "Sport"
        val contentUrl = "http://google.com"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake sub-object
        val mainImageThumbnailSubObject = ApiResponse.MainImageThumbnail(thumbnailUrl)
        val imagesObjectSubObject = ApiResponse.Images(mainImageThumbnailSubObject)
        val fakeArticle = ApiResponse.Item(contentId, title, summary, contentUrl, imagesObjectSubObject)
        val fakeArticlesList = ArrayList<ApiResponse.Item>()
        fakeArticlesList.add(fakeArticle)

        // Prepare fake ApiResponse
        fakeApiResponse.products = fakeArticlesList
    }
}
