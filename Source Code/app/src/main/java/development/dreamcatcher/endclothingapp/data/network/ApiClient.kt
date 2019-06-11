package development.dreamcatcher.endclothingapp.data.network

import io.reactivex.Observable
import retrofit2.http.GET

// External gate for communication with API endpoints (operated by Retrofit)
interface ApiClient {

    @GET("/media/catalog/example.json")
    fun getItems(): Observable<ApiResponse>
}