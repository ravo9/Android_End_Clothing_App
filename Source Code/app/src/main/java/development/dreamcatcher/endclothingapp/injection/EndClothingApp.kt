package development.dreamcatcher.endclothingapp.injection

import android.app.Application
import android.content.Context

class EndClothingApp : Application() {

    companion object {
        lateinit var appContext: Context
        lateinit var feedComponent: FeedComponent
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        feedComponent = DaggerFeedComponent.builder().appModule(AppModule(this)).feedModule(FeedModule()).build()
    }
}