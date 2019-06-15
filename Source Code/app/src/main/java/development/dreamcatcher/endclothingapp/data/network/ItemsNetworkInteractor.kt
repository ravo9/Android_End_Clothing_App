package development.dreamcatcher.endclothingapp.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import javax.inject.Inject

// Interactor used for communication between data repository and the external API
class ItemsNetworkInteractor @Inject constructor(private var apiClient: ApiClient) {

    val networkError: MutableLiveData<Boolean> = MutableLiveData()

    fun getAllItems(): Observable<Result<List<ApiResponse.Item>>> {
        val allItemsSubject = SingleSubject.create<Result<List<ApiResponse.Item>>>()

        apiClient.getItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it?.products != null) {
                        allItemsSubject.onSuccess(Result.success(it?.products!!))
                    } else {
                        Log.e("getItems() error: ", "NULL value")
                    }
                },
                {
                    networkError.postValue(true)
                    Log.e("getItems() error: ", it.message)
                })

        return allItemsSubject.toObservable()
    }
}