package development.dreamcatcher.endclothingapp.injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import development.dreamcatcher.endclothingapp.data.database.ItemsDatabase
import development.dreamcatcher.endclothingapp.data.database.ItemsDatabaseInteractor
import development.dreamcatcher.endclothingapp.data.network.ApiClient
import development.dreamcatcher.endclothingapp.data.network.ItemsNetworkInteractor
import development.dreamcatcher.endclothingapp.data.network.NetworkAdapter
import development.dreamcatcher.endclothingapp.data.repositories.ItemsRepository
import javax.inject.Singleton

@Module
class FeedModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Context): ItemsDatabase {
        return Room.databaseBuilder(application, ItemsDatabase::class.java, "items_database").build()
    }

    @Provides
    @Singleton
    fun providesItemsDatabaseInteractor(itemsDatabase: ItemsDatabase): ItemsDatabaseInteractor {
        return ItemsDatabaseInteractor(itemsDatabase)
    }

    @Provides
    @Singleton
    fun providesItemsNetworkInteractor(apiClient: ApiClient): ItemsNetworkInteractor {
        return ItemsNetworkInteractor(apiClient)
    }

    @Provides
    @Singleton
    fun providesApiClient(): ApiClient {
        return NetworkAdapter.apiClient()
    }

    @Provides
    @Singleton
    fun providesItemsRepository(itemsNetworkInteractor: ItemsNetworkInteractor,
                                itemsDatabaseInteractor: ItemsDatabaseInteractor): ItemsRepository {
        return ItemsRepository(itemsNetworkInteractor, itemsDatabaseInteractor)
    }
}