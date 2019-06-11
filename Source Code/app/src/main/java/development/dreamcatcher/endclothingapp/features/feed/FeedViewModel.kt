package development.dreamcatcher.endclothingapp.features.feed

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import development.dreamcatcher.endclothingapp.data.database.ItemEntity
import development.dreamcatcher.endclothingapp.data.repositories.ItemsRepository
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val itemsRepository: ItemsRepository)
    : ViewModel(), LifecycleObserver {

    fun getAllItems(): LiveData<List<ItemEntity>>? {
        return itemsRepository.getAllItems()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return itemsRepository.getNetworkError()
    }
}