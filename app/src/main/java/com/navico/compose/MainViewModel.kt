package com.navico.compose

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.navico.compose.data.Item
import com.navico.compose.retrofit.MarisApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val marisApiService: MarisApiService) :
    ViewModel() {
    fun obtainPois(context: Context): LiveData<List<Item?>> {
        val items : MutableLiveData<List<Item?>> = MutableLiveData<List<Item?>>()
        marisApiService.getPois().enqueue(object : Callback<List<Item?>> {
            override fun onResponse(
                call: Call<List<Item?>?>,
                response: Response<List<Item?>?>
            ) {
                response.body()?.let {
                    when (it.isNotEmpty()) {
                        true -> {
                            items.value = it
                            Timber.d("pois list is not empty: " +
                                    it.size
                            )
                        }
                        false -> {
                            Timber.d("pois list is empty: " +
                                it.size
                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Item?>?>, t: Throwable) {
                //Произошла ошибка
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
            }
        })

        return items
    }
}
