package examples.android2019.network.network

import android.content.Context
import examples.android2019.network.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val CACHE_SIZE: Long = 10 * 1024 * 1024 //10MB


class GiphyClient(context: Context) {

    private val cache = Cache(context.cacheDir, CACHE_SIZE)

    private val client = OkHttpClient().newBuilder()
        .cache(cache)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.giphy.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val giphyService = retrofit.create(GiphyApi::class.java)

    //TODO should do mapping & network error handling!!!
    suspend fun getTrending() = giphyService.getTrending(API_KEY, 25)

}

interface GiphyApi {

    @GET("/v1/gifs/trending")
    suspend fun getTrending(@Query("api_key") apiKey: String, @Query("limit") limit: Int, @Query("rating") rating: String = "g"): Response<List<Any>>

}
