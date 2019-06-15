package development.dreamcatcher.endclothingapp.data.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import development.dreamcatcher.endclothingapp.data.database.ItemEntity
import development.dreamcatcher.endclothingapp.data.database.ItemsDatabaseInteractor
import development.dreamcatcher.endclothingapp.data.network.ItemsNetworkInteractor
import javax.inject.Inject

// Data Repository - the main gate of the model (data) part of the application
class ItemsRepository @Inject constructor(private val itemsNetworkInteractor:  ItemsNetworkInteractor,
                                          private val databaseInteractor: ItemsDatabaseInteractor) {

    fun getSingleSavedItemById(id: String): LiveData<ItemEntity>? {
        return databaseInteractor.getSingleSavedItemById(id)
    }

    fun getAllItems(): LiveData<List<ItemEntity>>? {
        updateDataFromBackEnd()
        return databaseInteractor.getAllItems()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return itemsNetworkInteractor.networkError
    }

    @SuppressLint("CheckResult")
    private fun updateDataFromBackEnd() {
        itemsNetworkInteractor.getAllItems().subscribe {
            if (it.isSuccess && it.getOrDefault(null)?.size!! > 0) {

                // Clear database not to store outdated data
                databaseInteractor.clearDatabase()

                // Save freshly fetched items
                it.getOrNull()?.forEach {
                    databaseInteractor.addNewItem(it)
                }
            }
        }
    }
}