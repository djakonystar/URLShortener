package dev.djakonystar.urlshortener.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.djakonystar.urlshortener.core.NetworkResult
import dev.djakonystar.urlshortener.data.RequestUrl
import dev.djakonystar.urlshortener.data.ResponseUrl
import dev.djakonystar.urlshortener.data.RetrofitService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val apiService = RetrofitService.apiService

    private var _shortenUrl: MutableLiveData<NetworkResult<ResponseUrl>> = MutableLiveData()
    val shortenUrl: LiveData<NetworkResult<ResponseUrl>> = _shortenUrl

    fun sendUrl(requestUrl: RequestUrl) {
        _shortenUrl.value = NetworkResult.Loading()

        try {
            viewModelScope.launch {
                val response = apiService.sendUrl(requestUrl = requestUrl)

                if (response.isSuccessful) {
                    response.body()?.let {
                        _shortenUrl.value = NetworkResult.Success(it)
                    }
                } else {
                    _shortenUrl.value = NetworkResult.Error(response.message())
                }
            }
        } catch (e: Exception) {
            _shortenUrl.value = NetworkResult.Error(e.localizedMessage)
        }
    }
}
