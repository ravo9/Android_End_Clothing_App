package development.dreamcatcher.endclothingapp.features.detailedArticle

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import development.dreamcatcher.endclothingapp.data.database.ItemEntity
import development.dreamcatcher.endclothingapp.data.repositories.ItemsRepository
import javax.inject.Inject

class DetailedItemViewModel @Inject constructor(private val itemsRepository: ItemsRepository)
    : ViewModel(), LifecycleObserver {

    fun getSingleSavedArticleById(articleId: String): LiveData<ItemEntity>? {
        return itemsRepository.getSingleSavedItemById(articleId)
    }
}