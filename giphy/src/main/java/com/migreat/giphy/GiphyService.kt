package com.migreat.giphy

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val API_KEY = "LJ1BhrpKfWZO9GIuYQiJ6wEWQFUDAkE6"

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

    fun loadTrending() {
        val trending = service.trending(API_KEY)

        val result = trending.enqueue(object : Callback<TrendingResponse> {
            override fun onFailure(call: Call<TrendingResponse>, t: Throwable) {
                Log.d("GiphyService", "error: $t")
            }

            override fun onResponse(
                call: Call<TrendingResponse>,
                response: Response<TrendingResponse>
            ) {

                val success = response.body()
                val error = response.errorBody()

                Log.d("GiphyService", "success: $success")
                Log.d("GiphyService", "error: $error")
                //TODO return result
            }

        }) //TODO this works?
    }

}

interface GiphyApi {
    @GET("v1/gifs/trending")
    fun trending(@Query("api_key") api_key: String): Call<TrendingResponse>
}

data class TrendingResponse(
    val data: ArrayList<ResponseGif>
) {
    data class ResponseGif(
        val type: String,
        val id: String,
        val url: String,
        val slug: String,
        val title: String,
        val images: Images
    ) {
        data class Images(
            val original: Original,
            val preview_gif: Preview
        ) {
            data class Original(
                val frames: String,
                val hash: String,
                val height: String,
                val mp4: String,
                val mp4_size: String,
                val size: String,
                val url: String,
                val webp: String,
                val webp_size: String,
                val width: String
            )

            data class Preview(
                val height: String,
                val size: String,
                val url: String,
                val width: String
            )
        }
    }
}
