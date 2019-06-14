package development.dreamcatcher.endclothingapp.data.network

import com.google.gson.annotations.SerializedName
import java.util.*

// ApiResponse object and its sub-objects used for de-serializing data coming from API endpoint
class ApiResponse {

    @SerializedName("products")
    var products: List<Item> = ArrayList()

    data class Item(

        @SerializedName("id")
        val id: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("price")
        val price: String,

        @SerializedName("color")
        val color: String?,

        @SerializedName("image")
        val image: String?)
}
