package com.migreat.giphy

import android.util.Log
import com.migreat.giphy.model.Gif
import com.migreat.giphy.network.GifDetailResponse
import com.migreat.giphy.network.TrendingResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "" //TODO add your own key!

sealed class GifResult {
    data class Error(val error: Throwable) : GifResult()
    data class Success(val gifs: List<Gif>) : GifResult()
}

interface GifResultReceiver {
    fun receive(result: GifResult)
}

class GiphyService {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.giphy.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val service: GiphyApi = retrofit.create<GiphyApi>(GiphyApi::class.java)

    fun loadTrending(receiver: GifResultReceiver) {
        service.trending(API_KEY)
            .enqueue(object : Callback<TrendingResponse> {
                override fun onFailure(call: Call<TrendingResponse>, t: Throwable) {
                    Log.d("GiphyService", "error: $t")
                    receiver.receive(GifResult.Error(t))
                }

                override fun onResponse(
                    call: Call<TrendingResponse>,
                    response: Response<TrendingResponse>
                ) {
                    val success = response.body()
                    if (success != null) {
                        val gifList = success.data.map { responseGif -> responseGif.toGif() }
                        receiver.receive(GifResult.Success(gifList))
                    } else {
                        receiver.receive(GifResult.Error(Throwable(response.errorBody().toString())))
                    }
                }
            })
    }

    suspend fun gifDetail(gifId: String): Gif {
        val response = service.gifDetail(apiKey = API_KEY, gifId = gifId)
        val success = response.body()

        if (success != null) {
            return success.data.toGif()
        } else {
            throw Exception(response.errorBody().toString())
        }
    }
}

interface GiphyApi {
    @GET("v1/gifs/trending")
    fun trending(@Query("api_key") api_key: String): Call<TrendingResponse>

    @GET("v1/gifs/{gif_id}")
    suspend fun gifDetail(@Path("gif_id") gifId: String, @Query("api_key") apiKey: String): Response<GifDetailResponse>
}
