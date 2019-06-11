package development.dreamcatcher.endclothingapp.injection

import dagger.Component
import development.dreamcatcher.endclothingapp.data.database.ItemsDatabaseInteractor
import development.dreamcatcher.endclothingapp.data.network.ItemsNetworkInteractor
import development.dreamcatcher.endclothingapp.features.detailedArticle.DetailedItemFragment
import development.dreamcatcher.endclothingapp.features.detailedArticle.DetailedItemViewModel
import development.dreamcatcher.endclothingapp.features.feed.FeedActivity
import development.dreamcatcher.endclothingapp.features.feed.FeedViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, FeedModule::class, ViewModelModule::class))
interface FeedComponent {
    fun inject(feedActivity: FeedActivity)
    fun inject(detailedItemFragment: DetailedItemFragment)
    fun inject(feedViewModel: FeedViewModel)
    fun inject(detailedItemViewModel: DetailedItemViewModel)
    fun inject(itemsNetworkInteractor: ItemsNetworkInteractor)
    fun inject(itemsDatabaseInteractor: ItemsDatabaseInteractor)
}